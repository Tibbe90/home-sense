package iot.temperature.stats.models;

import java.time.Instant;

public class StatsTempHumidityDTO {
    
   private String device;
   private double temp;
   private double humidity;
   private Instant measuredAt;

   public StatsTempHumidityDTO() {}

   public String getDevice() {
    return device;
   }

   public void setDevice(String device) {
    this.device = device;
   }

   public double getTemp() {
    return temp;
   }

   public void setTemp(double temp) {
    this.temp = temp;
   }

   public double getHumidity() {
    return humidity;
   }

   public void setHumidity(double humidity) {
    this.humidity = humidity;
   }

   public Instant getMeasuredAt() {
    return measuredAt;
   }

   public void setMeasuredAt(Instant measureAt) {
    this.measuredAt = measureAt;
   }
}
