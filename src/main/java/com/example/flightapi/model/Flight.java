package com.example.flightapi.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Flight {
    public Long id;
    public String departure;
    public String arrival;
    public LocalDateTime departureDateTime;
    public LocalDateTime arrivalDateTime;
    public BigDecimal price;
    public String flight_number;
    public LocalDateTime creation_date;

    public Flight() {}

    public Flight(Long id, String departure, String arrival,
                  LocalDateTime departureDateTime, LocalDateTime arrivalDateTime,
                  BigDecimal price, String flight_number, LocalDateTime creation_date) {
        this.id = id;
        this.departure = departure;
        this.arrival = arrival;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.price = price;
        this.flight_number = flight_number;
        this.creation_date = creation_date;
    }
}
