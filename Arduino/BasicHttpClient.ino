#include <Arduino.h>

#include <ESP8266WiFi.h>
#include <ESP8266WiFiMulti.h>

#include <ESP8266HTTPClient.h>

#include <WiFiClient.h>

ESP8266WiFiMulti WiFiMulti;

const byte pompPin = 12;
const byte interruptPin = 13;
const byte chargePin = 14;
volatile byte state = LOW;
volatile byte chargeState = HIGH;
volatile byte sync = 0;
volatile byte pre = 0;

// Replace with your own values

#define WIFI_SSID "WIFI_SSID"
#define WIFI_PASSWORD "WIFI_PASSWORD"
String SERVER_ADDERSS = "SERVER_ADDERSS";

void setup() {

  pinMode(pompPin, OUTPUT);
  pinMode(interruptPin, INPUT_PULLUP);
  pinMode(chargePin, OUTPUT);
  
  WiFi.mode(WIFI_STA);
  
  WiFiMulti.addAP(WIFI_SSID, WIFI_PASSWORD);
}

void loop() {
  // wait for WiFi connection
  if ((WiFiMulti.run() == WL_CONNECTED)) {

    WiFiClient client;

    HTTPClient http;

    http.setTimeout(20000);
    
    if (http.begin("http://" + SERVER_ADDERSS + ":8080/main")) {  // HTTP

      // start connection and send HTTP header
      http.addHeader("Content-Type", "text/plain");  //Specify content-type header
      int httpCode;
      if(sync){
        if(state == HIGH){
          httpCode = http.POST("p-on");
        }
        else{
          httpCode = http.POST("p-off");
        }
      }
      else{
        httpCode = http.POST("p-what");
      }
      

      // httpCode will be negative on error
      if (httpCode > 0) {
        // file found at server
        if (httpCode == HTTP_CODE_OK || httpCode == HTTP_CODE_MOVED_PERMANENTLY) {
          http.setTimeout(100);
          String payload = http.getString();
          if(payload == "on"){
            state = HIGH;
          }
          else if(payload == "off"){
            state = LOW;
          }
          else if(payload == "on-charge"){
            state = HIGH;
            chargeState = HIGH;
          }
          else if(payload == "off-charge"){
            state = LOW;
            chargeState = HIGH;
          }
          else if(payload == "on-dis"){
            state = HIGH;
            chargeState = LOW;
          }
          else if(payload == "off-dis"){
            state = LOW;
            chargeState = LOW;
          }
          sync = 0;
        }
      }

      http.end();
    }
  }
  
  int val = digitalRead(interruptPin);
  if(val != pre){
    pre = val;
    state = !state;
    sync = 1;
  }

  digitalWrite(pompPin, state);
  digitalWrite(chargePin, chargeState);
}
