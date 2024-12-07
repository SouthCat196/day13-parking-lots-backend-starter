package org.afs.pakinglot.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
public class Ticket {
    private String plateNumber;
    private int position;
    private int parkingLot;
    private LocalDateTime parkTime;

    public Ticket(String plateNumber, int position, int parkingLot) {
        this.plateNumber = plateNumber;
        this.position = position;
        this.parkingLot = parkingLot;
    }

    public Ticket(String plateNumber, int position, int parkingLot, LocalDateTime parkTime) {
        this.plateNumber = plateNumber;
        this.position = position;
        this.parkingLot = parkingLot;
        this.parkTime = parkTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(plateNumber, ticket.plateNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(plateNumber);
    }
}