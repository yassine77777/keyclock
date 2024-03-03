package com.primatec.accountservice.controller;


import com.primatec.accountservice.dto.CustomerDTO;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@FeignClient(name = "CUSTOMER-SERVICE")
public interface CustomerRestClient {

    @GetMapping("/v2/customers/get/{id}")
    CustomerDTO getById(@PathVariable String id);
}
