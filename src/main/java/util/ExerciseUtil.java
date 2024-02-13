package util;

import model.TimeStamp;
import model.Vehicle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ExerciseUtil {

    public static final Scanner scanner = new Scanner(System.in);
    private static final Integer SECTION_LENGTH = 10;
    private static final String INVALID_INPUT = "Please enter a valid hour and minute numerical values.";
    private static final Float ALLOWED_SPEED_KM_h = 90F;

    public List<Vehicle> extractDataFromInputFileAndStoreInList() throws IOException {
        Path file = new File("src/main/resources/measurements.txt").toPath();

        List<Vehicle> mappedDataToVehicles = new ArrayList<>();
        Scanner scanner = new Scanner(file);

        String line;
        String[] tokens;
        String licensePlate;
        String entryHour;
        String entryMinute;
        String entrySecond;
        String entryMillisecond;

        String exitHour;
        String exitMinute;
        String exitSecond;
        String exitMillisecond;

        while(scanner.hasNextLine()) {
            line = scanner.nextLine();
            tokens = line.split(" ");
            licensePlate = tokens[0];
            entryHour = tokens[1];
            entryMinute = tokens[2];
            entrySecond = tokens[3];
            entryMillisecond = tokens[4];

            exitHour = tokens[5];
            exitMinute = tokens[6];
            exitSecond = tokens[7];
            exitMillisecond = tokens[8];

            mappedDataToVehicles.add(new Vehicle(licensePlate,
                    new TimeStamp(Integer.parseInt(entryHour),
                            Integer.parseInt(entryMinute),
                            Integer.parseInt(entrySecond),
                            Integer.parseInt(entryMillisecond)),

                    new TimeStamp(Integer.parseInt(exitHour),
                            Integer.parseInt(exitMinute),
                            Integer.parseInt(exitSecond),
                            Integer.parseInt(exitMillisecond))));
        }

        return mappedDataToVehicles;
    }



    public List<Vehicle> vehiclesWithMappedData() throws IOException {
        List<Vehicle> vehiclesWithActualTimeDate = new ArrayList<>();

        if (!extractDataFromInputFileAndStoreInList().isEmpty()) {
            vehiclesWithActualTimeDate = extractDataFromInputFileAndStoreInList();
        }

        for(Vehicle vehicle : vehiclesWithActualTimeDate) {
            vehicle.setEnterTimeFormatted(LocalTime.of(vehicle.getEntryTime().getHour(), vehicle.getEntryTime().getMinute()));
            vehicle.setExitTimeFormatted(LocalTime.of(vehicle.getExitTime().getHour(), vehicle.getExitTime().getMinute()));
        }
        return vehiclesWithActualTimeDate;
    }

    public String exercise3VehiclesPassedBefore9() throws IOException {
         int result = vehiclesWithMappedData()
                .stream()
                .filter(vehicle -> vehicle.getExitTime().getHour() < 9)
                .toList()
                .size();

         return "\n Exercise 3 \n Before 9'o clock " + result
                 + " vehicles passed the exit point recorder.\n";
    }

    public String exercise4UserInput() throws IOException {
        System.out.println("Enter an hour and minute value: ");
        String userInput = scanner.nextLine();
        List<Vehicle> vehicles = vehiclesWithMappedData();

        String result = "";
        int count = 0;
        double trafficIntensity;

        if (userInput.isEmpty() || userInput.length() > 4) {
          return INVALID_INPUT;
        }

        String[] separateHourFromMinute = userInput.split(" ");
        LocalTime timeUserSuggested = LocalTime.of(Integer.parseInt(separateHourFromMinute[0]), Integer.parseInt(separateHourFromMinute[1]));

        for(Vehicle vehicle : vehicles) {
            if(     vehicle.getEnterTimeFormatted().getHour() == timeUserSuggested.getHour() &&
                    vehicle.getEnterTimeFormatted().getMinute() == timeUserSuggested.getMinute()) {
                count++;
                trafficIntensity = (double) count / SECTION_LENGTH;
                result =  "Exercise 4. \n a. The number of vehicle that passed the entry point recorder: " + count +
                        "\n b. The traffic intensity: " + trafficIntensity;
            }
        }
        return result;
    }

    public String exercise5AverageSpeed() throws IOException {
        List<Vehicle> vehicles = populateAverageSpeedForVehicle();
        String result = "";

        Integer maxAverageSpeed = vehicles
                .stream()
                .map(Vehicle::getAverageSpeed)
                .mapToInt(speed -> speed)
                .max().orElse(0);

        List<Vehicle> topSpeedVehicle = vehicles
                .stream()
                .filter(vehicle -> Objects.equals(vehicle.getAverageSpeed(), maxAverageSpeed))
                .findFirst()
                .stream()
                .toList();

        for(Vehicle vehicle : topSpeedVehicle) {

            result = "Exercise 5. \n The data of the vehicle with the highest speed are \n license plate number: "
                    + vehicle.getLicensePlate() + "\n average speed: "
                    + vehicle.getAverageSpeed() + " km/h \n" + "number of overtaken vehicles: "
                    + vehicles.stream().filter(vehicleSpeed -> vehicleSpeed.getAverageSpeed() < vehicle.getAverageSpeed()).toList().size() + "\n";
        }

        return result;

    }


    public String exercise6HowManyVehiclesAbove60kmh() throws IOException {
        List<Vehicle> vehicles = populateAverageSpeedForVehicle();
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(2);
        float vehiclesSpeeding = vehicles
                .stream()
                .filter(vehicle -> vehicle.getAverageSpeed() > ALLOWED_SPEED_KM_h)
                .toList().size();

        return "Exercise 6. \n " + decimalFormat.format((vehiclesSpeeding / vehicles.size()) * 100) + "% of te vehicles were speeding.";
    }


    private List<Vehicle> populateAverageSpeedForVehicle() throws IOException {
        List<Vehicle> vehicles = vehiclesWithMappedData();

        for(Vehicle vehicle : vehicles) {
            LocalTime start = vehicle.getEnterTimeFormatted();
            LocalTime end = vehicle.getExitTimeFormatted();
            float minuteDiff = Duration.between(start, end).toMinutes();
            float averageSpeed = 60 * (10F/minuteDiff);

            vehicle.setAverageSpeed((int) averageSpeed);
        }

        return vehicles;
    }


    public void writeResultsToFile() throws IOException {
        String exercise2 = "Exercise 2 \n The data of " + extractDataFromInputFileAndStoreInList().size()
                + " vehicles were recorded in the measurement.\n";
        String exercise3 = exercise3VehiclesPassedBefore9();
        String exercise4 = exercise4UserInput();
        String exercise5 = exercise5AverageSpeed();
        String exercise6 = exercise6HowManyVehiclesAbove60kmh();

        List<String> linesToWrite = Arrays.asList(exercise2, exercise3, exercise4, exercise5, exercise6);
        Path file = Paths.get("src/main/resources/endfile.txt");
        Files.write(file, linesToWrite, StandardCharsets.UTF_8);

    }

}
