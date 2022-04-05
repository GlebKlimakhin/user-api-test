package com.example.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateUserInfoRequestDto {
    private Long id;
    private String email;
    private String password;
}
