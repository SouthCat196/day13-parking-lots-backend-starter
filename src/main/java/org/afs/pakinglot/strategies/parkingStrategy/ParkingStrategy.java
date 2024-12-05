package org.afs.pakinglot.strategies.parkingStrategy;


import org.afs.pakinglot.domain.ParkingLot;

import java.util.List;

public interface ParkingStrategy {
    ParkingLot findParkingLot(List<ParkingLot> parkingLots);
}
