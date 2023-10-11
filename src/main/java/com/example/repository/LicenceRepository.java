package com.example.repository;

import com.example.entities.doc.DriverLicence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenceRepository extends JpaRepository<DriverLicence , Integer> {
}
