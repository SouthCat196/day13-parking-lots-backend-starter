package org.afs.pakinglot.service;

import org.afs.pakinglot.domain.Car;
import org.afs.pakinglot.domain.Ticket;
import org.afs.pakinglot.domain.dto.FetchRequest;
import org.afs.pakinglot.domain.dto.FetchResponse;
import org.afs.pakinglot.domain.dto.ParkingLotResponse;
import org.afs.pakinglot.domain.dto.TicketResponse;
import org.afs.pakinglot.domain.ParkingManager;
import org.afs.pakinglot.domain.ParkingLot;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingService {

    private final ParkingManager parkingManager;

    public ParkingService(ParkingManager parkingManager) {
        this.parkingManager = parkingManager;
    }

    public List<ParkingLotResponse> getParkingData() {
        List<ParkingLot> parkingLots = parkingManager.getParkingLots();
        return parkingLots.stream()
                .map(parkingLot -> new ParkingLotResponse(
                        parkingLot.getId(),
                        parkingLot.getName(),
                        parkingLot.getCapacity(),
                        parkingLot.getTickets().stream()
                                .map(ticket -> new TicketResponse(
                                        ticket.getPlateNumber(),
                                        ticket.getPosition(),
                                        ticket.getParkingLot()))
                                .toList()))
                .toList();
    }

    public Ticket park(Car car, String strategy) {
        return parkingManager.park(car, strategy);
    }

    public FetchResponse fetch(FetchRequest fetchRequest) {
        Ticket ticket = new Ticket(fetchRequest.getPlateNumber(), fetchRequest.getPosition(), fetchRequest.getParkingLot(), null);
        return parkingManager.fetch(ticket);
    }
}