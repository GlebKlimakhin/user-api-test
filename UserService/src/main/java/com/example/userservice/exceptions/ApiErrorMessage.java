package com.example.userservice.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class ApiErrorMessage {

    private int status;
    private LocalDate date;
    private String message;
}
