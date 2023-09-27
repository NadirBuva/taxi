package com.example.repository;



import com.example.entities.AttachEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachRepository extends JpaRepository<AttachEntity,String > {

}
