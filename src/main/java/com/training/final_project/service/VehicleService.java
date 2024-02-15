package com.training.final_project.service;

import com.training.final_project.model.Vehicle;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface VehicleService {

    Optional<List<Vehicle>> fetchVehicles() throws IOException;
    Optional<Vehicle> createVehicle(Vehicle vehicle);
    void deleteVehicleById(Long id) throws IOException;
    Optional<List<Vehicle>> getVehiclesForPeriod(Integer hour, Integer minute) throws IOException;
    Optional<List<Vehicle>> getVehiclesAboveSpeedLimit();
}
