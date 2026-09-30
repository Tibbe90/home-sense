package iot.temperature.stats.models;

import java.util.List;

public class StatsDTO {
    private Stats stats;
    private List<StatsTempHumidityDTO> measurements;
    
    public StatsDTO(Stats stats, List<StatsTempHumidityDTO> measurements) {
        this.stats = stats;
        this.measurements = measurements;
    }

    public Stats getStats() {
        return stats;
    }

    public List<StatsTempHumidityDTO> getMeasurements() {
        return measurements;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public void setMeasurements(List<StatsTempHumidityDTO> measurements) {
        this.measurements = measurements;
    }
    
}
