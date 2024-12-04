package ru.kata.spring.boot_security.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String dllAuto;

    @Override
    public void run(String... strings) {
        if (dllAuto.equals("update")) {
            Role adminRole = new Role("ROLE_ADMIN");
            Role userRole = new Role("ROLE_USER");

            this.roleRepository.save(adminRole);
            this.roleRepository.save(userRole);

            User admin = new User("max", "summer", "summer@mail.ru", 26, "admin");
            admin.setRoles(new HashSet<>(List.of(adminRole, userRole)));

            User user = new User("user", "User", "user@mail.ru", 30, "user");
            user.setRoles(new HashSet<>(List.of(userRole)));

            this.userRepository.save(admin);
            this.userRepository.save(user);
        }
    }
}