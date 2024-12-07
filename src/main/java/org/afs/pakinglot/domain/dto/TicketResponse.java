package org.afs.pakinglot.domain.dto;

import java.time.LocalDateTime;

public record TicketResponse(String plateNumber, int position, int parkingLot) {
}
