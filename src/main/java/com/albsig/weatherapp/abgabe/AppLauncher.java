package com.albsig.weatherapp.abgabe;

import javax.swing.SwingUtilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AppLauncher {
  
  private static final Logger LOGGER = LogManager.getLogger(AppLauncher.class);
    
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        LOGGER.info("start weatherApp");
        new WeatherAppGui().setVisible(true);
      }
    });
  }
}