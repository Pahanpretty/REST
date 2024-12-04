package ru.kata.spring.boot_security.demo.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/admin")
public class AdminRestController {

    private final UserService userService;

    @GetMapping("/current-user")
    public User getCurrentUser(Principal principal) {
        return userService.findByEmail(principal.getName());
    }

    @GetMapping("/all-users")
    public List<User> getAllUsersWithRoles() {
        return userService.getAllUsersWithRoles().keySet().stream()
                .toList();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody UserDto userDto) {
        userService.addUserWithRoles(userDto);
        return ResponseEntity.ok("User added successfully");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody UserDto userDto) {
        userService.updateUserWithRoles(userDto);
        return ResponseEntity.ok("User updated successfully");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestBody UserDto userDto) {
        userService.delete(userDto.getId());
        return ResponseEntity.ok("User deleted successfully");
    }
}
