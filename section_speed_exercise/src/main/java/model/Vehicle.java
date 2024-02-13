package model;

import java.sql.Time;
import java.text.DateFormat;
import java.time.LocalDateTime;

public class Vehicle {
    private String licensePlate;
    private TimeStamp entryTime;
    private TimeStamp exitTime;
    private Integer averageSpeed;


    public Vehicle(String licensePlate, TimeStamp entryTime, TimeStamp exitTime) {
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

    public TimeStamp getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(TimeStamp entryTime) {
        this.entryTime = entryTime;
    }

    public TimeStamp getExitTime() {
        return exitTime;
    }

    public Integer getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(Integer averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "licensePlate='" + licensePlate + '\'' +
                ", entryTime=" + entryTime +
                ", exitTime=" + exitTime +
                ", averageSpeed=" + averageSpeed +
                '}';
    }
}
