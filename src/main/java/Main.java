import util.ExerciseUtil;

import java.io.IOException;

public class Main {
    public static final ExerciseUtil exerciseUtil = new ExerciseUtil();

    public static void main(String[] args) throws IOException {

        System.out.println("Exercise 2. \n The data of " + exerciseUtil.extractDataFromInputFileAndStoreInList().size() + " vehicles were recorded in the measurement.");

        System.out.println(exerciseUtil.exercise3VehiclesPassedBefore9());

        System.out.println(exerciseUtil.exercise4UserInput());

        System.out.println(exerciseUtil.exercise5AverageSpeed());

        System.out.println(exerciseUtil.exercise6HowManyVehiclesAbove60kmh());

        exerciseUtil.writeResultsToFile();

    }


}