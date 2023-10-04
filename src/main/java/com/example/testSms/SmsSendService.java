package com.example.testSms;

import com.example.dto.PhoneNumberCheckSmsDTO;
import com.example.dto.PhoneNumberDTO;
import com.example.dto.ResponseSendSms;
import com.example.entities.PhoneNumberEntity;
import com.example.entities.auth.ProfileEntity;
import com.example.enums.Language;
import com.example.exp.ProfileNotFoundException;
import com.example.repository.PhoneNumberRepository;
import com.example.repository.ProfileRepository;
import com.example.service.ResourceBundleService;
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
    private final ProfileRepository profileRepository;
    public SmsSendService(PhoneNumberRepository repository, ResourceBundleService resourceBundleService, ProfileRepository profileRepository) {
        this.repository = repository;
        this.resourceBundleService = resourceBundleService;
        this.profileRepository = profileRepository;
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
            LocalDateTime sendingTime = entity.getSendingTime();
            var hour = sendingTime.getHour();
            var minute = sendingTime.getMinute();
            System.out.println(minute);
            System.out.println(LocalDateTime.now());
            if(hour==LocalDateTime.now().getHour() && minute>=LocalDateTime.now().getMinute()-2){
            if (entity.getCode().equals(code.getCode())) {
                Optional<ProfileEntity> byPhoneNumber = profileRepository.findByPhoneNumber(code.getPhoneNumber());
                if (byPhoneNumber.isEmpty()){
                response.setSuccess(true);
                response.setContent("Vsyo idiot po planu");
                return response ;
                }
                response.setSuccess(true);
                response.setContent("Vsyo idiot po planu");
                return response ;
            } else {
                response.setSuccess(false);
                response.setContent("Kod hato kiritildi ");
                return response ;
            }
            }
            response.setSuccess(false);
            response.setContent("kod yaroqsiz vaqt 2 mindan o'tib ketdi ");
            return response ;
        }
        response.setSuccess(false);
        response.setContent("Telefon raqam topilmadi!");
        repository.delete(exists.get());
        return response ;
    }
}
