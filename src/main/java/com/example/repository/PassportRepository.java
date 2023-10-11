package com.example.repository;

import com.example.entities.doc.PassportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassportRepository  extends JpaRepository<PassportEntity,Integer > {
}
