package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;


public interface UserService {
    User findUserById(Long userId);

    User findUserByUsername(String name);

    User findByEmail(String email);

    List<User> findAllUsers();

    void saveUser(User user);

    void updateUser(User user);

    boolean deleteUser(Long userId);






}
