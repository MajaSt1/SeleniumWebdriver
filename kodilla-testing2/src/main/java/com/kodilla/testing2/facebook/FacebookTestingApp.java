package com.kodilla.testing2.facebook;

import com.kodilla.config.WebDriverConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class FacebookTestingApp {
    public static final String XPATH_WAITFOR="//div[contains(@class,\"_5k_5\")]/span/span";
    public static final String XPATH_SELECT="//div[contains(@class,\"_5k_5\")]/span/span/select[1]";
    public static final String XPATH_SELECT2="//div[contains(@class,\"_5k_5\")]/span/span/select[2]";
    public static final String XPATH_SELECT3="//div[contains(@class,\"_5k_5\")]/span/span/select[3]";


    public static void main(String[] args) {
        WebDriver driver = WebDriverConfig.getDriver(WebDriverConfig.CHROME);
        driver.get("https://www.facebook.com/");

        while (!driver.findElement(By.xpath(XPATH_WAITFOR)).isDisplayed());

        WebElement selectCombo= driver.findElement(By.xpath(XPATH_SELECT));
        Select selectBoard= new Select(selectCombo);
        selectBoard.selectByVisibleText("6");

        WebElement selectCombo1= driver.findElement(By.xpath(XPATH_SELECT2));
        Select selectBoard1= new Select(selectCombo1);
        selectBoard1.selectByVisibleText("lip");

        WebElement selectCombo2= driver.findElement(By.xpath(XPATH_SELECT3));
        Select selectBoard2= new Select(selectCombo2);
        selectBoard2.selectByVisibleText("1991");

    }
}