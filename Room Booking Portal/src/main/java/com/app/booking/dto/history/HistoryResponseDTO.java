package com.app.booking.dto.history;

import com.app.booking.dto.booking.BookingUserDTO;
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
public class HistoryResponseDTO {
    private Long roomID;
    private Long bookingID;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBooking;
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime timeFrom;
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime timeTo;
    private String purpose;
    private BookingUserDTO user;

}
