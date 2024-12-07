package org.afs.pakinglot.controller;

import org.afs.pakinglot.domain.Car;
import org.afs.pakinglot.domain.Ticket;
import org.afs.pakinglot.domain.dto.FetchRequest;
import org.afs.pakinglot.domain.dto.FetchResponse;
import org.afs.pakinglot.domain.dto.ParkingLotResponse;
import org.afs.pakinglot.service.ParkingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking")
public class ParkingController {

    private final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
        init();
    }

    private void init() {
        parkingService.park(new Car("ABC1234"), "Standard");
        parkingService.park(new Car("TPC5131"), "Standard");
        parkingService.park(new Car("JKL5123"), "Standard");
        parkingService.park(new Car("QQE1512"), "Smart");
        parkingService.park(new Car("GNA5129"), "Smart");
        parkingService.park(new Car("GASPD12"), "Smart");
        parkingService.park(new Car("GOJASD4"), "Super");
        parkingService.park(new Car("D1293AS"), "Super");
        parkingService.park(new Car("FJ121AD"), "Super");
    }

    @GetMapping("/getParkingData")
    public ResponseEntity<List<ParkingLotResponse>> getParkingData() {
        return ResponseEntity.ok(parkingService.getParkingData());
    }

    @PostMapping("/park")
    public ResponseEntity<Ticket> park(@RequestBody Car car, @RequestParam String strategy) {
        return ResponseEntity.ok(parkingService.park(car, strategy));
    }

    @PostMapping("/fetch")
    public ResponseEntity<FetchResponse> fetch(@RequestBody FetchRequest fetchRequest) {
        return ResponseEntity.ok(parkingService.fetch(fetchRequest));
    }
}