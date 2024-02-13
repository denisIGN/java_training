package util;

import model.TimeStamp;
import model.Vehicle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExerciseUtil {

    private static final Integer SECTION_LENGTH = 10;
    private static final String INVALID_INPUT = "Please enter a valid hour and minute numerical values.";

    public List<Vehicle> extractDataFromInputFileAndStoreInList() throws IOException {
        Path file = new File("src/main/resources/measurements.txt").toPath();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(String.valueOf(file)));
        StringBuilder stringBuilder = new StringBuilder();
        List<Vehicle> mappedDataToVehicles = new ArrayList<>();

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

        while((bufferedReader.readLine()) != null) {
            stringBuilder.append(bufferedReader.readLine());
            tokens = bufferedReader.readLine().split(" ");
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
                    new TimeStamp(Integer.parseInt(entryHour), Integer.parseInt(entryMinute), Integer.parseInt(entrySecond), Integer.parseInt(entryMillisecond)),
                    new TimeStamp(Integer.parseInt(exitHour), Integer.parseInt(exitMinute), Integer.parseInt(exitSecond), Integer.parseInt(exitMillisecond))));
        }

        return mappedDataToVehicles;
    }

    public String exercise3VehiclesPassedBefore9() throws IOException {
         int result = extractDataFromInputFileAndStoreInList()
                .stream()
                .filter(vehicle -> vehicle.getExitTime().getHour() < 9)
                .toList()
                .size();

         return "Exercise 3 \n Before 9'o clock " + result
                 + " vehicles passed the exit point recorder.\n";
    }

    public String exercise4UserInput(String userInput) throws IOException {
        List<Vehicle> vehicles = extractDataFromInputFileAndStoreInList();
        String result = "";
        int count = 0;
        double trafficIntensity;
        if (userInput.isEmpty() || userInput.length() > 4) {
          return INVALID_INPUT;
        }

        String[] separateHourFromMinute = userInput.split(" ");

        for(Vehicle in : vehicles) {
            if(     in.getEntryTime().getHour() == Integer.parseInt(separateHourFromMinute[0]) &&
                    in.getEntryTime().getMinute() == Integer.parseInt(separateHourFromMinute[1])) {
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
                    + vehicle.getAverageSpeed() + " km/h";
        }

        return result;

    }

    private List<Vehicle> populateAverageSpeedForVehicle() throws IOException {
        List<Vehicle> vehicles = extractDataFromInputFileAndStoreInList();

        for(Vehicle vehicle : vehicles) {
            String exitTimeDouble = vehicle.getExitTime().getHour() + "." + vehicle.getExitTime().getMinute();
            String entryTimeDouble = vehicle.getEntryTime().getHour() + "." + vehicle.getEntryTime().getMinute();
            double fromAtoB = Double.parseDouble(exitTimeDouble) - Double.parseDouble(entryTimeDouble);
            int speed = (int) ( SECTION_LENGTH / fromAtoB);
            vehicle.setAverageSpeed(speed);
        }

        return vehicles;
    }


    public void writeResultsToFile(String userInput) throws IOException {
        FileWriter fileWriter = new FileWriter("src/main/resources/endfile.txt");
        String exercise2 = "Exercise 2 \n The data of " + extractDataFromInputFileAndStoreInList().size()
                + " vehicles were recorded in the measurement.\n";
        String exercise3 = "Exercise 3 \n Before 9'o clock " + exercise3VehiclesPassedBefore9()
                + " vehicles passed the exit point recorder.\n";
        String exercise4 = exercise4UserInput(userInput);
        String exercise5 = exercise5AverageSpeed();

        fileWriter.write(exercise2);
        fileWriter.write(exercise3);
        fileWriter.write(exercise4);
        fileWriter.write(exercise5);
        fileWriter.close();
    }

}
