package iot.temperature.stats.models;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "climate-data")
public class TempHumidity {

    @Id
    private int id;
    private String device;
    private double temp;
    private double humidity;
    private Instant measureTime;

    public TempHumidity(int id, String device, double temp, double humidity) {
        this.id = id;
        this.temp = temp;
        this.humidity = humidity;
        this.device = device;
        this.measureTime = Instant.now();
    }

    public int getId() {
        return id;
    }

    public double getTemp() {
        return temp;
    }

    public double getHumidity() {
        return humidity;
    }

    public Instant getMeasureTime() {
        return measureTime;
    }

    public String getDevice() {
        return device;
    }

    
    
}
