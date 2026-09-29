#include <WiFiS3.h>
#include <ArduinoJson.h>
#include <Modulino.h>
#include "settings.h"

char ssid[] = SECRET_SSID;
char pass[] = SECRET_PASSWORD;
char apiKey[] = SECRET_API_KEY;

int status = WL_IDLE_STATUS;

WiFiClient client;
char server[] = "192.168.50.16";
int port = 8081;

ModulinoThermo thermo;
float celsius = 0;
float humidity = 0;

unsigned long lastConnection = 0;
const unsigned long postingInterval = 900L * 1000L;

void setup() {
  //Initialize serial and wait for port to open:
  Serial.begin(9600);
  Modulino.begin();
  thermo.begin();

  // check for the WiFi module:
  if (WiFi.status() == WL_NO_MODULE) {
    Serial.println("Communication with WiFi module failed!");
    while (true);
  }

  connectWifi();
}
void loop() {

  if (WiFi.status() != WL_CONNECTED) {
    Serial.println("Connection lost, reconnecting....");
    status = WiFi.status();
    connectWifi();
  }

  if (millis() - lastConnection > postingInterval) {
    celsius = thermo.getTemperature();
    humidity = thermo.getHumidity();
    httpRequest(celsius, humidity);
  }
  delay(100);
}
