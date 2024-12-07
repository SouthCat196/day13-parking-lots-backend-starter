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

@RestController
@RequestMapping("/parking")
public class ParkingController {

    private final ParkingManager parkingManager;

    public ParkingController(ParkingManager parkingManager) {
        this.parkingManager = parkingManager;
        init();
    }

    private void init() {
        parkingManager.park(new Car("ABC1234"), "Standard");
        parkingManager.park(new Car("TPC5131"), "Standard");
        parkingManager.park(new Car("JKL5123"), "Standard");
        parkingManager.park(new Car("QQE1512"), "Smart");
        parkingManager.park(new Car("GNA5129"), "Smart");
        parkingManager.park(new Car("GASPD12"), "Smart");
        parkingManager.park(new Car("GOJASD4"), "Super");
        parkingManager.park(new Car("D1293AS"), "Super");
        parkingManager.park(new Car("FJ121AD"), "Super");
    }


    @GetMapping("/getParkingData")
    public ResponseEntity<List<ParkingLotResponse>> getParkingData() {
        List<ParkingLot> parkingLots = parkingManager.getParkingLots();
        List<ParkingLotResponse> response = parkingLots.stream()
                .map(parkingLot -> new ParkingLotResponse(
                        parkingLot.getId(),
                        parkingLot.getName(),
                        parkingLot.getCapacity(),
                        parkingLot.getTickets().stream()
                                .map(ticket -> new TicketResponse(
                                        ticket.plateNumber(),
                                        ticket.position(),
                                        ticket.parkingLot()))
                                .toList()))
                .toList();
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