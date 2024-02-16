package com.albsig.weatherapp.abgabe;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.simple.JSONArray;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;



public class WeatherAppTest {

  @Mock
  private WeatherApp weatherApp;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetCurrentTime() {
    LocalDateTime currentDateTime = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH':00'");
    String formattedDateTime = currentDateTime.format(formatter);

    Assert.assertEquals(weatherApp.getCurrentTime(), formattedDateTime);
  }
  
  
  @Test
  void testFindIndexOfCurrentTime() {
    JSONArray timeList = new JSONArray();
    timeList.add("2024-01-15T00:00");
    timeList.add(weatherApp.getCurrentTime());    
    
    Assert.assertEquals(weatherApp.findIndexOfCurrentTime(timeList), 1);
  }
  
  
  @Test
  void testConverWeatherCode() {
    long weatherCode = 0L;
    String weatherCondition = "Clear";
    Assert.assertEquals(weatherApp.convertWeatherCode(weatherCode), weatherCondition);
  }
}
