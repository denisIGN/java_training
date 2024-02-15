package com.training.final_project.controller;

import com.training.final_project.model.Vehicle;
import com.training.final_project.service.VehicleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;


    @GetMapping(value = "/getVehicles")
    public ResponseEntity<Optional<List<Vehicle>>> returnVehicles() throws IOException {

        return ResponseEntity.ok(vehicleService.fetchVehicles());
    }

    @PostMapping(value = "/addVehicle")
    public ResponseEntity<Optional<Vehicle>> createVehicle(@RequestBody Vehicle vehicle) {
        return ResponseEntity.ok(vehicleService.createVehicle(vehicle));
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteVehicleById(@PathVariable Long id) throws IOException {
         vehicleService.deleteVehicleById(id);
    }

    @GetMapping(value = "/getVehiclesForPeriod")
    public ResponseEntity<Optional<List<Vehicle>>> getVehiclesForPeriod(@RequestParam("hour") Integer hour, @RequestParam("minute") Integer minute) throws IOException {
        return ResponseEntity.ok(vehicleService.getVehiclesForPeriod(hour, minute));
    }

    @GetMapping(value = "/getVehiclesAboveSpeedLimit")
    public ResponseEntity<Optional<List<Vehicle>>> getVehiclesAboveSpeedLimit() {
        return ResponseEntity.ok(vehicleService.getVehiclesAboveSpeedLimit());
    }

}
