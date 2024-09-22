package com.app.booking.service;

import com.app.booking.dto.booking.BookingRequestDTO;
import com.app.booking.dto.booking.BookingUpdateDTO;
import com.app.booking.dto.error.ErrorResponse;
import com.app.booking.dto.history.HistoryResponseDTO;
import com.app.booking.model.Booking;
import com.app.booking.model.Room;
import com.app.booking.model.User;
import com.app.booking.repository.BookingRepository;
import com.app.booking.repository.RoomRepository;
import com.app.booking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    public ResponseEntity<Object> userBookingHistory(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return ResponseEntity.ok(new ErrorResponse("User does not exist"));
        } else {
            List<HistoryResponseDTO> historyResponseDTOList = new ArrayList<>();
            List<Room> bookedRoomsByUser = user.get().getRoomsBooked();

            for (Room room : bookedRoomsByUser) {
                List<Booking> bookings = room.getBooked();
                for (Booking booking : bookings) {
                    HistoryResponseDTO historyResponseDTO = getHistoryResponseDTO(room, booking);
                    historyResponseDTOList.add(historyResponseDTO);
                }
            }
            return ResponseEntity.ok(historyResponseDTOList);
        }
    }

    private static HistoryResponseDTO getHistoryResponseDTO(Room room, Booking booking) {
        HistoryResponseDTO historyResponseDTO = new HistoryResponseDTO();
        historyResponseDTO.setRoomID(room.getRoomID());
        historyResponseDTO.setBookingID(booking.getBookingID());
        historyResponseDTO.setDateOfBooking(booking.getDateOfBooking());
        historyResponseDTO.setTimeFrom(booking.getTimeFrom());
        historyResponseDTO.setTimeTo(booking.getTimeTo());
        historyResponseDTO.setPurpose(booking.getPurpose());
        historyResponseDTO.setUser(booking.getUser());
        return historyResponseDTO;
    }


    public ResponseEntity<Object> userUpcomingBookings(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return ResponseEntity.ok(new ErrorResponse("User does not exist"));
        } else {
            List<Booking> upcomingBookings = bookingRepository.findAllByUserAndDateOfBookingAfter(user.get(), LocalDate.now());
            List<HistoryResponseDTO> historyResponseDTOList = new ArrayList<>();

            for (Booking booking : upcomingBookings) {
                HistoryResponseDTO historyResponseDTO = getHistoryResponseDTO(booking.getRoom(), booking);
                historyResponseDTOList.add(historyResponseDTO);
            }
            return ResponseEntity.ok(historyResponseDTOList);
        }
    }


    public ResponseEntity<Object> newBooking(BookingRequestDTO bookingRequestDTO) {
        Optional<User> user = userRepository.findById(bookingRequestDTO.getUserID());
        if (user.isEmpty()) {
            return ResponseEntity.ok(new ErrorResponse("User does not exist"));
        } else {
            Optional<Room> room = roomRepository.findById(bookingRequestDTO.getRoomID());
            if (room.isEmpty()) {
                return ResponseEntity.ok(new ErrorResponse("Room does not exist"));
            } else {
                if (!isValidDateTime(bookingRequestDTO.getDateOfBooking(), bookingRequestDTO.getTimeFrom(), bookingRequestDTO.getTimeTo())) {
                    return ResponseEntity.ok(new ErrorResponse("Invalid date/time"));
                } else {
                    if (isRoomUnavailable(room.get(), bookingRequestDTO.getDateOfBooking(), bookingRequestDTO.getTimeFrom(), bookingRequestDTO.getTimeTo())) {
                        return ResponseEntity.ok(new ErrorResponse("Room unavailable"));
                    } else {
                        Booking booking = new Booking();
                        booking.setDateOfBooking(bookingRequestDTO.getDateOfBooking());
                        booking.setTimeFrom(bookingRequestDTO.getTimeFrom());
                        booking.setTimeTo(bookingRequestDTO.getTimeTo());
                        booking.setPurpose(bookingRequestDTO.getPurpose());
                        booking.setRoom(room.get());

                        booking.setUser(user.get());

                        bookingRepository.save(booking);
                        room.get().getBooked().add(booking);
                        roomRepository.save(room.get());


                        user.get().getRoomsBooked().add(room.get());
                        userRepository.save(user.get());
                        return ResponseEntity.ok("Booking created successfully");
                    }
                }
            }
        }
    }

    private boolean isValidDateTime(LocalDate dateOfBooking, LocalTime timeFrom, LocalTime timeTo) {
        if (dateOfBooking.isBefore(LocalDate.now()) || timeFrom.isAfter(timeTo)) {
            return false;
        }
        return true;
    }

    private boolean isRoomUnavailable(Room room, LocalDate dateOfBooking, LocalTime timeFrom, LocalTime timeTo) {
        List<Booking> bookings = room.getBooked();
        for (Booking booking : bookings) {
            if (booking.getDateOfBooking().equals(dateOfBooking)) {
                if (timeFrom.isBefore(booking.getTimeTo()) && timeTo.isAfter(booking.getTimeFrom())) {
                    return true; // room is unavailable
                }
            }
        }
        return false; // room is available
    }


    /**
     * To edit an existing booking: Method: PATCH
     * Body: -userID<int>
     * -roomID<int> -bookingID<int> -dateOfBooking<date> -timeFrom<str> -timeTo<str> -purpose<str>
     * Response: (Any of the following)
     * ● Booking modified successfully
     * ● Room unavailable
     * ● Room does not exist
     * ● User does not exist
     * ● Invalid date/time
     */

    public ResponseEntity<Object> editBooking(BookingUpdateDTO bookingUpdateDTO) {
        Optional<User> user = userRepository.findById(bookingUpdateDTO.getUserID());
        if (user.isEmpty()) {
            return ResponseEntity.ok(new ErrorResponse("User does not exist"));
        } else {
            Optional<Room> room = roomRepository.findById(bookingUpdateDTO.getRoomID());
            if (room.isEmpty()) {
                return ResponseEntity.ok(new ErrorResponse("Room does not exist"));
            } else {
                Optional<Booking> booking = bookingRepository.findById(bookingUpdateDTO.getBookingID());
                if (booking.isEmpty()) {
                    return ResponseEntity.ok(new ErrorResponse("Booking does not exist"));
                } else {
                    if (!isValidDateTime(bookingUpdateDTO.getDateOfBooking(), bookingUpdateDTO.getTimeFrom(), bookingUpdateDTO.getTimeTo())) {
                        return ResponseEntity.ok(new ErrorResponse("Invalid date/time"));
                    } else {
                        if (isRoomUnavailable(room.get(), bookingUpdateDTO.getDateOfBooking(), bookingUpdateDTO.getTimeFrom(), bookingUpdateDTO.getTimeTo())) {
                            return ResponseEntity.ok(new ErrorResponse("Room unavailable"));
                        } else {
                            booking.get().setDateOfBooking(bookingUpdateDTO.getDateOfBooking());
                            booking.get().setTimeFrom(bookingUpdateDTO.getTimeFrom());
                            booking.get().setTimeTo(bookingUpdateDTO.getTimeTo());
                            booking.get().setPurpose(bookingUpdateDTO.getPurpose());
                            booking.get().setRoom(room.get());
                            booking.get().setUser(user.get());
                            bookingRepository.save(booking.get());
                            return ResponseEntity.ok("Booking modified successfully");
                        }
                    }
                }
            }
        }

    }


    public ResponseEntity<?> deleteBooking(Long bookingID) {
        Optional<Booking> booking = bookingRepository.findById(bookingID);
        if (booking.isEmpty()) {
            return ResponseEntity.ok(new ErrorResponse("Booking does not exist"));
        } else {
            bookingRepository.delete(booking.get());
            return ResponseEntity.ok("Booking deleted successfully");
        }
    }

}
