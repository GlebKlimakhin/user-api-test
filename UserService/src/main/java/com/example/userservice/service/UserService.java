package com.example.userservice.service;

import com.example.userservice.dto.UpdateUserInfoRequestDto;
import com.example.userservice.dto.UpdateUserRolesRequestDto;
import com.example.userservice.dto.UserDto;
import com.example.userservice.exceptions.InvalidOperationException;
import com.example.userservice.exceptions.RoleNotFoundException;
import com.example.userservice.exceptions.UserNotFoundException;
import com.example.userservice.model.Role;
import com.example.userservice.model.User;
import com.example.userservice.repository.RoleRepository;
import com.example.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(this::mapUserToUserDto).collect(Collectors.toList());
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public UserDto findUserById(Long id) {
        return userRepository.findById(id).map(this::mapUserToUserDto).orElseThrow(() ->
                new UserNotFoundException(String.format("User with id %s not found", id)));
    }

    public void updateUserInfo(UpdateUserInfoRequestDto dto) {
        userRepository.updateById(
                dto.getUsername(), dto.getPassword(), dto.getId());
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public void updateUserRoles(UpdateUserRolesRequestDto dto) {
        User user = mapUserDtoToUser(findUserById(dto.getId()));
        Role role = findRoleByName(dto.getRoleName());
        List<Role> userRoles = new ArrayList<>();
        if(user.getRoles() != null) {
            userRoles.addAll(user.getRoles());
        }
        String operation = dto.getOperation();
        if(operation.equals("add")) {
            userRoles.add(role);
        }
        else if(operation.equals("delete")) {
            userRoles.remove(role);
        }
        else {
            throw new InvalidOperationException();
        }
        user.setRoles(userRoles);
        userRepository.flush();
    }

    public Role findRoleByName(String roleName) {
        return roleRepository.findByName(roleName).orElseThrow(() ->
                new RoleNotFoundException(String.format("Role with name %s not found", roleName)));
    }

    private UserDto mapUserToUserDto(User user) {
        return new UserDto(user.getId(),
                user.getUsername(),
                user.getPassword());
    }

    private User mapUserDtoToUser(UserDto userDto){
        return new User(userDto.getId(), userDto.getUsername(), userDto.getPassword(), userDto.getRoles());
    }

}
