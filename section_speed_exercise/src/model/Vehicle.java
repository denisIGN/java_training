package model;

import java.text.DateFormat;
import java.time.LocalDateTime;

public class Vehicle {
    private String licensePlate;
    private String entryTime;
    private String exitTime;

    public Vehicle(String licensePlate, String entryTime, String exitTime) {
        this.licensePlate = licensePlate;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getExitTime() {
        return exitTime;
    }

    public void setExitTime(String exitTime) {
        this.exitTime = exitTime;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "licensePlate='" + licensePlate + '\'' +
                ", entryTime='" + entryTime + '\'' +
                ", exitTime='" + exitTime + '\'' +
                '}';
    }
}
