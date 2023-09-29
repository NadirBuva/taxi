package com.example.service;

import com.example.dto.PhoneNumberCheckSmsDTO;
import com.example.dto.PhoneNumberDTO;
import com.example.dto.ResponseSendSms;
import com.example.entities.PhoneNumberEntity;
import com.example.enums.Language;
import com.example.exp.EmailAlreadyExistsException;
import com.example.repository.PhoneNumberRepository;
import lombok.SneakyThrows;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class SmsSendService {

    private final PhoneNumberRepository repository ;
    private final ResourceBundleService resourceBundleService;
    public SmsSendService(PhoneNumberRepository repository, ResourceBundleService resourceBundleService) {
        this.repository = repository;
        this.resourceBundleService = resourceBundleService;
    }

    @SneakyThrows
    public ResponseSendSms sendSms(PhoneNumberDTO contact ) {
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
        ResponseSendSms sendSms= new ResponseSendSms();
        sendSms.setMessage("Ваш код подтверждения - "+ code);

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                sendSms.setSuccess(true);
                System.out.println(response);
                repository.save(phoneNumber);
                return sendSms ;
            }
//        sendSms.setSuccess(false);
                repository.save(phoneNumber);
        sendSms.setSuccess(true); // keyin to'girlash kerak
            return sendSms ;
    }

    private String getSmsToken() {
        return "......";
    }

    public static String generateRandomFourDigitNumber() {
        Random random = new Random();
        int randomNumber = random.nextInt(1000000); // Generate a random number between 0 (inclusive) and 1000000 (exclusive)
        return String.format("%06d", randomNumber);
    }

    public ResponseSendSms checkSms(PhoneNumberCheckSmsDTO code, Language language) {
        Optional<PhoneNumberEntity> exists = repository.findByPhoneNumber(code.getPhoneNumber());
        ResponseSendSms response = new ResponseSendSms() ;
        if (exists.isPresent()) {
            PhoneNumberEntity entity = exists.get();
            if (entity.getCode().equals(code.getCode())) {
                response.setSuccess(true);
                response.setContent("Vsyo idiot po planu");
                return response ;
            } else {
                throw new EmailAlreadyExistsException(resourceBundleService.getMessage("email.exists", language));
            }
        }
        response.setSuccess(false);
        return response ;
    }
}
