package org.afs.pakinglot.strategies.parkingStrategy;


import org.afs.pakinglot.domain.ParkingLot;
import org.afs.pakinglot.exception.NoAvailablePositionException;

import java.util.List;

public class SequentiallyStrategy implements ParkingStrategy {

    @Override
    public ParkingLot findParkingLot(List<ParkingLot> parkingLots) {
        return  parkingLots.stream()
                .filter(parkingLot -> !parkingLot.isFull())
                .findFirst()
                .orElseThrow(NoAvailablePositionException::new);
    }
}
