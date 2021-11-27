package ru.stqa.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertTrue;

public class TestTask08 extends BaseTest {

    @Test
    public void checkSticker(){
        openSite();
        By locatorProduct = By.cssSelector(".product");
        By locatorSticker = By.cssSelector(".sticker");
        int numberProduct = driver.findElements(locatorProduct).size();
        for (int i = 0; i < numberProduct; i++){
            WebElement wrapperProduct = driver.findElements(locatorProduct).get(i);
            assertTrue(wrapperProduct.findElements(locatorSticker).size() == 1);
        }
    }
}
