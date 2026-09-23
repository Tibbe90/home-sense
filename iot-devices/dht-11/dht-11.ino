#include <ArduinoHttpClient.h>
#include <WiFiS3.h>
#include "settings.h"

char ssid[] = SECRET_SSID;
char pass[] = SECRET_PASSWORD;

//ANTAGLIGEN INKORREKT, KOLLA ADRESSEN EFTER SKAPAD BACKEND
char serverAdress[] = "192.168.50.184";
int port = 8080;

WiFiClient wifi;
HttpClient client = HttpClient(wifi, serverAdress, port);

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);

  Serial.println("Ansluter till Wifi");
  WiFi.begin(ssid, pass);

  while (WiFi.status() != WL_CONNECTED) {
    Serial.println("Ansluter..");
    delay(500);
  }

Serial.println("Ansluten till WiFi");
}

void loop() {
  // put your main code here, to run repeatedly:
  String postData = "{\"test\":\"Arduino\"}";

  Serial.println("Skickar vår POST");

  client.beginRequest();

  //ÄNDRA ADRESSEN TILL AKTUELL BACKEND
  client.post("/temp"); 
  client.sendHeader("Content-Type", "application/json");
  client.sendHeader("Content-Length", postData.length());

  client.beginBody();
  client.print(postData);
  client.endRequest();

  int statusCode = client.responseStatusCode();
  String response = client.responseBody();

  Serial.print("Status code: ");
  Serial.println(statusCode);
  Serial.print("Response: ");
  Serial.print(response);

  delay(1000);

}
