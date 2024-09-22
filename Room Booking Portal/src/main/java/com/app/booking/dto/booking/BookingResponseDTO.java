package com.app.booking.dto.booking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDTO {
    private Long bookingID;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBooking;
    @DateTimeFormat(pattern = "HH:mm:ss")

    private LocalTime timeFrom;
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime timeTo;
    private String purpose;
    BookingUserDTO user;
}
