package iot.temperature.stats.models;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;

@Document(collection = "climate-data")
public class TempHumidity {

    @Id
    private String id;
    @NotEmpty 
    private String device;
    @DecimalMin("-40")
    @DecimalMax("110")
    private double temp;
    @DecimalMin("0")
    @DecimalMax("100")
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
