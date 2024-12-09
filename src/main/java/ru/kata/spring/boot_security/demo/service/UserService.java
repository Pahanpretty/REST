package ru.kata.spring.boot_security.demo.service;


import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Map;

@Service
public interface UserService {


    void delete(Long id);

    Map<User, List<String>> getAllUsersWithRoles();

    void addUserWithRoles(UserDto userDto);

    void updateUserWithRoles(UserDto userDto);

    User findByEmail(String email);

}
