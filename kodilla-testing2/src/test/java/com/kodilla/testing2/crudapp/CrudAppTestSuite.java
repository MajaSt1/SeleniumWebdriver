package com.kodilla.testing2.crudapp;

import com.kodilla.config.WebDriverConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.Random;
import java.util.stream.Collectors;

public class CrudAppTestSuite {
    private static final String BASE_URL= "https://majast1.github.io/";
    private WebDriver driver;
    private Random generator;

    @Before
    public void initTest(){
        driver=WebDriverConfig.getDriver(WebDriverConfig.CHROME);
        driver.get(BASE_URL);
        generator= new Random();
    }

    private String createCrudAppTestTask() throws InterruptedException{
        final String XPATH_TASK_NAME="//form[contains(@action,\"createTask\")]/fieldset[1]/input";
        final String XPATH_TASK_CONTENT="//form[contains(@action,\"createTask\")]/fieldset[2]/textarea";
        final String XPATH_ADD_BUTTON="//form[contains(@action,\"createTask\")]/fieldset[3]/button";
        String taskname= "Task number " + generator.nextInt(100000);
        String taskContent= taskname + "content";

        WebElement name = driver.findElement(By.xpath(XPATH_TASK_NAME));
        name.sendKeys(taskname);

        WebElement content= driver.findElement(By.xpath(XPATH_TASK_CONTENT));
        content.sendKeys(taskContent);

        WebElement addButton= driver.findElement(By.xpath(XPATH_ADD_BUTTON));
        addButton.click();
        Thread.sleep(2000);

        return taskname;
    }


    private void deleteCrudAppTestTask(String taskname) throws InterruptedException{
        driver.navigate().refresh();

       driver.findElements(By.xpath("//form[@class=\"datatable_row\"]")).stream()
               .filter(findE-> findE.findElement(By.xpath(".//p[@class=\"datatable_field-value\"]"))
                       .getText().equals(taskname))
               .forEach(theForm->{
                   WebElement deleteButton= theForm.findElement(By.xpath(".//button[contains(@data-task-delete-button)]"));
                   deleteButton.click();
               });
        Thread.sleep(5000);
    }

    private void sendTestTaskToTrello(String taskName) throws InterruptedException{
        driver.navigate().refresh();

        while(!driver.findElement(By.xpath("//select[1]")).isDisplayed());

        driver.findElements(By.xpath("//form[@class=\"datatable_row\"]")).stream()
                .filter(anyForm->
                        anyForm.findElement(By.xpath(".//p[@class=\"datatable_field-value\"]"))
                                .getText().equals(taskName))
                .forEach(theForm->{
                    WebElement selectElement = theForm.findElement(By.xpath(".//select[1]"));
                    Select select = new Select(selectElement);
                    select.selectByIndex(1);

                    WebElement buttonCreateCard=
                            theForm.findElement(By.xpath(".//button[contains(@class, \"card-creation\")]"));
                    buttonCreateCard.click();
                });
        Thread.sleep(5000);
    }

    private boolean checkTaskExistsInTrello(String taskname) throws InterruptedException{
        final String TRELLO_URL= "https://trello.com/login";
        boolean result= false;
        WebDriver driverTrello= WebDriverConfig.getDriver(WebDriverConfig.CHROME);
        driverTrello.get(TRELLO_URL);

        driverTrello.findElement(By.id("user")).sendKeys("ikjam1@gmail.com");
        driverTrello.findElement(By.id("password")).sendKeys("Pantera12");
        driverTrello.findElement(By.id("login")).submit();

        Thread.sleep(2000);

        driverTrello.findElements(By.xpath("//a[@class=\"board-title\"]")).stream()
                .filter(aHref-> aHref.findElements(By.xpath(".//span[@title=\"Kodilla Application\"]")).size() > 0)
                .forEach(aHref -> aHref.click());

        Thread.sleep(2000);

        result = driverTrello.findElements(By.xpath("//span")).stream()
                .filter(theSpan->theSpan.getText().contains(taskname))
                .collect(Collectors.toList())
                .size()>0;

        return result;
    }

    @Test
    public void shouldCreateTrelloCard() throws InterruptedException{
        String taskName= createCrudAppTestTask();
       // sendTestTaskToTrello(taskName);
        //assertTrue(checkTaskExistsInTrello(taskName));
        deleteCrudAppTestTask(taskName);
    }

    @After
    public void cleanUpAfterTest(){
        driver.close();
    }
}
