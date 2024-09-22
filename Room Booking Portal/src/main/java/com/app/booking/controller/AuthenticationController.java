package com.app.booking.controller;

import com.app.booking.dto.auth.LoginDto;
import com.app.booking.dto.auth.SignupDto;
import com.app.booking.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDto loginDto) {
        return authenticationService.login(loginDto);
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody SignupDto signupDto) {
        return authenticationService.signup(signupDto);
    }

}
