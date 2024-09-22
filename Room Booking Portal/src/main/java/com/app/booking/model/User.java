package com.app.booking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long userID;
    private String name;
    private String email;
    @JsonIgnore
    private String password;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Room> roomsBooked;
}
