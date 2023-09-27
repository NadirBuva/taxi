package com.example.service;

import com.example.enums.Language;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

@Service
public class SMSSender {

    private static final String API_URL = "http://notify.eskiz.uz/api/message/sms/send"
            ;
    private static final String BEARER_TOKEN
            = "";
    private static final String code
            =generateRandomFourDigitNumber();

    public  String sendSMS(String phoneNumber , Language language) {
        try {

            URL url = new URL(API_URL);
            System.out.println(code);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            String callbackUrl = "http://localhost:8080/test/sms";
            String message = "Ваш код подтверждения - " + code  ;
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + BEARER_TOKEN);
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            String from = "4546";
            connection.setDoOutput(true);
            connection.setDoInput(true);

            String postData = "mobile_phone=" + phoneNumber
                    + "&message=" + message
                    + "&from=" + from
                    + "&callback_url=" + callbackUrl;

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(postData);
            wr.flush();
            wr.close();

            int responseCode = connection.getResponseCode();
            BufferedReader in = new
                    BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            if (responseCode == 200) {
                System.out.println("SMS sent successfully.");
                return "SMS sent successfully." ;
            } else {
                System.out.println("Failed to send SMS. Status code: " + responseCode);
                System.out.println("Response content: " + response);
                return "Failed to send SMS. Status code: " + responseCode + "\n" +"Response content: " + response ;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Nimadir yozdim tepadan lekin chiqmadi ! ";
    }
    public static String generateRandomFourDigitNumber() {
        Random random = new Random();
        int randomNumber = random.nextInt(100000); // Generate a random number between 0 (inclusive) and 10000 (exclusive)
        return String.format("%04d", randomNumber); // Format the number with leading zeros if necessary
    }
}
