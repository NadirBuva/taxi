package com.example.utill;

import com.example.dto.auth.JwtDTO;
import com.example.entities.auth.ProfileRole;
import io.jsonwebtoken.*;

import java.util.Date;

public class JwtUtil {

    private static final String secretKey = "topsecretKey!123";
    private static final int tokenLiveTime = 1000 * 3600 * 4 ; // 4-hour

    public static String encode(String phoneNumber, ProfileRole role) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS512, secretKey);

        jwtBuilder.claim("phoneNumber", phoneNumber);

        jwtBuilder.claim("role", role);

        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (tokenLiveTime)));
        jwtBuilder.setIssuer("WareHouse test port");
        return jwtBuilder.compact();
    }

    public static JwtDTO decodeToken(String token) {

        JwtParser jwtParser = Jwts.parser();
        jwtParser.setSigningKey(secretKey);

        Jws<Claims> jws = jwtParser.parseClaimsJws(token);

        Claims claims = jws.getBody();

        String username = (String) claims.get("username");

        String role = (String) claims.get("role");
        ProfileRole profileRole = ProfileRole.valueOf(role);

        return new JwtDTO(username, profileRole);

    }

}
