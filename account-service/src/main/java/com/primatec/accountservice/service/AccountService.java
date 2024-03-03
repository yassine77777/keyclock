package com.primatec.accountservice.service;


import com.primatec.accountservice.dto.AccountDTO;
import com.primatec.accountservice.dto.CustomerDTO;
import com.primatec.accountservice.exception.AccountNotFoundException;
import com.primatec.accountservice.exception.CustomerAlreadyHaveAccountException;
import com.primatec.accountservice.exception.CustomerNotFoundException;


public interface AccountService {

    AccountDTO getById(String id) throws AccountNotFoundException;


    AccountDTO getByCustomerId(String customerId) throws AccountNotFoundException;


    AccountDTO save(AccountDTO accountDTO) throws CustomerNotFoundException, CustomerAlreadyHaveAccountException;


    void deleteById(String id);


    AccountDTO updateStatus(AccountDTO accountDTO) throws AccountNotFoundException;
}
