package com.erp.user.service;

import com.erp.user.entity.User;
import com.erp.user.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String username, String email, String rawPassword, String role) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Bu kullanıcı adı zaten kayıtlı: " + username);
        }
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Bu email zaten kayıtlı: " + email);
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(rawPassword));
        user.setRole(role);
        return userRepository.save(user);
    }

    // Express'teki gibi düşün: username ile kullanıcıyı bul, bcrypt.compare ile şifreyi doğrula
    public User authenticate(String username, String rawPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadCredentialsException("Kullanıcı adı veya şifre hatalı"));

        if (!passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
            throw new BadCredentialsException("Kullanıcı adı veya şifre hatalı");
        }

        return user;
    }
}