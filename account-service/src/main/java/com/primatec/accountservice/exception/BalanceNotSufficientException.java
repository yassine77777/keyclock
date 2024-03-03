package com.primatec.accountservice.exception;


public class BalanceNotSufficientException extends Exception{

    public BalanceNotSufficientException(String message) {
        super(message);
    }
}
