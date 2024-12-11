package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UsersRepo;

import java.util.Optional;

@Service
public class UserDetailsServiceAuthentication implements UserDetailsService {
    private UsersRepo usersRepo;
    @Autowired
    public UserDetailsServiceAuthentication(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = usersRepo.findByUsername(username);
        return user.orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not  found!", username)));
    }

}