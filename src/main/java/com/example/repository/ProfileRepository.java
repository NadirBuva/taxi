package com.example.repository;


import com.example.entities.auth.ProfileEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer>,
        PagingAndSortingRepository<ProfileEntity, Integer> {
    Optional<ProfileEntity> findByPhoneNumber(String phone);
    Optional<ProfileEntity> findByName(String userName) ;


}
