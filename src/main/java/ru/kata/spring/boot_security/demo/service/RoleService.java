package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();

    Role findById(Long id);

    Role findByName(String name);

    void save(Role role);

}
