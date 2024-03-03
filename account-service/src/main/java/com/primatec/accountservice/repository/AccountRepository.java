package com.primatec.accountservice.repository;

import com.primatec.accountservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {


    @Query("select a from Account a where a.customerId = ?1")
    Account findByCustomerId(String customerId);


    @Query("select case when count(a) > 0 then true else false end from Account a where a.customerId = ?1")
    Boolean checkIfCustomerIdExists(String customerId);
}
