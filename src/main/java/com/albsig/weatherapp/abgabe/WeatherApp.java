package com.albsig.weatherapp.abgabe;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

// retreive weather data from API - this backend logic will fetch the latest weather
// data from the external API and return it. The GUI will
// display this data to the user
public class WeatherApp {
    
  private static final Logger LOGGER = LogManager.getLogger(WeatherApp.class);
  private static JSONArray locationData;
  private static JSONObject location;
  private static double latitude;
  private static double longitude;
  private static String urlString;
  
  public static JSONObject getWeatherData(String locationName) {

    locationData = getLocationData(locationName);
    location = (JSONObject) locationData.get(0);
    latitude = (double) location.get("latitude");
    longitude = (double) location.get("longitude");

    urlString = "https://api.open-meteo.com/v1/forecast?" + "latitude=" + latitude + "&longitude="
        + longitude
        + "&hourly=temperature_2m,relativehumidity_2m,weathercode,"
        + "windspeed_10m&timezone=America%2FLos_Angeles";

    try {
      HttpURLConnection conn = fetchApiResponse(urlString);

      if (conn.getResponseCode() != 200) {
        LOGGER.error("Couldn't connect to API, Response Code: ", conn.getResponseCode());
        return null;
      }
      
      LOGGER.info("established connection with API");

      StringBuilder resultJson = new StringBuilder();
      Scanner scanner = new Scanner(conn.getInputStream(), "utf-8");
      while (scanner.hasNext()) {
        resultJson.append(scanner.nextLine());
      }

      scanner.close();
      conn.disconnect();
      
      LOGGER.info("closed connection to API");

      JSONParser parser = new JSONParser();
      JSONObject resultJsonObj = (JSONObject) parser.parse(String.valueOf(resultJson));
      JSONObject hourly = (JSONObject) resultJsonObj.get("hourly");
      JSONArray time = (JSONArray) hourly.get("time");      
      int index = findIndexOfCurrentTime(time);

      JSONArray temperatureData = (JSONArray) hourly.get("temperature_2m");
      double temperature = (double) temperatureData.get(index);

      JSONArray weathercode = (JSONArray) hourly.get("weathercode");
      String weatherCondition = convertWeatherCode((long) weathercode.get(index));

      JSONArray relativeHumidity = (JSONArray) hourly.get("relativehumidity_2m");
      long humidity = (long) relativeHumidity.get(index);

      JSONArray windspeedData = (JSONArray) hourly.get("windspeed_10m");
      double windspeed = (double) windspeedData.get(index);

      JSONObject weatherData = new JSONObject();
      weatherData.put("temperature", temperature);
      weatherData.put("weather_condition", weatherCondition);
      weatherData.put("humidity", humidity);
      weatherData.put("windspeed", windspeed);

      return weatherData;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  
  public static JSONArray getLocationData(String locationName) {
    locationName = locationName.replaceAll(" ", "+");

    String urlString = "https://geocoding-api.open-meteo.com/v1/search?name=" + locationName
        + "&count=10&language=en&format=json";

    try {
      HttpURLConnection conn = fetchApiResponse(urlString);

      if (conn.getResponseCode() != 200) {
        LOGGER.error("Could not connect to API, Response Code: ", conn.getResponseCode());
        return null;
      } else {
        LOGGER.info("established connection with API");
        StringBuilder resultJson = new StringBuilder();
        Scanner scanner = new Scanner(conn.getInputStream(), "utf-8");

        while (scanner.hasNext()) {
          resultJson.append(scanner.nextLine());
        }

        scanner.close();
        conn.disconnect();
        LOGGER.info("closed connection to API");

        JSONParser parser = new JSONParser();
        JSONObject resultsJsonObj = (JSONObject) parser.parse(String.valueOf(resultJson));
        JSONArray locationData = (JSONArray) resultsJsonObj.get("results");
        return locationData;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }
  
  
  public static HttpURLConnection fetchApiResponse(String urlString) {
    try {
      URL url = new URL(urlString);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();

      conn.setRequestMethod("GET");
      conn.connect();
      
      return conn;
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }

  
  public static int findIndexOfCurrentTime(JSONArray timeList) {
    String currentTime = getCurrentTime();

    for (int i = 0; i < timeList.size(); i++) {
      String time = (String) timeList.get(i);
      if (time.equalsIgnoreCase(currentTime)) {
        return i;
      }
    }

    return 0;
  }

  
  public static String getCurrentTime() {
    LocalDateTime currentDateTime = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH':00'");
    String formattedDateTime = currentDateTime.format(formatter);

    return formattedDateTime;
  }

  public static String convertWeatherCode(long weathercode) {
    String weatherCondition = "";
    if (weathercode == 0L) {
      weatherCondition = "Clear";
    } else if (weathercode > 0L && weathercode <= 3L) {
      weatherCondition = "Cloudy";
    } else if ((weathercode >= 51L && weathercode <= 67L)
            || (weathercode >= 80L && weathercode <= 99L)) {
      weatherCondition = "Rain";
    } else if (weathercode >= 71L && weathercode <= 77L) {
      weatherCondition = "Snow";
    }
    LOGGER.info("set weatherCondition to: ", weatherCondition);

    return weatherCondition;
  }
}







