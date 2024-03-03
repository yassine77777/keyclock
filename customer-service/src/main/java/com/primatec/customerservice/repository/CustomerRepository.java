package com.primatec.customerservice.repository;

import com.primatec.customerservice.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, String> {


    @Query("select case when count(c)>0 then true else false END from Customer c where c.cin=?1")
    Boolean checkIfCinExists(String cin);


    @Query("select case when count(c)>0 then true else false END from Customer c where c.phone=?1")
    Boolean checkIfPhoneExists(String phone);


    @Query("select case when count(c)>0 then true else false END from Customer c where c.email=?1")
    Boolean checkIfEmailExists(String email);


    @Query("SELECT c FROM Customer c WHERE c.name LIKE :keyword OR c.firstname LIKE :keyword OR c.cin LIKE :keyword ORDER BY c.cin DESC")
    Page<Customer> searchByFirstnameOrNameOrCin(@Param("keyword") String keyword, Pageable pageable);


    @Query("SELECT c FROM Customer c ORDER BY c.cin")
    Page<Customer> getAllByPage(Pageable pageable);
}
