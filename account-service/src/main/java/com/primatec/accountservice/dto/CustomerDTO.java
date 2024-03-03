package com.primatec.accountservice.dto;

import com.primatec.accountservice.enums.Sex;

import java.time.LocalDateTime;
import java.util.Date;

public record CustomerDTO(String id,
                          String firstname,
                          String name,
                          String placeOfBirth,
                          Date dateOfBirth,
                          String nationality,
                          Sex sex,
                          String cin,
                          String email,
                          String phone,
                          LocalDateTime creation,
                          LocalDateTime lastUpdate) {
}
