import model.Vehicle;
import util.ReadFileUtil;

import java.util.List;

public class Main {
    public static final ReadFileUtil readFileUtil = new ReadFileUtil();

    public static void main(String[] args) {

    }


    public static List<Vehicle> exercise1ReadAndStoreData() {
        return readFileUtil.storeDataIntoList();
    }

    public static void exercise2ShowRecorderVehicles() {
        System.out.println("Exercise 2. \n" + "The data of " + readFileUtil.storeDataIntoList().size() + " vehicles were recorded in the measurement");
    }

    public static void exercise3vehiclesPassedExitPointBefore9() {
        int count = 0;
        List<Vehicle> vehicles = readFileUtil.storeDataIntoList();

        for (Vehicle inList : vehicles) {
            if(inList.getExitTime().startsWith("8")) {
                count++;
            }
        }
        System.out.println("Exercise 3. \n Before 9 o'clock " + count + " vehicles passed the exit point recorder");
    }

}