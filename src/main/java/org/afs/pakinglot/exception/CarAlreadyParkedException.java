package org.afs.pakinglot.exception;

public class CarAlreadyParkedException extends RuntimeException {
    public CarAlreadyParkedException() {
        super("This car is already parked.");
    }
}
