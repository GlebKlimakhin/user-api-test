package com.example.userservice.exceptions;

import lombok.Getter;

@Getter
public class InvalidOperationException extends RuntimeException {
    private final String message = "No such operation exists. List : add, delete";
}
