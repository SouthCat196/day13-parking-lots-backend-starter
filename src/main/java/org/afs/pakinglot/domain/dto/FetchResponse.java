package org.afs.pakinglot.domain.dto;

import lombok.Data;
import org.afs.pakinglot.domain.Car;

@Data
public class FetchResponse {
    private final Car car;
    private final long parkingDuration;

    public FetchResponse(Car car, long parkingDuration) {
        this.car = car;
        this.parkingDuration = parkingDuration;
    }
}