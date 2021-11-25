package ru.stqa.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class TestSeleniumFirst extends BaseTest{

    @Test
    public void myFirstTestSelenium(){
        driver.get("https://google.com");
        driver.findElement(By.name("q")).sendKeys("webdriver", Keys.ENTER);
        assertTrue(isElementPresent(By.cssSelector("#rcnt")));
        wait.until(titleIs("webdriver - Поиск в Google"));

    }
}
