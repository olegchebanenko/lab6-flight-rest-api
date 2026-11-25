package com.example.flightapi.controller;

import com.example.flightapi.model.Flight;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/flights")
public class FlightController {
    private static final List<Flight> flights = new CopyOnWriteArrayList<>(List.of(
        new Flight(1L, "Warsaw", "London",
            LocalDateTime.parse("2026-12-10T08:00:00"),
            LocalDateTime.parse("2026-12-10T10:30:00"),
            new BigDecimal("150.00"), "LO281", LocalDateTime.now()),
        new Flight(2L, "Paris", "Rome",
            LocalDateTime.parse("2026-12-11T12:00:00"),
            LocalDateTime.parse("2026-12-11T14:10:00"),
            new BigDecimal("120.00"), "AF1004", LocalDateTime.now()),
        new Flight(3L, "Berlin", "Madrid",
            LocalDateTime.parse("2026-12-12T09:00:00"),
            LocalDateTime.parse("2026-12-12T12:15:00"),
            new BigDecimal("180.00"), "IB1802", LocalDateTime.now())
    ));
    private static final AtomicLong nextId = new AtomicLong(4);

    @GetMapping
    public ResponseEntity<List<Flight>> getAllFlights() {
        return ResponseEntity.ok(List.copyOf(flights));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable("id") Long id) {
        return flights.stream()
            .filter(flight -> flight.id.equals(id))
            .findFirst()
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Flight> createFlight(@RequestBody Flight request) {
        if (request.departure == null || request.departure.isBlank()
                || request.arrival == null || request.arrival.isBlank()
                || request.departureDateTime == null || request.arrivalDateTime == null
                || request.price == null || request.price.signum() < 0
                || request.flight_number == null || request.flight_number.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        Flight flight = new Flight(nextId.getAndIncrement(), request.departure,
            request.arrival, request.departureDateTime, request.arrivalDateTime,
            request.price, request.flight_number, LocalDateTime.now());
        flights.add(flight);
        return ResponseEntity.created(URI.create("/flights/" + flight.id)).body(flight);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable("id") Long id) {
        boolean removed = flights.removeIf(flight -> flight.id.equals(id));
        return removed ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }
}
