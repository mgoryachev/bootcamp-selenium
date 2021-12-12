package ru.stqa.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class MainPage extends Page {
    public MainPage(EventFiringWebDriver driver) {
        super(driver);
    }

    public List<WebElement> products() {
        return driver.findElements((By.cssSelector(".product a.link")));
    }

    public void openProductPage (WebElement element) {
        element.click();
        wait.until(d -> d.findElement(By.cssSelector("button[name=add_cart_product]")));
    }

    public void openCart() {
        open();
        driver.findElement(By.cssSelector("a.link[href$=checkout")).click();
        wait.until(presenceOfElementLocated(By.cssSelector("[name=confirm_order]")));
    }
}
