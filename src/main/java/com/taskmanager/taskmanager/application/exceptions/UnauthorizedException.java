package com.taskmanager.taskmanager.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.ACCEPTED.FORBIDDEN)
public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException (String message){
        super(message);
    }
}
