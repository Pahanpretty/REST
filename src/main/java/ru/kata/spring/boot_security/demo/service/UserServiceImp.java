package ru.kata.spring.boot_security.demo.service;

import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.mappers.UserMapper;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;


    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImp(UserRepository userRepository,
                          UserMapper userMapper,
                          PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Transactional(readOnly = true)
    @Override
    public Map<User, List<String>> getAllUsersWithRoles() {
        return userRepository
                .findAll(Sort.by("id"))
                .stream()
                .sorted(Comparator.comparing(User::getId))
                .collect(Collectors.toMap(
                        Function.identity(),
                        user -> user.getRoles().stream()
                                .map(Role::getName)
                                .map(roleName -> roleName.replace("ROLE_", ""))
                                .sorted()
                                .toList(),
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));
    }


    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.
                User(user.getUsername(), user.getPassword(), authorities);
    }


    @Transactional
    @Override
    public void addUserWithRoles(UserDto userDto) {
        User user = userMapper.toModel(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }


    @Transactional
    @Override
    public void updateUserWithRoles(UserDto userDto) {
        User existingUser = userRepository.findById(userDto.getId()).orElse(null);
        existingUser.setPassword(passwordEncoder.encode(existingUser.getPassword()));
        if (existingUser != null) {
            existingUser.setFirstname(userDto.getFirstname());
            existingUser.setLastname(userDto.getLastname());
            existingUser.setAge(userDto.getAge());
            existingUser.setEmail(userDto.getEmail());
            existingUser.setPassword(userDto.getPassword());
            userRepository.save(existingUser);
        }
    }


}


