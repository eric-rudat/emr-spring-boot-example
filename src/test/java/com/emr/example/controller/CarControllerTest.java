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
            var car = new Car().setVin("vin").setMake("make").setModel("model").setColor("red");
            carRepository.save(car);

            // Act & Assert
            mockMvc.perform(get(BASE_URL))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(1)))
                    .andExpect(jsonPath("$[0].id", equalTo(car.getId())))
                    .andExpect(jsonPath("$[0].vin", equalTo(car.getVin())))
                    .andExpect(jsonPath("$[0].make", equalTo(car.getMake())))
                    .andExpect(jsonPath("$[0].model", equalTo(car.getModel())))
                    .andExpect(jsonPath("$[0].color", equalTo(car.getColor())));
        }
    }

    @Nested
    class CreateCar {

        @Test
        void happyPath() throws Exception {
            // Arrange
            var carRequest = new CarResource().setVin("vin").setMake("make").setModel("model").setColor("red");

            var json = objectMapper.writeValueAsString(carRequest);

            // Act & Assert
            mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(json))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id", notNullValue()))
                    .andExpect(jsonPath("$.vin", equalTo(carRequest.getVin())))
                    .andExpect(jsonPath("$.make", equalTo(carRequest.getMake())))
                    .andExpect(jsonPath("$.model", equalTo(carRequest.getModel())))
                    .andExpect(jsonPath("$.color", equalTo(carRequest.getColor())));
        }
    }

    @Nested
    class GetCar {

        @Test
        void happyPath() throws Exception {
            // Arrange
            var car = new Car().setVin("vin").setMake("make").setModel("model").setColor("red");
            carRepository.save(car);

            // Act & Assert
            mockMvc.perform(get(BASE_URL + "/" + car.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", equalTo(car.getId())))
                    .andExpect(jsonPath("$.vin", equalTo(car.getVin())))
                    .andExpect(jsonPath("$.make", equalTo(car.getMake())))
                    .andExpect(jsonPath("$.model", equalTo(car.getModel())))
                    .andExpect(jsonPath("$.color", equalTo(car.getColor())));
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
    class UpdateCar {

        @Test
        void happyPath() throws Exception {
            // Arrange
            var car = new Car().setVin("vin").setMake("make").setModel("model").setColor("red");
            carRepository.save(car);

            var updatedCar = new CarResource().setVin(car.getVin()).setMake(car.getMake()).setModel(car.getModel()).setColor("yellow");

            var json = objectMapper.writeValueAsString(updatedCar);

            // Act & Assert
            mockMvc.perform(put(BASE_URL + "/" + car.getId()).contentType(MediaType.APPLICATION_JSON).content(json))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", equalTo(car.getId())))
                    .andExpect(jsonPath("$.color", equalTo(updatedCar.getColor())));
        }

        @Test
        void readOnlyPropertiesAreNotUpdated() throws Exception {
            // Arrange
            var car = new Car().setVin("vin").setMake("make").setModel("model").setColor("red");
            carRepository.save(car);

            var updatedCar = new CarResource().setVin("updated vin").setMake("updated make").setModel("updated model").setColor(car.getColor());

            var json = objectMapper.writeValueAsString(updatedCar);

            // Act & Assert
            mockMvc.perform(put(BASE_URL + "/" + car.getId()).contentType(MediaType.APPLICATION_JSON).content(json))
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
            var updatedCar = new CarResource().setVin("vin").setMake("make").setModel("model").setColor("yellow");

            var json = objectMapper.writeValueAsString(updatedCar);

            mockMvc.perform(put(BASE_URL + "/" + badId).contentType(MediaType.APPLICATION_JSON).content(json))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    class DeleteCar {

        @Test
        void happyPath() throws Exception {
            // Arrange
            var car = new Car().setVin("vin").setMake("make").setModel("model").setColor("red");
            carRepository.save(car);

            // Act & Assert
            mockMvc.perform(delete(BASE_URL + "/" + car.getId())).andExpect(status().isNoContent());
        }

        @Test
        void returns404_whenCarIdIsNotFound() throws Exception {
            // Arrange
            int badId = 9999;

            // Act & Assert
            mockMvc.perform(delete(BASE_URL + "/" + badId)).andExpect(status().isNotFound());
        }
    }
}
