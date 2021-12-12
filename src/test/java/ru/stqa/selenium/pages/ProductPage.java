package ru.stqa.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class ProductPage extends Page {
    public ProductPage(EventFiringWebDriver driver) {
        super(driver);
    }

    public void addToCart () {
        int quantity = Integer.parseInt(quantityInCart().getText());
        driver.findElement(By.cssSelector("button[name=add_cart_product]")).click();
        wait.until(ExpectedConditions.textToBe(By.cssSelector("#cart .quantity"), String.valueOf(quantity + 1)));
    }
    public boolean isSelectorSize() {
        return isElementPresent(By.cssSelector("select"));
    }

    public void selectSize(String value) {
        Select selOption = new Select(driver.findElement(By.cssSelector("select")));
        selOption.selectByValue(value);
    }

}
