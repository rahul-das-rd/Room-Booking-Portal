package com.app.booking.dto.room;

import com.app.booking.dto.booking.BookingResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponseDTO {
    private Long roomID;
    private String roomName;
    private int roomCapacity;
    private List<BookingResponseDTO> booked;

}
