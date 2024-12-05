package org.afs.pakinglot.strategies.parkingStrategy;


import org.afs.pakinglot.domain.ParkingLot;
import org.afs.pakinglot.exception.NoAvailablePositionException;

import java.util.Comparator;
import java.util.List;

public class MaxAvailableStrategy implements ParkingStrategy{
    @Override
    public ParkingLot findParkingLot(List<ParkingLot> parkingLots) {
        return parkingLots.stream()
                .max(Comparator.comparingInt(ParkingLot::getAvailableCapacity))
                .orElseThrow(NoAvailablePositionException::new);
    }
}
