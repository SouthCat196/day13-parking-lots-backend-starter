package org.afs.pakinglot.domain;

import lombok.Data;
import org.afs.pakinglot.exception.UnrecognizedTicketException;
import org.afs.pakinglot.strategies.parkingStrategy.AvailableRateStrategy;
import org.afs.pakinglot.strategies.parkingStrategy.MaxAvailableStrategy;
import org.afs.pakinglot.strategies.parkingStrategy.SequentiallyStrategy;

import java.util.List;
import java.util.Optional;

@Data
public class ParkingManager {
    private static final String STANDARD = "Standard";
    private static final String SMART = "Smart";
    private static final String SUPER = "Super";

    private final List<ParkingLot> parkingLots;
    private final List<ParkingBoy> parkingBoys;

    public ParkingManager() {
        parkingLots = List.of(
                new ParkingLot(1, "The Plaza Park", 9),
                new ParkingLot(2, "City Mall Garage", 12),
                new ParkingLot(3, "Office Tower Parking", 9)
        );

        parkingLots.get(0).park(new Car("ABC123"));
        parkingLots.get(1).park(new Car("XYZ789"));
        parkingLots.get(2).park(new Car("LMN456"));

        parkingBoys = List.of(
                new ParkingBoy(parkingLots, new SequentiallyStrategy()),
                new ParkingBoy(parkingLots, new MaxAvailableStrategy()),
                new ParkingBoy(parkingLots, new AvailableRateStrategy())
        );
    }

    public Ticket park(Car car, String strategyType) {
        ParkingBoy parkingBoy = getParkingBoyByStrategy(strategyType);
        return parkingBoy.park(car);
    }

    public Car fetch(Ticket ticket) {
        Optional<ParkingLot> parkingLot = parkingLots.stream()
                .filter(lot -> lot.contains(ticket))
                .findFirst();

        if (parkingLot.isPresent()) {
            return parkingLot.get().fetch(ticket);
        } else {
            throw new UnrecognizedTicketException();
        }
    }

    private ParkingBoy getParkingBoyByStrategy(String strategyType) {
        return switch (strategyType) {
            case STANDARD -> parkingBoys.get(0);
            case SMART -> parkingBoys.get(1);
            case SUPER -> parkingBoys.get(2);
            default -> throw new IllegalArgumentException("Invalid strategy type: " + strategyType);
        };
    }
}