package com.app.booking.repository;

import com.app.booking.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByRoomName(String roomName);

    List<Room> findByRoomCapacity(int roomCapacity);
}
