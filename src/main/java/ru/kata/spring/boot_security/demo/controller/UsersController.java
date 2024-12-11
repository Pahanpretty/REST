package ru.kata.spring.boot_security.demo.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UsersRepo;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;


@Controller
@RequestMapping("/user")
public class UsersController {
    private final UserService userService;
    private final UsersRepo usersRepo;
    @Autowired
    public UsersController(UserService userService, UsersRepo usersRepo) {
        this.userService = userService;
        this.usersRepo = usersRepo;
    }
    @GetMapping("/")
    public ResponseEntity <UserDTO> show(Principal principal) {
        return new ResponseEntity<>(convertToUserDto(userService.getPersonByUsername(principal.getName())),
                HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") int id) {
        return userService.findOne(id);
    }


    public UserDTO convertToUserDto(User user) {
        ModelMapper modelMapper =new ModelMapper();
        return modelMapper.map(user, UserDTO.class);
    }
}

