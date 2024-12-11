package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UsersRepo;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private UsersRepo usersRepo;
    private PasswordEncoder passwordEncoder;

    private RoleService roleService;
    @Autowired
    public UserServiceImpl(UsersRepo usersRepo, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.usersRepo = usersRepo;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public List<User> findAll() {
        return usersRepo.findAll();
    }
    @Override
    public User findOne(int id) {
        Optional<User> foundUser = usersRepo.findById(id);
        return foundUser.orElse(null);
    }

    @Override
    @Transactional
    public void save(User user) {

        User userToUpdate = new User();
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
        userToUpdate.setFirstName(user.getFirstName());
        Set<Role> rolesJS = user.getRoles();
        Set<Role> roleDB = new HashSet<>();
        for(Role role: rolesJS){
            roleDB.add(roleService.getRoleByName(role.getAuthority()));
        }
        userToUpdate.setRoles(roleDB);

        usersRepo.save(userToUpdate);
    }

    @Override
    @Transactional
    public void update(User updatedUser) {
        User userToUpdate = findOne(updatedUser.getId());
        userToUpdate.setUsername(updatedUser.getUsername());
        userToUpdate.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        userToUpdate.setFirstName(updatedUser.getFirstName());
        userToUpdate.setRoles(updatedUser.getRoles());

        usersRepo.save(userToUpdate);
    }

    @Override
    @Transactional
    public void delete(int id) {
        usersRepo.deleteById(id);
    }
    public User getPersonByUsername(String username) {
        Optional<User> user = usersRepo.findByUsername(username);
        return user.orElse(null);
    }
}
