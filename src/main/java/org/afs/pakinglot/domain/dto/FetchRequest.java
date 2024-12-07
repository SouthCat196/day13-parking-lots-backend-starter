package org.afs.pakinglot.domain.dto;

public class FetchRequest {
    private final String plateNumber;
    private final int position;
    private final int parkingLot;

    public FetchRequest(String plateNumber, int position, int parkingLot) {
        this.plateNumber = plateNumber;
        this.position = position;
        this.parkingLot = parkingLot;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public int getPosition() {
        return position;
    }

    public int getParkingLot() {
        return parkingLot;
    }
}