package com.example.userservice.api;

import com.example.userservice.dto.UpdateUserInfoRequestDto;
import com.example.userservice.dto.UpdateUserRolesRequestDto;
import com.example.userservice.dto.UserDto;
import com.example.userservice.model.User;
import com.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserApi {

    private UserService userService;

    @Autowired
    public UserApi(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> findAll() {
        return userService.findAll().stream().map(u -> new UserDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User findUserById(@PathVariable long id) {
        return userService.findUserById(id);
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        URI location = URI.create(String.format("/users/%s", user.getId()));
        return ResponseEntity.created(location).body(userService.createUser(user));
    }

    @PutMapping("/info")
    @ResponseStatus(HttpStatus.OK)
    public void updateUserInfo(@RequestBody UpdateUserInfoRequestDto dto) {
        userService.updateUserInfo(dto);
    }

    @PutMapping("/roles/")
    @ResponseStatus(HttpStatus.OK)
    public void updateUserRoles(@RequestBody UpdateUserRolesRequestDto dto) {
        userService.updateUserRoles(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

}
