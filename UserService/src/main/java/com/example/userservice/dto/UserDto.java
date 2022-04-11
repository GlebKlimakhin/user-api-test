package com.example.userservice.dto;

import com.example.userservice.model.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@EqualsAndHashCode
public class UserDto {

    Long id;
    String username;
    String password;
    List<Role> roles;

    public UserDto(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
