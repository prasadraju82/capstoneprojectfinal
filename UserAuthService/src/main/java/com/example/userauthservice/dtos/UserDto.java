package com.example.userauthservice.dtos;

import lombok.Getter;
import lombok.Setter;
import com.example.userauthservice.models.Role;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class UserDto {
    private Long id;
    private String email;
    // private List<Role> roles = new ArrayList();
}

