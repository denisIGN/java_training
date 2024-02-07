package util;

import model.Vehicle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ReadFileUtil {

    private static final String inputFilePath = "/Users/JF32648/IdeaProjects/section_speed_exercise/src/resource/measurements.txt";

    public List<Vehicle> storeDataIntoList() {
        List<Vehicle> vehiclesFromFile = new ArrayList<>();

        try {
            List<String> fileLines = Files.readAllLines(Paths.get(inputFilePath));

            for (String line : fileLines) {
                String licensePlate = line.substring(0, 6);
                String entryAndExitTimes = line.substring(7);
                int midString = entryAndExitTimes.length() / 2;
                String entry = entryAndExitTimes.substring(0, midString).trim().replace(" ", "");
                String exit = entryAndExitTimes.substring(midString).replaceFirst("^\\s*", "").trim().replace(" ", "");

                System.out.println(entry);
                vehiclesFromFile.add(new Vehicle(licensePlate, entry, exit));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return vehiclesFromFile;
    }

}
