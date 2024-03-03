package com.primatec.accountservice.exception;


public class AccountNotFoundException extends Exception{

    public AccountNotFoundException(String message) {
        super(message);
    }
}
