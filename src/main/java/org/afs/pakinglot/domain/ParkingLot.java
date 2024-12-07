package org.afs.pakinglot.domain;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.afs.pakinglot.exception.NoAvailablePositionException;
import org.afs.pakinglot.exception.UnrecognizedTicketException;

public class ParkingLot {
    private int id;
    private String name;
    private final Map<Ticket, Car> tickets = new HashMap<>();

    private static final int DEFAULT_CAPACITY = 10;
    private final int capacity;

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

    public int getCapacity() {
        return capacity;
    }

    public int getAvailableCapacity() {
        return capacity - tickets.size();
    }

    public Ticket park(Car car) {
        if (isFull()) {
            throw new NoAvailablePositionException();
        }

        int nextPosition = getNextAvailablePosition();
        Ticket ticket = new Ticket(car.plateNumber(), nextPosition, this.id);
        tickets.put(ticket, car);
        return ticket;
    }

    private int getNextAvailablePosition() {
        for (int i = 1; i <= capacity; i++) {
            int finalI = i;
            boolean positionTaken = tickets.keySet().stream()
                .anyMatch(ticket -> ticket.position() == finalI);
            if (!positionTaken) {
                return i;
            }
        }
        throw new NoAvailablePositionException();
    }

    public boolean isFull() {
        return capacity == tickets.size();
    }

    public Car fetch(Ticket ticket) {
        if (!tickets.containsKey(ticket)) {
            throw new UnrecognizedTicketException();
        }

        return tickets.remove(ticket);
    }

    public boolean contains(Ticket ticket) {
        return tickets.containsKey(ticket);
    }

    public double getAvailablePositionRate() {
        return getAvailableCapacity() / (double) capacity;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public List<Ticket> getTickets() {
        return tickets.keySet().stream().toList();
    }

}
