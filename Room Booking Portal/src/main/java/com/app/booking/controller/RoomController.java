package com.app.booking.controller;

import com.app.booking.model.Room;
import com.app.booking.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/rooms")
    public ResponseEntity<?> roomsList(@RequestParam(name = "capacity", required = false) Integer capacity) {
        return roomService.getAllRooms(capacity);
    }


    @PostMapping("/rooms")
    public ResponseEntity<?> createRoom(@RequestBody Room room) {
        return roomService.createRoom(room);
    }

    @PatchMapping("/rooms")
    public ResponseEntity<?> updateRoom(@RequestBody Room room) {
        return roomService.updateRoom(room);
    }


    @DeleteMapping("/rooms")
    public ResponseEntity<?> deleteRoom(@RequestParam(name = "roomID") Long roomID) {
        return roomService.deleteRoom(roomID);
    }

}
