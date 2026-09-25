package iot.temperature.stats.models;

import java.util.List;

public class StatsDTO {
    private Stats stats;
    private List<TempHumidityDTO> measurements;
    
    public StatsDTO(Stats stats, List<TempHumidityDTO> measurements) {
        this.stats = stats;
        this.measurements = measurements;
    }

    public Stats getStats() {
        return stats;
    }

    public List<TempHumidityDTO> getMeasurements() {
        return measurements;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public void setMeasurements(List<TempHumidityDTO> measurements) {
        this.measurements = measurements;
    }
    
}
