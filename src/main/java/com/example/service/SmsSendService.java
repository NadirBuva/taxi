package com.example.service;

import com.example.dto.PhoneNumberDTO;
import com.example.entities.PhoneNumberEntity;
import com.example.repository.PhoneNumberRepository;
import okhttp3.*;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class SmsSendService {

    private final PhoneNumberRepository repository ;

    public SmsSendService(PhoneNumberRepository repository) {
        this.repository = repository;
    }

    public String sendSms(PhoneNumberDTO contact ) {
        PhoneNumberEntity phoneNumber = new PhoneNumberEntity();

        String code = generateRandomFourDigitNumber();
        System.out.println(code);
        phoneNumber.setPhoneNumber(contact.getPhoneNumber());
        phoneNumber.setSendingTime(LocalDateTime.now());
        phoneNumber.setCode(code);
        System.out.println(contact.getPhoneNumber().substring(1));
        // RestTemplate or HttpOK or  HttpClient
        String token = "Bearer " + getSmsToken();
        OkHttpClient client = new OkHttpClient();

        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("mobile_phone", contact.getPhoneNumber().substring(1))
                .addFormDataPart("message", "Ваш код подтверждения - "+code )
                .addFormDataPart("from", "998907442551")

                .build();
        Request request = new Request.Builder()
                .url("https://notify.eskiz.uz/api/message/sms/send")
                .method("POST", body)
                .header("Authorization", token)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                System.out.println(response);
                repository.save(phoneNumber);
                return response.toString() ;
            } else {
                throw new IOException();
            }
        } catch (IOException  e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private String getSmsToken() {
        return "......";
    }

    public static String generateRandomFourDigitNumber() {
        Random random = new Random();
        int randomNumber = random.nextInt(100000); // Generate a random number between 0 (inclusive) and 10000 (exclusive)
        return String.format("%04d", randomNumber); // Format the number with leading zeros if necessary
    }

}
