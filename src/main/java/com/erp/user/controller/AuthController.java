package com.erp.user.controller;

import com.erp.common.security.JwtTokenProvider;
import com.erp.user.dto.AuthResponseDto;
import com.erp.user.dto.LoginRequestDto;
import com.erp.user.dto.RegisterRequestDto;
import com.erp.user.entity.User;
import com.erp.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponseDto register(@Valid @RequestBody RegisterRequestDto dto) {
        User user = userService.register(dto.getUsername(), dto.getEmail(), dto.getPassword(), "ROLE_USER");
        String token = jwtTokenProvider.generateToken(user.getUsername());
        return new AuthResponseDto(token, user.getUsername(), user.getRole());
    }

    @PostMapping("/login")
    public AuthResponseDto login(@Valid @RequestBody LoginRequestDto dto) {
        User user = userService.authenticate(dto.getUsername(), dto.getPassword());
        String token = jwtTokenProvider.generateToken(user.getUsername());
        return new AuthResponseDto(token, user.getUsername(), user.getRole());
    }
}