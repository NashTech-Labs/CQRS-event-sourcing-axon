package com.example.CQRSeventsourcing.exception;

public class UnconfirmedOrderException extends Throwable {

    public UnconfirmedOrderException(String message) {
        super(message);
    }
}
