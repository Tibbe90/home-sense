package iot.temperature.stats.models;

public class TempMapper {
    public static TempHumidityDTO toDisplayTempHumidityDTO(TempHumidity tempHumidity) {
        TempHumidityDTO dto = new TempHumidityDTO();
        dto.setDevice(tempHumidity.getDevice());
        dto.setHumidity(tempHumidity.getHumidity());
        dto.setTemp(tempHumidity.getTemp());
        return dto;
    }

    public static StatsTempHumidityDTO toDisplayStatsTempHumidityDTO(TempHumidity tempHumidity) {
        StatsTempHumidityDTO dto = new StatsTempHumidityDTO();
        dto.setDevice(tempHumidity.getDevice());
        dto.setHumidity(tempHumidity.getHumidity());
        dto.setTemp(tempHumidity.getTemp());
        dto.setMeasuredAt(tempHumidity.getMeasureTime());
        return dto;
    }
}
