package com.app.booking.service;

import com.app.booking.dto.auth.LoginDto;
import com.app.booking.dto.auth.SignupDto;
import com.app.booking.dto.error.ErrorResponse;
import com.app.booking.repository.UserRepository;
import com.app.booking.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;

    public ResponseEntity<Object> login(LoginDto loginDto) {
        Optional<User> user = userRepository.findByEmail(loginDto.getEmail()); // <>
        if (user.isEmpty()) {
            return ResponseEntity.ok(new ErrorResponse("User does not exist"));
        } else if (user.get().getPassword() != null && user.get().getPassword().equals(loginDto.getPassword())) {
            return ResponseEntity.ok("Login Successful");
        } else {
            return ResponseEntity.ok(new ErrorResponse("Username/Password Incorrect"));
        }
    }

    public ResponseEntity<Object> signup(SignupDto signupDto) {
        Optional<User> existingUser = userRepository.findByEmail(signupDto.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.ok(new ErrorResponse("Forbidden, Account already exists"));
        }
        User user = new User();
        user.setName(signupDto.getName());
        user.setEmail(signupDto.getEmail());
        user.setPassword(signupDto.getPassword());
        userRepository.save(user);
        return ResponseEntity.ok("Account Creation Successful");
    }
}
