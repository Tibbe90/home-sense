package iot.temperature.stats.service;

import org.springframework.data.mongodb.core.MongoOperations;

import iot.temperature.stats.models.TempHumidity;

public class ArduinoService {

    MongoOperations mongoOperations;

    public ArduinoService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public void saveMeasurement(TempHumidity measurements) {
        mongoOperations.insert(measurements);
    }
    
}
