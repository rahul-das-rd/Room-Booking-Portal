package com.app.booking.dto.booking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * {
 * "roomID": 1,
 * "userID": 1,
 * "dateOfBooking": "2024-04-30",
 * "timeFrom": "03:52:00",
 * "timeTo": "05:00:00",
 * "purpose": "Fun"
 * }
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingUpdateDTO {
    private Long userID;
    private Long roomID;
    private Long bookingID;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBooking;
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime timeFrom;
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime timeTo;
    private String purpose;
}
