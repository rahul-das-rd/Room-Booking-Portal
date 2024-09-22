package com.app.booking.service;

import com.app.booking.dto.error.ErrorResponse;
import com.app.booking.dto.user.UserResponseDto;
import com.app.booking.repository.UserRepository;
import com.app.booking.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public ResponseEntity<Object> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            UserResponseDto userResponseDto = new UserResponseDto();
            userResponseDto.setUserID(user.get().getUserID());
            userResponseDto.setName(user.get().getName());
            userResponseDto.setEmail(user.get().getEmail());
            return ResponseEntity.ok(userResponseDto);
        } else {
            return ResponseEntity.ok(new ErrorResponse("User does not exist"));
        }
    }


    public ResponseEntity<Object> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponseDto> userResponseDtosList = new ArrayList<>();
        for (User user : users) {
            UserResponseDto userResponseDto = new UserResponseDto();
            userResponseDto.setUserID(user.getUserID());
            userResponseDto.setName(user.getName());
            userResponseDto.setEmail(user.getEmail());

            userResponseDtosList.add(userResponseDto);
        }
        return new ResponseEntity<>(userResponseDtosList, HttpStatusCode.valueOf(200));
    }
}
