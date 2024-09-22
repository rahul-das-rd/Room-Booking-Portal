package com.app.booking.repository;

import com.app.booking.model.Booking;
import com.app.booking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByOrderByDateOfBookingDesc();

    List<Booking> findAllByUserAndDateOfBookingAfter(User user, LocalDate date);

    List<Booking> findAllByUser(User user);
}
