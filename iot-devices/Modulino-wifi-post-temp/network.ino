// https://docs.arduino.cc/language-reference/en/functions/wifi/client/
// https://arduinojson.org/

void httpRequest(float celsius, float humidity) {
  JsonDocument data;

  data["device"] = "Arduino-wifi-hi-range";
  data["temp"] = celsius;
  data["humidity"] = humidity;

  String body;
  serializeJson(data, body);
 
  // close any connection before send a new request.
  client.stop();

  if (client.connect(server, port)) {
    Serial.println("connected, sending readings..");
    // send the HTTP request:
    client.println("POST /arduino/sensor-data HTTP/1.1");
    client.print("Host: ");
    client.println(server);
    client.println("Content-type: application/json");

    //Backend checks for key
    client.print("API-Key: ");
    client.println(apiKey);
    client.print("Content-Length: ");
    client.println(body.length());
    client.println("Connection: close");
    client.println();
    client.print(body);
    lastConnection = millis();

    /* Checks if JSON is serialized properly
    JsonDocument print;
    deserializeJson(print, body);
    const float temp2 = print["temp"];
    Serial.print("Posted temperate: ");
    Serial.println(temp2);
    */
  } else {
    // if connection fails:
    lastConnection = millis();
    Serial.println("connection failed");
  }
}

void connectWifi() {
  while (status != WL_CONNECTED) {
    Serial.print("Attempting to connect to SSID: ");
    Serial.println(ssid);
    status = WiFi.begin(ssid, pass);
    delay(10000);
    Serial.println(status);
  }
}