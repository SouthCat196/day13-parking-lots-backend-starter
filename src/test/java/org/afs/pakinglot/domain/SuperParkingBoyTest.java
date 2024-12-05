package org.afs.pakinglot.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.afs.pakinglot.strategies.parkingStrategy.AvailableRateStrategy;
import org.junit.jupiter.api.Test;

class SuperParkingBoyTest {
    @Test
    void should_park_into_the_first_parking_lot_when_park_given_two_parking_lots_with_same_available_capacity_rate() {
        // Given
        ParkingLot parkingLot1 = new ParkingLot();
        ParkingLot parkingLot2 = new ParkingLot();
        ParkingBoy superParkingBoy = new ParkingBoy(List.of(parkingLot1, parkingLot2), new AvailableRateStrategy());
        Car car = new Car(CarPlateGenerator.generatePlate());

        // When
        Ticket ticket = superParkingBoy.park(car);
        // Then
        Car fetchedCar = parkingLot1.fetch(ticket);
        assertEquals(car, fetchedCar);
    }

    @Test
    void should_park_into_the_the_parking_lot_which_have_big_available_capacity_rate() {
        // Given
        ParkingLot parkingLot1 = new ParkingLot(3);
        ParkingLot parkingLot2 = new ParkingLot(2);
        parkingLot1.park(new Car(CarPlateGenerator.generatePlate()));
        parkingLot1.park(new Car(CarPlateGenerator.generatePlate())); // rate = 1/3
        parkingLot2.park(new Car(CarPlateGenerator.generatePlate())); // rate = 1/2

        ParkingBoy superParkingBoy = new ParkingBoy(List.of(parkingLot1, parkingLot2), new AvailableRateStrategy());
        Car car = new Car(CarPlateGenerator.generatePlate());
        // When
        Ticket ticket = superParkingBoy.park(car);
        // Then
        Car fetchedCar = parkingLot2.fetch(ticket);
        assertEquals(car, fetchedCar);
    }
}