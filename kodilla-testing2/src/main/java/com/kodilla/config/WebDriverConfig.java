package com.kodilla.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverConfig {
    public final static String CHROME="CHROME_DRIVER";
    public static WebDriver getDriver(final String driver){
        System.setProperty("webdriver.chrome.driver","c:\\Selenium-drivers\\Chrome\\chromedriver.exe");

        if(driver.equals(CHROME)){
            return new ChromeDriver();
        }else {
            return null;
        }
    }
}
