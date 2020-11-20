package com.emr.example.controller;

import com.emr.example.controller.resource.CarResource;
import com.emr.example.model.Car;
import com.emr.example.repository.CarRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CarControllerTest extends IntegrationTestBase {

    private static final String BASE_URL = "/cars";

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setup() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    void teardown() {
        carRepository.deleteAll();
    }

    @Nested
    class GetCars {

        @Test
        void happyPath() throws Exception {
            // Arrange
            var car = new Car().setVin("vin").setMake("make").setModel("model");
            carRepository.save(car);

            // Act & Assert
            mockMvc.perform(get(BASE_URL))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(1)))
                    .andExpect(jsonPath("$[0].id", equalTo(car.getId())))
                    .andExpect(jsonPath("$[0].vin", equalTo(car.getVin())))
                    .andExpect(jsonPath("$[0].make", equalTo(car.getMake())))
                    .andExpect(jsonPath("$[0].model", equalTo(car.getModel())));
        }
    }

    @Nested
    class CreateCar {

        @Test
        void happyPath() throws Exception {
            // Arrange
            var carRequest = new CarResource().setVin("vin").setMake("make").setModel("model");

            var json = objectMapper.writeValueAsString(carRequest);

            // Act & Assert
            mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(json))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id", notNullValue()))
                    .andExpect(jsonPath("$.vin", equalTo(carRequest.getVin())))
                    .andExpect(jsonPath("$.make", equalTo(carRequest.getMake())))
                    .andExpect(jsonPath("$.model", equalTo(carRequest.getModel())));
        }
    }

    @Nested
    class GetCar {

        @Test
        void happyPath() throws Exception {
            // Arrange
            var car = new Car().setVin("vin").setMake("make").setModel("model");
            carRepository.save(car);

            // Act & Assert
            mockMvc.perform(get(BASE_URL + "/" + car.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", equalTo(car.getId())))
                    .andExpect(jsonPath("$.vin", equalTo(car.getVin())))
                    .andExpect(jsonPath("$.make", equalTo(car.getMake())))
                    .andExpect(jsonPath("$.model", equalTo(car.getModel())));
        }

        @Test
        void returns404_whenCarIdIsNotFound() throws Exception {
            // Arrange
            int badId = 9999;

            // Act & Assert
            mockMvc.perform(get(BASE_URL + "/" + badId)).andExpect(status().isNotFound());
        }
    }

    @Nested
    class DeleteCar {

        @Test
        void happyPath() throws Exception {
            // Arrange
            var car = new Car().setVin("vin").setMake("make").setModel("model");
            carRepository.save(car);

            // Act & Assert
            mockMvc.perform(delete(BASE_URL + "/" + car.getId())).andExpect(status().isNoContent());
        }
    }
}
