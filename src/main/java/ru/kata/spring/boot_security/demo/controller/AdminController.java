package ru.kata.spring.boot_security.demo.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepo;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private final RoleRepo roleRepo;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, RoleRepo roleRepo) {
        this.userService = userService;
        this.roleService = roleService;
        this.roleRepo = roleRepo;
    }
    @GetMapping("/users")
    public ResponseEntity <List<UserDTO>> index() {
        return new ResponseEntity <> (userService.findAll().stream().map(this::convertToUserDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }
    @PostMapping("/users")
    public ResponseEntity<HttpStatus> create(@Valid @RequestBody UserDTO userDTO, BindingResult result) {

        if(result.hasErrors()) {
            throw new IllegalArgumentException();
        }
        userService.save(convertToUser(userDTO));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<HttpStatus> update(@Valid @RequestBody UserDTO userDTO) {
        userService.update(convertToUser(userDTO));
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        User user = userService.findOne(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/users/roles")
    public List<Role> roleList() {
        return roleRepo.findAll();
    }

    private User convertToUser(UserDTO userDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userDTO, User.class);
    }

    public UserDTO convertToUserDto(User user) {
        ModelMapper modelMapper =new ModelMapper();
        return modelMapper.map(user, UserDTO.class);
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable("id") int id) {
        return userService.findOne(id);
    }
}
