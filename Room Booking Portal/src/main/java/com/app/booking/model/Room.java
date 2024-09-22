package com.app.booking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long roomID;
    private String roomName;
    private int roomCapacity;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Booking> booked;
}
