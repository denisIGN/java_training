package com.training.final_project.service;

import com.training.final_project.exception.EmptyVehicleListException;
import com.training.final_project.exception.ObjectHasNullValuesException;
import com.training.final_project.model.Vehicle;
import com.training.final_project.repo.VehicleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class VehicleServiceImpl implements VehicleService {


    private final VehicleRepository vehicleRepository;
    private static final Integer SPEED_LIMIT = 90;

    @Override
    public Optional<List<Vehicle>> fetchVehicles() throws IOException {
        if(getVehicleObjectsFromTxtFile().isEmpty()) {
            throw new EmptyVehicleListException("There are no vehicles to show. Check your resource.");
        }

        return Optional.of(getVehicleObjectsFromTxtFile());
    }

    @Override
    public Optional<Vehicle> createVehicle(Vehicle vehicle) {
        if(vehicle == null) {
         throw new EmptyVehicleListException("Please make sure that the request body information is populated.");
        }
        log.info("Populating vehicle info from request body values: {}", vehicle);

        return Optional.of(vehicleRepository.save(Vehicle.builder()
                .licensePlate(vehicle.getLicensePlate())
                .entryTime(vehicle.getEntryTime())
                .exitTime(vehicle.getExitTime())
                .averageSpeed(vehicle.getAverageSpeed())
                .build()));
    }


    @Override
    public void deleteVehicleById(Long id) throws IOException {
        if(id == null) {
            throw new ObjectHasNullValuesException("Id cannot be null.");
        }

        boolean isIdPresent = getVehicleObjectsFromTxtFile()
                .stream()
                .findAny()
                .map(vehicle -> Objects.equals(vehicle.getId(), id))
                .orElse(false);

        if(isIdPresent) {
            log.info("Deleting record with id: {}", id);
            vehicleRepository.deleteById(id);
        } else {
            log.info("No such Id in the database.");
        }
    }


    @Override
    public Optional<List<Vehicle>> getVehiclesForPeriod(Integer hour, Integer minute) {
        if((hour == null || minute == null)) {
            throw new ObjectHasNullValuesException("Hour and Minute query params are required.");
        }

        LocalTime userInput = LocalTime.of(hour, minute);
        List<Vehicle> vehicles = vehicleRepository.findAll();
        checkListOrThrowCustomException(vehicles);

        return Optional.of(vehicles.stream()
                .filter(vehicle -> vehicle.getEntryTime().getHour() == userInput.getHour()
                        && vehicle.getEntryTime().getMinute() == userInput.getMinute())
                .collect(Collectors.toList()));
    }

    @Override
    public Optional<List<Vehicle>> getVehiclesAboveSpeedLimit() {
        checkListOrThrowCustomException(vehicleRepository.findAll());

        return Optional.of(vehicleRepository.findAll()
                .stream()
                .filter(vehicle -> vehicle.getAverageSpeed() > SPEED_LIMIT)
                .collect(Collectors.toList()));
    }

    private List<Vehicle> getVehicleObjectsFromTxtFile() throws IOException {
        Path file = new File("src/main/resources/measurements.txt").toPath();
        Scanner scanner = new Scanner(file);
        List<Vehicle> vehicles = new ArrayList<>();

        String[] stringTokens;
        String line;
        String licensePlate;
        String entryHour;
        String entryMinute;

        String exitHour;
        String exitMinute;

        while(scanner.hasNextLine()) {
            line = scanner.nextLine();
            stringTokens = line.split(" ");
            licensePlate = stringTokens[0];
            entryHour = stringTokens[1];
            entryMinute = stringTokens[2];

            exitHour = stringTokens[5];
            exitMinute = stringTokens[6];

            vehicles.add(Vehicle.builder()
                    .licensePlate(licensePlate)
                    .entryTime(LocalTime.of(Integer.parseInt(entryHour), Integer.parseInt(entryMinute)))
                    .exitTime(LocalTime.of(Integer.parseInt(exitHour), Integer.parseInt(exitMinute)))
                    .build());
        }

        log.info("Saving entities into DB.");
        return vehicleRepository.saveAll(populateAverageSpeedForVehicle(vehicles));
    }

    private List<Vehicle> populateAverageSpeedForVehicle(List<Vehicle> initialList) {

        if(initialList.isEmpty()) {
            throw new EmptyVehicleListException("No vehicles to fetch in order to populate average speed. Check your resources.");
        }

        for(Vehicle vehicle : initialList) {
            LocalTime start = vehicle.getEntryTime();
            LocalTime end = vehicle.getExitTime();
            float minuteDiff = Duration.between(start, end).toMinutes();
            float averageSpeed = 60 * (10F/minuteDiff);

            vehicle.setAverageSpeed((int) averageSpeed);
        }

        return initialList;
    }

    private void checkListOrThrowCustomException(List<Vehicle> vehicles) {
        if(vehicles.isEmpty()) {
            throw new EmptyVehicleListException("List is empty. Either create a vehicle via POST request, or populate H2 DB from GET /getVehicles");
        }
    }
}
