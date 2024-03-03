package com.primatec.customerservice.controller;


import com.primatec.customerservice.dto.CustomerDTO;

import com.primatec.customerservice.exception.CinAlreadyExistException;
import com.primatec.customerservice.exception.CustomerNotFoundException;
import com.primatec.customerservice.exception.EmailAlreadyExistException;
import com.primatec.customerservice.exception.PhoneAlreadyExistException;
import com.primatec.customerservice.kafka.EventProducer;
import com.primatec.customerservice.service.CustomerService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v2/customers")
public class CustomerRestController {

    private final CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/save")

    public CustomerDTO save(@RequestBody CustomerDTO customerDTO) throws CinAlreadyExistException, EmailAlreadyExistException, PhoneAlreadyExistException {


        return customerService.save(customerDTO);

    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('client_admin')")
    public CustomerDTO update(@PathVariable String id, @RequestBody  CustomerDTO customerDTO) throws CustomerNotFoundException, CinAlreadyExistException, EmailAlreadyExistException, PhoneAlreadyExistException{
        return customerService.update(id, customerDTO);
    }

    @GetMapping("/get/{id}")
    public CustomerDTO getById(@PathVariable String id) throws CustomerNotFoundException {
        return customerService.getById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable String id) throws CustomerNotFoundException {
        customerService.deleteById(id);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(@NotNull Exception exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
