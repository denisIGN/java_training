import util.ExerciseUtil;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static final ExerciseUtil readFileUtil = new ExerciseUtil();
    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        System.out.println("Exercise 2 \n The data of " + readFileUtil.extractDataFromInputFileAndStoreInList().size()
                + " vehicles were recorded in the measurement.\n");

        System.out.println("Exercise 3 \n Before 9'o clock " + readFileUtil.exercise3VehiclesPassedBefore9()
                + " vehicles passed the exit point recorder.\n");

        System.out.println("Exercise 4 \n Enter an hour and minute value: ");
        String userInput = readFileUtil.exercise4UserInput(scanner.nextLine());
        System.out.println(userInput);

        System.out.println(readFileUtil.exercise5AverageSpeed());

        readFileUtil.writeResultsToFile(userInput);

    }


}