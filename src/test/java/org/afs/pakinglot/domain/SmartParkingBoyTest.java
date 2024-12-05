package org.afs.pakinglot.domain;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.afs.pakinglot.strategies.parkingStrategy.MaxAvailableStrategy;
import org.junit.jupiter.api.Test;

class SmartParkingBoyTest {
    @Test
    void should_park_car_to_parking_lot_with_more_empty_positions_when_park_given_2_parking_lots() {
        // Given
        ParkingLot parkingLot1 = new ParkingLot();
        ParkingLot parkingLot2 = new ParkingLot();
        parkingLot1.park(new Car(CarPlateGenerator.generatePlate()));
        ParkingBoy smartParkingBoy = new ParkingBoy(List.of(parkingLot1, parkingLot2), new MaxAvailableStrategy());
        Car car = new Car(CarPlateGenerator.generatePlate());
        // When
        Ticket ticket = smartParkingBoy.park(car);
        // Then
        Car fetchedCar = parkingLot2.fetch(ticket);
        assertEquals(car, fetchedCar);
    }

    @Test
    void should_park_car_to_first_parking_lot_when_park_given_2_parking_lots_have_same_number_of_empty_position() {
        // Given
        ParkingLot parkingLot1 = new ParkingLot();
        ParkingLot parkingLot2 = new ParkingLot();
        parkingLot1.park(new Car(CarPlateGenerator.generatePlate()));
        parkingLot2.park(new Car(CarPlateGenerator.generatePlate()));
        ParkingBoy smartParkingBoy = new ParkingBoy(List.of(parkingLot1, parkingLot2), new MaxAvailableStrategy());
        Car car = new Car(CarPlateGenerator.generatePlate());
        // When
        Ticket ticket = smartParkingBoy.park(car);
        // Then
        Car fetchedCar = parkingLot1.fetch(ticket);
        assertEquals(car, fetchedCar);
    }
}
