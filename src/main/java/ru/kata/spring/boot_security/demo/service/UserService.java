package ru.kata.spring.boot_security.demo.service;


import org.springframework.data.jpa.repository.Query;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;


public interface UserService {
   List<User> findAllUsers();

    User findUserById(Long userId);


    void saveUser(User user);

    void updateUser(User user);

    boolean deleteUser(Long userId);

    User findByEmail(String email);

 @Query("SELECT u FROM User u WHERE u.name = :name")
 User getUserByName(String name);



}
