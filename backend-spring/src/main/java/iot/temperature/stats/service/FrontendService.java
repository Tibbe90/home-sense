package iot.temperature.stats.service;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import iot.temperature.stats.models.StatsDTO;
import iot.temperature.stats.models.TempHumidity;
import iot.temperature.stats.models.TempHumidityDTO;
import iot.temperature.stats.models.TempMapper;

public class FrontendService {
    
    MongoOperations mongoOperations;

    public FrontendService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public StatsDTO get24hStats() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get24hStats'");
    }

    public String getStatus() {
        // TODO Auto-generated method stub
        return "help, I'm unfinished";
    }

    public void delete() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    public void delete(int readings) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    public List<TempHumidityDTO> getLatestData() {
        //FIX QUERY
        List<TempHumidity> newReadings = mongoOperations.find(Query.query(null), TempHumidity.class);
        List<TempHumidityDTO> dtos = newReadings.stream()
        .map(temp -> TempMapper.toDisplayTempHumidityDTO(temp))
        .toList();

        return dtos;
    }
    
}
