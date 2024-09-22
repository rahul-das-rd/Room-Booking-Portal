package com.app.booking.service;

import com.app.booking.dto.booking.BookingResponseDTO;
import com.app.booking.dto.error.ErrorResponse;
import com.app.booking.dto.room.RoomResponseDTO;
import com.app.booking.model.Booking;
import com.app.booking.model.Room;
import com.app.booking.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;

    public ResponseEntity<?> createRoom(Room room) {
        Optional<Room> existingRoom = roomRepository.findByRoomName(room.getRoomName());
        if (existingRoom.isPresent()) {
            return ResponseEntity.ok(new ErrorResponse("Room already exists"));
        } else if (room.getRoomCapacity() < 0) {
            return ResponseEntity.ok(new ErrorResponse("Invalid capacity"));
        } else {
            roomRepository.save(room);
            return ResponseEntity.ok("Room created successfully");
        }
    }


    public ResponseEntity<?> updateRoom(Room room) {
        Optional<Room> existingRoom = roomRepository.findById(room.getRoomID());
        if (existingRoom.isEmpty()) {
            return ResponseEntity.ok(new ErrorResponse("Room does not exist"));
        } else if (room.getRoomCapacity() < 0) {
            return ResponseEntity.ok(new ErrorResponse("Invalid capacity"));
        } else {
            if (existingRoom.get().getRoomName().equalsIgnoreCase(room.getRoomName()) && !Objects.equals(existingRoom.get().getRoomID(), room.getRoomID())) {
                return ResponseEntity.ok(new ErrorResponse("Room already exists"));
            }

            existingRoom.get().setRoomCapacity(room.getRoomCapacity());
            existingRoom.get().setRoomName(room.getRoomName());
            roomRepository.save(existingRoom.get());
            return ResponseEntity.ok("Room edited successfully");
        }
    }


    public ResponseEntity<?> deleteRoom(Long roomID) {
        Optional<Room> existingRoom = roomRepository.findById(roomID);
        if (existingRoom.isEmpty()) {
            return ResponseEntity.ok(new ErrorResponse("Room does not exist"));
        } else {
            roomRepository.delete(existingRoom.get());
            return ResponseEntity.ok("Room deleted successfully");
        }
    }


    public ResponseEntity<?> getAllRooms(Integer capacity) {
        List<RoomResponseDTO> roomResponseDTOList = new ArrayList<>();
        List<Room> rooms = new ArrayList<>();
        if (capacity == null) {
            rooms = roomRepository.findAll();
        } else {
            rooms = roomRepository.findByRoomCapacity(capacity);
        }
        rooms.forEach(room -> {
            List<BookingResponseDTO> allRoomBookings = new ArrayList<>();
            RoomResponseDTO roomResponseDTO = new RoomResponseDTO();
            roomResponseDTO.setRoomID(room.getRoomID());
            roomResponseDTO.setRoomName(room.getRoomName());
            roomResponseDTO.setRoomCapacity(room.getRoomCapacity());
            for (Booking booking : room.getBooked()) {
                BookingResponseDTO bookingResponseDTO = getBookingResponseDTO(booking);
                allRoomBookings.add(bookingResponseDTO);
            }
            roomResponseDTO.setBooked(allRoomBookings);
            roomResponseDTOList.add(roomResponseDTO);

        });
        return new ResponseEntity<>(roomResponseDTOList, HttpStatus.OK);
    }

    private static BookingResponseDTO getBookingResponseDTO(Booking booking) {
        BookingResponseDTO bookingResponseDTO = new BookingResponseDTO();
        bookingResponseDTO.setBookingID(booking.getBookingID());
        bookingResponseDTO.setDateOfBooking(booking.getDateOfBooking());
        bookingResponseDTO.setTimeFrom(booking.getTimeFrom());
        bookingResponseDTO.setTimeTo(booking.getTimeTo());
        bookingResponseDTO.setPurpose(booking.getPurpose());
        bookingResponseDTO.setUser(booking.getUser());
        return bookingResponseDTO;
    }


}
