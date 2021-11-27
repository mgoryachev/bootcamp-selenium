package ru.stqa.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertTrue;

public class TestTask08 extends BaseTest {

    @Test
    public void checkSticker(){
        openSite();
        By locatorWraper = By.cssSelector("div.image-wrapper");
        By locatorSticker = By.cssSelector(".sticker");
        int numberWrapperProduct = driver.findElements(locatorWraper).size();
        for (int i = 0; i < numberWrapperProduct; i++){
            WebElement wrapperProduct = driver.findElements(locatorWraper).get(i);
            assertTrue(wrapperProduct.findElements(locatorSticker).size() == 1);
        }
    }
}
