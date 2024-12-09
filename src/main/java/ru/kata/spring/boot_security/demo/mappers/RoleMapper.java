package ru.kata.spring.boot_security.demo.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dto.RoleDto;
import ru.kata.spring.boot_security.demo.model.Role;

@Component
public class RoleMapper {

    private final ModelMapper modelMapper;

    public RoleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Role toModel(RoleDto dto) {
        return modelMapper.map(dto, Role.class);
    }


}
