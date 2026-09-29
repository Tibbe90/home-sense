package iot.temperature.stats.models;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "climate-data")
public class TempHumidity {

    @Id
    private String id;
    private String device;
    private double temp;
    private double humidity;
    private Instant measureTime;

    public TempHumidity(String device, double temp, double humidity) {
        this.temp = temp;
        this.humidity = humidity;
        this.device = device;
        this.measureTime = Instant.now();
    }

    @Override
    public String toString() {
        return "TempHumidity [id=" + id + ", device=" + device + ", temp=" + temp + ", humidity=" + humidity
                + ", measureTime=" + measureTime + "]";
    }

    public String getId() {
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
