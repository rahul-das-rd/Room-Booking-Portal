package com.app.booking.model;

import com.app.booking.dto.booking.BookingUserDTO;
import jakarta.persistence.*;
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
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long bookingID;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBooking;
    @DateTimeFormat(pattern = "HH:mm:ss")

    private LocalTime timeFrom;
    @DateTimeFormat(pattern = "HH:mm:ss")

    private LocalTime timeTo;
    private String purpose;

    @ManyToOne(cascade = CascadeType.ALL)
    private Room room;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    public BookingUserDTO getUser() {
        BookingUserDTO bookingUserDTO = new BookingUserDTO();
        bookingUserDTO.setUserID(this.user.getUserID());
        return bookingUserDTO;
    }
}
