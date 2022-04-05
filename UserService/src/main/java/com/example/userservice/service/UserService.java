package com.example.userservice.service;

import com.example.userservice.dto.UpdateUserInfoRequestDto;
import com.example.userservice.dto.UpdateUserRolesRequestDto;
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

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException(String.format("User with id %s not found", id)));
    }

    public void updateUserInfo(UpdateUserInfoRequestDto dto) {
        userRepository.updateById(
                dto.getEmail(), dto.getPassword(), dto.getId());
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public void updateUserRoles(UpdateUserRolesRequestDto dto) {
        User user = findUserById(dto.getId());
        Role role = findRoleByName(dto.getRoleName());
        List<Role> userRoles = new ArrayList<>();
        user.getRoles().forEach(r -> {
            userRoles.add(r);
        });
        String operation = dto.getOperation();
        if(operation.equals("add")) {
            userRoles.add(role);
        }
        if(operation.equals("delete")) {
            userRoles.remove(role);
        }
        else {
            throw new InvalidOperationException();
        }
        user.setRoles(userRoles);
    }

    public Role findRoleByName(String roleName) {
        return roleRepository.findByName(roleName).orElseThrow(() ->
                new RoleNotFoundException(String.format("Role with name %s not found", roleName)));
    }


}
