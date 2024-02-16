package com.albsig.weatherapp.abgabe;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;


public class WeatherAppGui extends JFrame {
  private static final long serialVersionUID = 1L;
  private JTextField searchTextField;
  private JLabel weatherConditionImage;
  private JLabel temperatureText;
  private JLabel weatherConditionDesc;
  private JLabel humidityImage;
  private JLabel humidityText;
  private JLabel windspeedImage;
  private JLabel windspeedText;
  private JButton searchButton;
  private JSONObject weatherData;
  private static final Logger LOGGER = LogManager.getLogger(WeatherAppGui.class);

  public WeatherAppGui() {
    super("Weather App");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(450, 650);
    setLocationRelativeTo(null);
    setLayout(null);
    setResizable(false);

    addGuiComponents();
  }

  private void addGuiComponents() {
    searchTextField = new JTextField();
    searchTextField.setBounds(15, 15, 351, 45);
    searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));
    add(searchTextField);

    weatherConditionImage = new JLabel(
            loadImage("src/main/java/com/albsig/weatherApp/abgabe/assets/cloudy.png"));
    weatherConditionImage.setBounds(0, 125, 450, 217);
    add(weatherConditionImage);

    temperatureText = new JLabel("10 C");
    temperatureText.setBounds(0, 350, 450, 54);
    temperatureText.setFont(new Font("Dialog", Font.BOLD, 48));
    temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
    add(temperatureText);

    weatherConditionDesc = new JLabel("Cloudy");
    weatherConditionDesc.setBounds(0, 405, 450, 36);
    weatherConditionDesc.setFont(new Font("Dialog", Font.PLAIN, 32));
    weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
    add(weatherConditionDesc);

    humidityImage = new JLabel(
            loadImage("src/main/java/com/albsig/weatherApp/abgabe/assets/humidity.png"));
    humidityImage.setBounds(15, 500, 74, 66);
    add(humidityImage);

    humidityText = new JLabel("<html><b>Humidity</b> 100%</html>");
    humidityText.setBounds(90, 500, 85, 55);
    humidityText.setFont(new Font("Dialog", Font.PLAIN, 16));
    add(humidityText);

    windspeedImage = new JLabel(
            loadImage("src/main/java/com/albsig/weatherApp/abgabe/assets/windspeed.png"));
    windspeedImage.setBounds(220, 500, 74, 66);
    add(windspeedImage);

    windspeedText = new JLabel("<html><b>Windspeed</b> 15km/h</html>");
    windspeedText.setBounds(310, 500, 85, 55);
    windspeedText.setFont(new Font("Dialog", Font.PLAIN, 16));
    add(windspeedText);

    searchButton = new JButton(
            loadImage("src/main/java/com/albsig/weatherApp/abgabe/assets/search.png"));

    searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    searchButton.setBounds(375, 13, 47, 45);
    searchButton.addActionListener(new SearchButtonActionListener(
            searchTextField,
            weatherData,
            weatherConditionImage,
            temperatureText,
            weatherConditionDesc,
            humidityText,
            windspeedText
            ));
    
    add(searchButton);
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









