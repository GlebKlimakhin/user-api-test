package com.example.userservice;

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
import com.example.userservice.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceApplicationTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    User user1 = new User(1L, "test@bb.ru", "1000");
    User user2 = new User(2L, "test3@aa.ru", "300");
    User user3 = new User(3L, "test3@aa.ru", "300");
    Role adminRole = new Role(2, "ROLE_ADMIN");


    @BeforeAll
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAll() {
        List<User> users = new ArrayList<>(Arrays.asList(user1, user2, user3));
        Mockito.when(userRepository.findAll()).thenReturn(users);

        assertNotNull(userService.findAll());


        assertEquals(
                users.stream().map(this::mapUserToUserDto).collect(Collectors.toList()),
                userService.findAll());
    }

    @Test
    public void findById() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        assertNotNull(userService.findUserById(1L));

        assertEquals(mapUserToUserDto(user1), userService.findUserById(1L));
    }

    @Test
    public void createUser() {
        Mockito.when(userRepository.save(user1)).thenReturn(user1);

        assertNotNull(userService.createUser(user1));

        assertEquals(user1, userService.createUser(user1));
    }

    @Test
    public void updateUserInfo() {
        UpdateUserInfoRequestDto infoReq = new UpdateUserInfoRequestDto(1L, "updated", "updated");

        userService.updateUserInfo(infoReq);
        userService.updateUserInfo(infoReq);

        verify(userRepository, times(2)).updateById(infoReq.getUsername(),
                                                                         infoReq.getPassword(),
                                                                         infoReq.getId());

    }

    /**
     * UpdateUserRoles - плохой тест, не проверяет ролей пользователя, которого мы сохраняем в репозитории
     * Всё ещё думаю как пофиксить, буду рад, если подскажете :)
     */
    @Test
    public void updateUserRoles() {
        UpdateUserRolesRequestDto rolesReq = new UpdateUserRolesRequestDto(1L, "ROLE_ADMIN", "add");
        Role userRole = new Role(1, "ROLE_USER");
        User user = new User(1L, "username", "password", List.of(userRole));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(Optional.of(adminRole));

        userService.updateUserRoles(rolesReq);

        verify(userRepository, times(1)).flush();
    }

    @Test
    public void deleteUser() {
        userService.deleteUserById(any(Long.class));
        verify(userRepository, times(1)).deleteById(any(Long.class));
    }

    @Test
    public void findRoleByName() {
        when(roleRepository.findByName(any(String.class))).thenReturn(Optional.of(adminRole));

        assertNotNull(userService.findRoleByName("ROLE_ADMIN"));

        assertEquals(adminRole, userService.findRoleByName("ROLE_ADMIN"));
    }

    @Test
    public void assert_throwsUserNotFoundException() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.findUserById(1L);
        });
        assertInstanceOf(UserNotFoundException.class, exception);
    }

    @Test
    public void assert_throwsRoleNotFoundException() {
        when(roleRepository.findByName(any(String.class))).thenReturn(Optional.empty());
        RoleNotFoundException exception = assertThrows(RoleNotFoundException.class, () -> {
            userService.findRoleByName(any(String.class));
        });
        assertInstanceOf(RoleNotFoundException.class, exception);
    }

    @Test
    public void assert_throwsInvalidOperationException () {
        UpdateUserRolesRequestDto updRequest = new UpdateUserRolesRequestDto(1L, "ROLE_ADMIN", "wrongOperation");
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user1));
        when(roleRepository.findByName(any(String.class))).thenReturn(Optional.of(adminRole));
        InvalidOperationException exception = assertThrows(InvalidOperationException.class, () -> {
            userService.updateUserRoles(updRequest);
        });
        assertInstanceOf(InvalidOperationException.class, exception);
    }

    private UserDto mapUserToUserDto(User user) {
        return new UserDto(user.getId(),
                user.getUsername(),
                user.getPassword());
    }
}
