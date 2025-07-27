package com.example.userauthservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.userauthservice.controllers.AuthController;
import com.example.userauthservice.dtos.SignupRequest;
import com.example.userauthservice.dtos.UserDto;
import com.example.userauthservice.models.User;
import com.example.userauthservice.exceptions.UserAlreadyExistException;
import com.example.userauthservice.services.IAuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IAuthService authService;

    @InjectMocks
    private AuthController authController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    public void testSignup_Success() throws Exception {

        SignupRequest request = new SignupRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");

        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");


        when(authService.signup("test@example.com", "password123")).thenReturn(user);


        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    public void testSignup_UserAlreadyExists() throws Exception {

        SignupRequest request = new SignupRequest();
        request.setEmail("existing@example.com");
        request.setPassword("password123");


        when(authService.signup("existing@example.com", "password123"))
                .thenThrow(new UserAlreadyExistException("User already exists"));


        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSignup_InvalidInput() throws Exception {

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").value("Email is required"))
                .andExpect(jsonPath("$.password").value("Password is required"));
    }

    @Test
    public void testSignup_InvalidEmail() throws Exception {

        SignupRequest request = new SignupRequest();
        request.setEmail("invalid-email");
        request.setPassword("password123");

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").value("Email should be valid"));
    }

    @Test
    public void testSignup_ShortPassword() throws Exception {

        SignupRequest request = new SignupRequest();
        request.setEmail("test@example.com");
        request.setPassword("short");

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password").value("Password must be at least 6 characters long"));
    }


    private UserDto from(User user) {
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        if (user.getId() != null) {
            userDto.setId(user.getId());
        }
        return userDto;
    }
}
