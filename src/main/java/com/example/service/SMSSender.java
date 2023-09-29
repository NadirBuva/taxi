package com.example.service;

import com.example.dto.PhoneNumberDTO;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
public class SMSSender {

//    private static final String API_BASE_URL = "https://api.sms-provider.com/send-sms"; // Replace with the actual API URL
//    private static final String BEARER_TOKEN = "<token>"; // Replace with your Bearer Token

    private static final String API_BASE_URL = "http://notify.eskiz.uz/api/message/sms/send";
    private static final String BEARER_TOKEN
            = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjQ1MTEsInJvbGUiOm51bGwsImRhdGEiOnsiaWQiOjQ1MTEsIm5hbWUiOiJPT08gVEFYSSAxMjIyIiwiZW1haWwiOiJibG9rX2NoYWluQG1haWwucnUiLCJyb2xlIjpudWxsLCJhcGlfdG9rZW4iOm51bGwsInN0YXR1cyI6ImFjdGl2ZSIsInNtc19hcGlfbG9naW4iOiJlc2tpejIiLCJzbXNfYXBpX3Bhc3N3b3JkIjoiZSQkayF6IiwidXpfcHJpY2UiOjUwLCJ1Y2VsbF9wcmljZSI6MTE1LCJ0ZXN0X3VjZWxsX3ByaWNlIjpudWxsLCJiYWxhbmNlIjozMDAwMDAsImlzX3ZpcCI6MCwiaG9zdCI6InNlcnZlcjEiLCJjcmVhdGVkX2F0IjoiMjAyMy0wNy0xOVQwMzo0OToxMC4wMDAwMDBaIiwidXBkYXRlZF9hdCI6IjIwMjMtMDctMjRUMDc6Mzk6MDkuMDAwMDAwWiIsIndoaXRlbGlzdCI6bnVsbCwiaGFzX3BlcmZlY3R1bSI6MH0sImlhdCI6MTY5MDI2NjIyMywiZXhwIjoxNjkyODU4MjIzfQ.obO_He2FtOHBpUvJogoztAVHOz-ov4amcNr4pmD47R4";


    @SneakyThrows
    public String sendSms(PhoneNumberDTO phoneNumberDTO)  {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(API_BASE_URL);

        // Set the Bearer Token
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + BEARER_TOKEN);
        String code = generateRandomFourDigitNumber();
        System.out.println(code);
        // Create the form data
        List<NameValuePair> params = new ArrayList<>();
        System.out.println(phoneNumberDTO.getPhoneNumber().substring(1));
        params.add(new BasicNameValuePair("mobile_phone", phoneNumberDTO.getPhoneNumber().substring(1)));
        params.add(new BasicNameValuePair("message", "Ваш код подтверждения - "+ code ));
        params.add(new BasicNameValuePair("from", "4546"));
        params.add(new BasicNameValuePair("callback_url", "http://0000.uz/test.php"));

        // Add form data to the request
        httpPost.setEntity(new UrlEncodedFormEntity(params));

        // Execute the request
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();

        // Check the response status
        if (response.getStatusLine().getStatusCode() == 200) {
            String responseBody = EntityUtils.toString(entity);
            return "SMS sent successfully. Response: " + responseBody;
        } else {
            String errorMessage = EntityUtils.toString(entity);
            return "Failed to send SMS. Error: " + errorMessage;
        }
    }
    public static String generateRandomFourDigitNumber() {
        Random random = new Random();
        int randomNumber = random.nextInt(1000000); // Generate a random number between 0 (inclusive) and 10000 (exclusive)
        return String.format("%04d", randomNumber); // Format the number with leading zeros if necessary
    }
}
