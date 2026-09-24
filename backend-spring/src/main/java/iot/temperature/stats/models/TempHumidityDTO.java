package iot.temperature.stats.models;

public class TempHumidityDTO {

   private String device;
   private double temp;
   private double humidity;

   public TempHumidityDTO() {}

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

}
