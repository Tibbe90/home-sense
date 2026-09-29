// https://plugga.tech/kurs/iot-med-arduino?l=arduino-wifi-post-backend&s=skip
// https://arduinojson.org/

void httpRequest(float celsius, float humidity) {
  JsonDocument data;

  data["device"] = "Arduino-wifi-lo-range";
  data["temp"] = celsius;
  data["humidity"] = humidity;

  String body;
  serializeJson(data, body);

  httpClient.beginRequest();
  httpClient.post(endpoint);
  httpClient.sendHeader("Content-Type", "application/json");
  httpClient.sendHeader("Content-Length", body.length());
  httpClient.sendHeader("API-Key", apiKey);

  httpClient.beginBody();
  httpClient.print(body);
  httpClient.endRequest();

  int statusCode = httpClient.responseStatusCode();
  String response = httpClient.responseBody();

  Serial.print("Status code: ");
  Serial.println(statusCode);
  Serial.print("Response: ");
  Serial.println(response);

  lastConnection = millis();

  /* Checks if JSON is serialized properly
    JsonDocument print;
    deserializeJson(print, body);
    const float temp2 = print["temp"];
    Serial.print("Posted temperate: ");
    Serial.println(temp2);
    */
}

void connectWifi() {
  while (status != WL_CONNECTED) {
    Serial.print("Attempting to connect to SSID: ");
    Serial.println(ssid);
    status = WiFi.begin(ssid, pass);
    delay(10000);
    Serial.println("Wifi module status (3 means connected): ");
    Serial.println(status);
  }
}