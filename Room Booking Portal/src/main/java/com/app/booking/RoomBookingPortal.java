package com.app.booking;

import com.app.booking.model.Room;
import com.app.booking.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RoomBookingPortal implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(RoomBookingPortal.class, args);
    }

    @Autowired
    private RoomRepository repository;

    @Override
    public void run(String... args) throws Exception {
        Room room = new Room();
        room.setRoomName("F103");
        room.setRoomCapacity(2);
        repository.save(room);

    }
}
