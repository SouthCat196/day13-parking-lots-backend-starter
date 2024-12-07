package org.afs.pakinglot.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class ParkingLotResponse {
    private int id;
    private String name;
    private List<TicketResponse> tickets;
    private int capacity;

    public ParkingLotResponse(int id, String name, int capacity, List<TicketResponse> tickets) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.tickets = tickets;
    }
}