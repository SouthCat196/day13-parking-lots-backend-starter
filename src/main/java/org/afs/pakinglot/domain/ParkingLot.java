package org.afs.pakinglot.domain;

import lombok.Data;
import org.afs.pakinglot.domain.dto.FetchResponse;
import org.afs.pakinglot.exception.CarAlreadyParkedException;
import org.afs.pakinglot.exception.NoAvailablePositionException;
import org.afs.pakinglot.exception.UnrecognizedTicketException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ParkingLot {
    public static final int TOLL_CYCLE = 14;
    public static final double TOLL_CYCLE_FEE = 4.0;
    private int id;
    private String name;
    private final Map<Ticket, Car> tickets = new HashMap<>();

    private static final int DEFAULT_CAPACITY = 10;
    private final int capacity;

    private static final double FEE_PER_HOUR = 5.0;

    public ParkingLot() {
        this(DEFAULT_CAPACITY);
    }

    public ParkingLot(int capacity) {
        this.capacity = capacity;
    }

    public ParkingLot(int id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
    }

    public int getAvailableCapacity() {
        return capacity - tickets.size();
    }

    public Ticket park(Car car) {
        if (isFull()) {
            throw new NoAvailablePositionException();
        }
        if (tickets.values().stream().anyMatch(parkedCar -> parkedCar.plateNumber().equals(car.plateNumber()))) {
            throw new CarAlreadyParkedException();
        }
        int nextPosition = getNextAvailablePosition();
        Ticket ticket = new Ticket(car.plateNumber(), nextPosition, this.id, LocalDateTime.now());
        tickets.put(ticket, car);
        return ticket;
    }

    private int getNextAvailablePosition() {
        for (int i = 1; i <= capacity; i++) {
            int finalI = i;
            boolean positionTaken = tickets.keySet().stream()
                    .anyMatch(ticket -> ticket.getPosition() == finalI);
            if (!positionTaken) {
                return i;
            }
        }
        throw new NoAvailablePositionException();
    }

    public boolean isFull() {
        return capacity == tickets.size();
    }

    public FetchResponse fetch(Ticket ticket) {
        Ticket existTicket = tickets.keySet().stream()
                .filter(t -> t.equals(ticket))
                .findFirst()
                .orElse(null);

        if (existTicket == null) {
            throw new UnrecognizedTicketException();
        }

        Car fetchedCar = tickets.remove(existTicket);
        Duration parkingDuration = Duration.between(existTicket.getParkTime(), LocalDateTime.now());
        double parkingFee = calculateParkingFee(parkingDuration);
        return new FetchResponse(fetchedCar, parkingDuration.toMinutes(), parkingFee);
    }

    private double calculateParkingFee(Duration duration) {
        long minutes = duration.toMinutes();
        long quarters = (minutes + TOLL_CYCLE - 1) / TOLL_CYCLE;
        return quarters * TOLL_CYCLE_FEE;
    }

    public boolean contains(Ticket ticket) {
        return tickets.containsKey(ticket);
    }

    public double getAvailablePositionRate() {
        return getAvailableCapacity() / (double) capacity;
    }

    public List<Ticket> getTickets() {
        return tickets.keySet().stream().toList();
    }

}