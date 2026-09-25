package iot.temperature.stats.models;

import java.time.Instant;

public class Stats {
    
    String device;
    double averageTemp;
    double minTemp;
    double maxTemp;
    double averageHumidity;
    double minHumidity;
    double maxHumidity;
    Instant periodStart;
    Instant periodEnd;

    public Stats(String device, double averageTemp, double minTemp, double maxTemp, double averageHumidity,
            double minHumidity, double maxHumidity, Instant periodStart, Instant periodEnd) {
        this.device = device;
        this.averageTemp = averageTemp;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.averageHumidity = averageHumidity;
        this.minHumidity = minHumidity;
        this.maxHumidity = maxHumidity;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public double getAverageTemp() {
        return averageTemp;
    }

    public void setAverageTemp(double averageTemp) {
        this.averageTemp = averageTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getAverageHumidity() {
        return averageHumidity;
    }

    public void setAverageHumidity(double averageHumidity) {
        this.averageHumidity = averageHumidity;
    }

    public double getMinHumidity() {
        return minHumidity;
    }

    public void setMinHumidity(double minHumidity) {
        this.minHumidity = minHumidity;
    }

    public double getMaxHumidity() {
        return maxHumidity;
    }

    public void setMaxHumidity(double maxHumidity) {
        this.maxHumidity = maxHumidity;
    }

    public Instant getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(Instant periodStart) {
        this.periodStart = periodStart;
    }

    public Instant getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(Instant periodEnd) {
        this.periodEnd = periodEnd;
    }
}