package org.afs.pakinglot.controller;

import org.afs.pakinglot.domain.Car;
import org.afs.pakinglot.domain.ParkingLot;
import org.afs.pakinglot.domain.ParkingManager;
import org.afs.pakinglot.domain.Ticket;
import org.afs.pakinglot.domain.dto.ParkingLotResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class ParkingControllerTest {

    @Autowired
    private MockMvc client;

    @Autowired
    private JacksonTester<List<ParkingLotResponse>> parkingLotResponseJacksonTester;

    @Autowired
    private JacksonTester<Ticket> ticketJacksonTester;

    @Autowired
    private JacksonTester<Car> carJacksonTester;

    private ParkingManager parkingManager;

    @BeforeEach
    void setUp() {
        parkingManager = new ParkingManager();
    }

    @Test
    void should_return_all_parking_lots_when_get_all_parking_lots() throws Exception {
        // Given
        List<ParkingLot> givenParkingLots = parkingManager.getParkingLots();

        // When
        MvcResult result = client.perform(MockMvcRequestBuilders.get("/parking/getParkingData"))
                .andReturn();

        // Then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getResponse().getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        List<ParkingLotResponse> fetchedParkingLots = parkingLotResponseJacksonTester.parseObject(result.getResponse().getContentAsString());
        assertThat(fetchedParkingLots).hasSameSizeAs(givenParkingLots);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Standard", "Smart", "Super"})
    void should_park_car_and_return_valid_ticket_when_park_given_strategy(String strategy) throws Exception {
        // Given
        Car car = new Car(strategy + "123");

        // When
        MvcResult result = client.perform(MockMvcRequestBuilders.post("/parking/park")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carJacksonTester.write(car).getJson())
                        .param("strategy", strategy))
                .andReturn();

        // Then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        Ticket ticket = ticketJacksonTester.parseObject(result.getResponse().getContentAsString());
        assertThat(ticket).isNotNull();
    }

    @Test
    void should_fetch_car_when_fetch_given_valid_ticket() throws Exception {
        // Given
        Car car = new Car("JKL012");
        MvcResult res = client.perform(MockMvcRequestBuilders.post("/parking/park")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carJacksonTester.write(car).getJson())
                        .param("strategy", "Standard"))
                .andReturn();
        Ticket ticket = ticketJacksonTester.parseObject(res.getResponse().getContentAsString());

        // When
        MvcResult result = client.perform(MockMvcRequestBuilders.post("/parking/fetch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ticketJacksonTester.write(ticket).getJson()))
                .andReturn();

        // Then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        Car fetchedCar = carJacksonTester.parseObject(result.getResponse().getContentAsString());
        assertThat(fetchedCar).isEqualTo(car);
    }

    @Test
    void should_throw_exception_when_fetch_given_invalid_ticket() throws Exception {
        // Given
        Ticket invalidTicket = new Ticket("XYZ999", 1, 1);

        // When & Then
        client.perform(MockMvcRequestBuilders.post("/parking/fetch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ticketJacksonTester.write(invalidTicket).getJson()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void should_throw_exception_when_park_given_no_available_position() throws Exception {
        // Given
        for (int i = 1; i < 28; i++) {
            Car car = new Car("CAR" + i);
            client.perform(MockMvcRequestBuilders.post("/parking/park")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(carJacksonTester.write(car).getJson())
                            .param("strategy", "Standard"));
        }

        // When & Then
        client.perform(MockMvcRequestBuilders.post("/parking/park")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carJacksonTester.write(new Car("OVERFLOW")).getJson())
                        .param("strategy", "Standard"))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }
}