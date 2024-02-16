package com.albsig.weatherapp.abgabe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

public class SearchButtonActionListener implements ActionListener {
  
  private JTextField searchTextField;
  private JSONObject weatherData;
  private JLabel weatherConditionImage;
  private JLabel temperatureText;
  private JLabel weatherConditionDesc;
  private JLabel humidityText;
  private JLabel windspeedText;
  private static final Logger LOGGER = LogManager.getLogger(SearchButtonActionListener.class);

  public SearchButtonActionListener(JTextField searchTextField, JSONObject weatherData,
        JLabel weatherConditionImage, JLabel temperatureText, JLabel weatherConditionDesc,
        JLabel humidityText, JLabel windspeedText) {
    this.searchTextField = searchTextField;
    this.weatherData = weatherData;
    this.weatherConditionImage = weatherConditionImage;
    this.temperatureText = temperatureText;
    this.weatherConditionDesc = weatherConditionDesc;
    this.humidityText = humidityText;
    this.windspeedText = windspeedText;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String userInput = searchTextField.getText();

    if (userInput.replaceAll("\\s", "").length() <= 0) {
      return;
    }

    weatherData = WeatherApp.getWeatherData(userInput);

    String weatherCondition = (String) weatherData.get("weather_condition");

    switch (weatherCondition) {
      case "Clear":
        weatherConditionImage.setIcon(
                loadImage("src/main/java/com/albsig/weatherApp/abgabe/assets/clear.png"));
        LOGGER.info("set weather Condition to: ", weatherCondition);
        break;
      case "Cloudy":
        weatherConditionImage.setIcon(
                loadImage("src/main/java/com/albsig/weatherApp/abgabe/assets/cloudy.png"));
        LOGGER.info("set weather Condition to: ", weatherCondition);
        break;
      case "Rain":
        weatherConditionImage.setIcon(
                loadImage("src/main/java/com/albsig/weatherApp/abgabe/assets/rain.png"));
        LOGGER.info("set weather Condition to: ", weatherCondition);
        break;
      case "Snow":
        weatherConditionImage.setIcon(
                loadImage("src/main/java/com/albsig/weatherApp/abgabe/assets/snow.pngImage"));
        LOGGER.info("set weather Condition to: ", weatherCondition);
        break;
      default:
        LOGGER.info("found no matching weather Condition for: ", weatherCondition);
        break;
    }

    double temperature = (double) weatherData.get("temperature");
    temperatureText.setText(temperature + " C");

    weatherConditionDesc.setText(weatherCondition);

    long humidity = (long) weatherData.get("humidity");
    humidityText.setText("<html><b>Humidity</b> " + humidity + "%</html>");

    double windspeed = (double) weatherData.get("windspeed");
    windspeedText.setText("<html><b>Windspeed</b> " + windspeed + "km/h</html>");     
  }
  
  private ImageIcon loadImage(String resourcePath) {
    try {
      BufferedImage image = ImageIO.read(new File(resourcePath));
      LOGGER.info("loaded image correctly");
      return new ImageIcon(image);
    } catch (IOException e) {
      e.printStackTrace();
    }

    LOGGER.info("Couldn't find resource");
    return null;
  }

}
