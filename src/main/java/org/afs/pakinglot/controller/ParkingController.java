package org.afs.pakinglot.controller;

import org.afs.pakinglot.domain.Car;
import org.afs.pakinglot.domain.ParkingLot;
import org.afs.pakinglot.domain.ParkingManager;
import org.afs.pakinglot.domain.Ticket;

import org.afs.pakinglot.domain.dto.ParkingLotResponse;
import org.afs.pakinglot.domain.dto.TicketResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/parking")
public class ParkingController {

    private final ParkingManager parkingManager = new ParkingManager();

    @GetMapping("/getParkingData")
    public ResponseEntity<List<ParkingLotResponse>> getParkingData() {
        List<ParkingLot> parkingLots = parkingManager.getParkingLots();
        List<ParkingLotResponse> response = parkingLots.stream()
                .map(parkingLot -> new ParkingLotResponse(
                        parkingLot.getId(),
                        parkingLot.getName(),
                        parkingLot.getTickets().stream()
                                .map(ticket -> new TicketResponse(
                                        ticket.plateNumber(),
                                        ticket.position(),
                                        ticket.parkingLot()))
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/park")
    public ResponseEntity<Ticket> park(@RequestBody Car car, @RequestParam String strategy) {
        Ticket ticket = parkingManager.park(car, strategy);
        return ResponseEntity.ok(ticket);
    }

    @PostMapping("/fetch")
    public ResponseEntity<Car> fetch(@RequestBody Ticket ticket) {
        Car car = parkingManager.fetch(ticket);
        return ResponseEntity.ok(car);
    }
}