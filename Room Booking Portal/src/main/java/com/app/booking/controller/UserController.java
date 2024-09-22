package com.app.booking.controller;

import com.app.booking.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<Object> getUserById(@RequestParam(name = "userID") Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/users")
    public ResponseEntity<Object> getAllUsers() {
        return userService.getAllUsers();
    }


}
