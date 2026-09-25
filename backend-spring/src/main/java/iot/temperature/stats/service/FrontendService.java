package iot.temperature.stats.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import iot.temperature.stats.models.Stats;
import iot.temperature.stats.models.StatsDTO;
import iot.temperature.stats.models.TempHumidity;
import iot.temperature.stats.models.TempHumidityDTO;
import iot.temperature.stats.models.TempMapper;

/* Some references used to build this service:
https://www.baeldung.com/java-collection-min-max
Plugga.tech MongoDb med Spring Boot


*/
public class FrontendService {

    private final MongoOperations mongoOperations;
    private final List<String> devices;

    public FrontendService(MongoOperations mongoOperations, List<String> devices) {
        this.mongoOperations = mongoOperations;
        this.devices = getAllDevices();
    }

    public List<String> getAllDevices() {
        List<String> devices = mongoOperations.findDistinct("device", TempHumidity.class, String.class);
        return devices;
    }

    public List<TempHumidityDTO> getLatestData() {
        List<TempHumidity> newReadings = new ArrayList<>();

        for (String device : devices) {
            Query query = new Query();
            query.addCriteria(Criteria.where("device").is(device)).with(Sort.by(Sort.Direction.DESC, "measureTime"));
            TempHumidity measurement = mongoOperations.findOne(query, TempHumidity.class);
            if (measurement != null) {
                newReadings.add(measurement);
            }
        }

        List<TempHumidityDTO> dtos = newReadings.stream()
                .map(temp -> TempMapper.toDisplayTempHumidityDTO(temp))
                .toList();
        return dtos;
    }

    public List<StatsDTO> get24hReadings() {
        Instant twentyFourH = Instant.now().minusSeconds(86400);
        Query query = new Query();
        query.addCriteria(Criteria.where("measureTime").gt(twentyFourH));
        query.with(Sort.by(Sort.Direction.ASC, "measureTime"));
        List<TempHumidity> readings = mongoOperations.find(query, TempHumidity.class);
        return calculateStats(readings);
    }

    public List<StatsDTO> getAllReadings() {
        List<TempHumidity> fullStats = mongoOperations.findAll(TempHumidity.class);
        return calculateStats(fullStats);
    }

    public String getStatus() {
        // TODO Auto-generated method stub
        return "help, I'm unfinished";
    }

    public void delete() {
        mongoOperations.dropCollection(TempHumidity.class);
    }

    public void delete(int readings) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    // Calculate the stats in the given list of measurements
    public List<StatsDTO> calculateStats(List<TempHumidity> measurements) {
        List<StatsDTO> displayStats = new ArrayList<>();

        if (measurements.isEmpty()) {
            return displayStats;
        }

        for (String device : devices) {
            List<TempHumidity> deviceMeasurements = measurements.stream()
                    .filter(d -> {
                        return device.equals(d.getDevice());})
                    .collect(Collectors.toList());

                    if (deviceMeasurements.isEmpty()) {
                        continue;}

                    // SummaryStatistics is a class that calculates stats for you with much cleaner code.
                    //You can get min, max, average and count. .accept puts a new variable into the calculation.
            DoubleSummaryStatistics tempStats = deviceMeasurements.stream()
                    .mapToDouble(TempHumidity::getTemp)
                    .summaryStatistics();
            DoubleSummaryStatistics humidityStats = deviceMeasurements.stream()
                    .mapToDouble(TempHumidity::getHumidity)
                    .summaryStatistics();
            Instant periodStart = deviceMeasurements.get(0).getMeasureTime();
            Instant periodEnd = deviceMeasurements.get(deviceMeasurements.size() - 1).getMeasureTime();

            List<TempHumidityDTO> dtos = deviceMeasurements.stream()
                .map(temp -> TempMapper.toDisplayTempHumidityDTO(temp))
                .toList();

            Stats stats = new Stats(
                    device,
                    tempStats.getAverage(),
                    tempStats.getMin(),
                    tempStats.getMax(),
                    humidityStats.getAverage(),
                    humidityStats.getMin(),
                    humidityStats.getMax(),
                    periodStart,
                    periodEnd);
            displayStats.add(new StatsDTO(stats, dtos));
        }
        return displayStats;

    }

}
