package com.primatec.accountservice.entity;

import com.primatec.accountservice.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private String id;
    private String firstname;
    private String name;
    private String placeOfBirth;
    private Date dateOfBirth;
    private String nationality;
    private Sex sex;
    private String cin;
    private String email;
    private String phone;
    private LocalDateTime creation;
    private LocalDateTime lastUpdate;
}
