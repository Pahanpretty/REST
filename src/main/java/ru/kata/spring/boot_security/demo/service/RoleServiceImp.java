package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dto.RoleDto;
import ru.kata.spring.boot_security.demo.mappers.RoleMapper;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.List;

@Service
public class RoleServiceImp implements RoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    public RoleServiceImp(RoleRepository roleRepository,
                          RoleMapper roleMapper) {

        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public void addRole(RoleDto roleDto) {
        Role role = roleMapper.toModel(roleDto);
        roleRepository.save(role);
    }

    @Override
    public void delete(Long id) {
        roleRepository.delete(roleRepository.getById(id));

    }

}

