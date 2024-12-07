package org.afs.pakinglot.domain;

import org.afs.pakinglot.exception.NoAvailablePositionException;
import org.afs.pakinglot.exception.UnrecognizedTicketException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParkingManagerTest {
    private ParkingManager parkingManager;

    @BeforeEach
    void setUp() {
        parkingManager = new ParkingManager();
    }

    @Test
    void should_return_all_parking_lots_when_get_all_parking_lots() {
        // Given

        // When
        List<ParkingLot> parkingLots = parkingManager.getParkingLots();

        // Then
        assertEquals(3, parkingLots.size());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Standard", "Smart", "Super"})
    void should_park_car_and_return_valid_ticket_when_park_given_strategy(String strategy) {
        // Given
        Car car = new Car(strategy + "123");

        // When
        Ticket ticket = parkingManager.park(car, strategy);

        // Then
        assertNotNull(ticket);
    }

    @Test
    void should_fetch_car_when_fetch_given_valid_ticket() {
        // Given
        Car car = new Car("JKL012");
        Ticket ticket = parkingManager.park(car, "Standard");

        // When
        Car fetchedCar = parkingManager.fetch(ticket).getCar();

        // Then
        assertEquals(car, fetchedCar);
    }

    @Test
    void should_throw_exception_when_fetch_given_invalid_ticket() {
        // Given
        Ticket invalidTicket = new Ticket("XYZ999", 1, 1);

        // When & Then
        assertThrows(UnrecognizedTicketException.class, () -> parkingManager.fetch(invalidTicket));
    }

    @Test
    void should_throw_exception_when_park_given_no_available_position() {
        // Given
        for (int i = 0; i < 30; i++) {
            parkingManager.park(new Car("CAR" + i), "Standard");
        }

        // When & Then
        assertThrows(NoAvailablePositionException.class, () -> parkingManager.park(new Car("OVERFLOW"), "Standard"));
    }
}