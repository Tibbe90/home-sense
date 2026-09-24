package iot.temperature.stats.models;

public class TempMapper {
    public static TempHumidityDTO toDisplayTempHumidityDTO(TempHumidity tempHumidity) {
        TempHumidityDTO dto = new TempHumidityDTO();
        dto.setDevice(tempHumidity.getDevice());
        dto.setHumidity(tempHumidity.getHumidity());
        dto.setTemp(tempHumidity.getTemp());
        return dto;
    }
}
