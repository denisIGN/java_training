package com.training.final_project;

import com.training.final_project.model.Vehicle;
import com.training.final_project.repo.VehicleRepository;
import com.training.final_project.service.VehicleService;
import com.training.final_project.service.VehicleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;
    private VehicleService vehicleService;


    @BeforeEach
    void setUp() {
        vehicleService = new VehicleServiceImpl(vehicleRepository);
    }

    @Test
    void test_add_vehicle_success() {
        Vehicle vehicle = Vehicle.builder()
                .licensePlate("12345")
                .entryTime(LocalTime.of(8, 0))
                .exitTime(LocalTime.of(8, 20))
                .averageSpeed(100)
                .build();

        when(vehicleRepository.save(any(Vehicle.class))).then(returnsFirstArg());

        Optional<Vehicle> savedVehicle = vehicleService.createVehicle(vehicle);

       assertEquals(savedVehicle, Optional.of(vehicle));

    }

}
