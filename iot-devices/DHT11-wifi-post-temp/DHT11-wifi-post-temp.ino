#include <DHT.h>
#include <DHT_U.h>

#include <WiFiS3.h>
#include <ArduinoHttpClient.h>
#include <ArduinoJson.h>
#include "settings.h"

#define DHTPIN 8
#define DHTTYPE DHT11

DHT dht(DHTPIN, DHTTYPE);

char ssid[] = SECRET_SSID;
char pass[] = SECRET_PASSWORD;
char apiKey[] = SECRET_API_KEY;

int status = WL_IDLE_STATUS;

WiFiClient client;
char server[] = "192.168.50.16";
int port = 8081;
char endpoint[] = "/arduino/sensor-data";

HttpClient httpClient = HttpClient(client, server, port);

float celsius = 0;
float humidity = 0;

unsigned long lastConnection = 0;
const unsigned long postingInterval = 900L * 1000L;

void setup() {
  //Initialize serial and wait for port to open:
  Serial.begin(9600);
  dht.begin();
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
    celsius = dht.readTemperature();
    humidity = dht.readHumidity();
    httpRequest(celsius, humidity);
  }
  delay(100);
}
