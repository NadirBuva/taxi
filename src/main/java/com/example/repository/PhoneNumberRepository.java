package com.example.repository;

import com.example.dto.PhoneNumberDTO;
import com.example.entities.PhoneNumberEntity;
import com.example.entities.auth.ProfileEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhoneNumberRepository extends CrudRepository<PhoneNumberEntity, Integer>,
        PagingAndSortingRepository<PhoneNumberEntity , Integer> {
    Optional<PhoneNumberEntity> findByPhoneNumber(String phone);
}
