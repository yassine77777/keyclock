package com.primatec.customerservice.service;

import com.primatec.customerservice.dto.CustomerDTO;
import com.primatec.customerservice.exception.CinAlreadyExistException;
import com.primatec.customerservice.exception.CustomerNotFoundException;
import com.primatec.customerservice.exception.EmailAlreadyExistException;
import com.primatec.customerservice.exception.PhoneAlreadyExistException;


public interface CustomerService {




    CustomerDTO save(CustomerDTO customerDTO) throws CinAlreadyExistException, EmailAlreadyExistException, PhoneAlreadyExistException;

    CustomerDTO update(String id, CustomerDTO customerDTO) throws CustomerNotFoundException, CinAlreadyExistException, EmailAlreadyExistException, PhoneAlreadyExistException;


    CustomerDTO getById(String id) throws CustomerNotFoundException;

    void deleteById(String id) throws CustomerNotFoundException;
}
