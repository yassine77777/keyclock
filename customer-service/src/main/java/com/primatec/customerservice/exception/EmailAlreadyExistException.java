package com.primatec.customerservice.exception;


public class EmailAlreadyExistException extends Exception{

    public EmailAlreadyExistException(String message) {
        super(message);
    }
}
