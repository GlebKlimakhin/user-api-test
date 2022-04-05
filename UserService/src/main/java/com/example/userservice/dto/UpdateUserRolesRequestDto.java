package com.example.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateUserRolesRequestDto {
    private long id;
    private String roleName;
    private String operation;
}
