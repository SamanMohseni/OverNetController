# Over The Internet Controller
An Over-the-internet remote controller using ESP8266, a custom-written Android application, and a Java-written server application.

## Overview
I initially developed this project to control the faulty water pump of the house I had rented. It couldn't turn on or off automatically and had to be plugged/unplugged, and the owner refused to fix it.
This controller would be placed between the power source and remotely cut or connect power using an electrical relay.

## The Hardware
The hardware (shown below) is an ESP8266 module with a relay connected to it, along with some simple circuitry to bios the system.
This hardware connects to a remote server over the wifi connection.
You should set `WIFI_SSID`, `WIFI_PASSWORD`, and `SERVER_ADDERSS` in `BasicHttpClient.ino` accordingly.
The hardware can also charge anything over USB if the server commands it.

<img src="https://github.com/SamanMohseni/OverNetController/assets/51726090/ccb8bdd9-60d3-4f10-9e97-ddccafc09146" width=40% height=40%>

## The server
The server application is a Java Servlet that accepts HTTP requests from the Android application and pushes them to the hardware controller client.

## The Android Application
It's a simple Android application that indicates the switch state with a button to toggle the switch.
You should set the `REPLACE_WITH_YOUR_SERVER_ADDRESS` value at `AndroidApp/src/main/java/m/s/pomp/httpRequest/API.java`.
