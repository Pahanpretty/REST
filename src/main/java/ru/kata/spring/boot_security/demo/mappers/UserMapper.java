package ru.kata.spring.boot_security.demo.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.model.User;


@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User toModel(UserDto dto) {
        return modelMapper.map(dto, User.class);
    }

}