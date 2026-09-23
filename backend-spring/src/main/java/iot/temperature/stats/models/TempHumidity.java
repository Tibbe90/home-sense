package iot.temperature.stats.models;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "climate-data")
public class TempHumidity {

    @Id
    private String id;
    private float temp;
    private float humidity;
    private Instant measureTime;

    public TempHumidity(String id, float temp, float humidity) {
        this.id = id;
        this.temp = temp;
        this.humidity = humidity;
        this.measureTime = Instant.now();
    }

    public String getId() {
        return id;
    }

    public float getTemp() {
        return temp;
    }

    public float getHumidity() {
        return humidity;
    }

    public Instant getMeasureTime() {
        return measureTime;
    }

    
    
}
