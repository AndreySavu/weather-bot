package com.example.weather_bot.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "user_location")
public class UserLocation {

    @Id
    private Long id;

    @Column(name = "user_id")
    private long userId;

    private List<String> location = new ArrayList<>();
}
