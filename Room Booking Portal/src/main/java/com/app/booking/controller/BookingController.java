package com.app.booking.controller;

import com.app.booking.dto.booking.BookingRequestDTO;
import com.app.booking.dto.booking.BookingUpdateDTO;
import com.app.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/history")
    public ResponseEntity<Object> userBookingHistory(@RequestParam(name = "userID") Long id) {
        return bookingService.userBookingHistory(id);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<Object> userUpcomingBookings(@RequestParam(name = "userID") Long id) {
        return bookingService.userUpcomingBookings(id);
    }

    @PostMapping("/book")
    public ResponseEntity<?> newBooking(@RequestBody BookingRequestDTO bookingRequestDTO) {
        return bookingService.newBooking(bookingRequestDTO);
    }

    @PatchMapping("/book")
    public ResponseEntity<?> editBooking(@RequestBody BookingUpdateDTO bookingUpdateDTO) {
        return bookingService.editBooking(bookingUpdateDTO);
    }

    @DeleteMapping("/book")
    public ResponseEntity<?> deleteBooking(@RequestParam(name = "bookingID") Long bookingID) {
        return bookingService.deleteBooking(bookingID);
    }

}
