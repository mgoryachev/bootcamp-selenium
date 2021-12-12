package ru.stqa.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class CartPage extends Page {

    public CartPage(EventFiringWebDriver driver) {
        super(driver);
    }

    public List<WebElement> buttonsRemoveProduct() {
        return driver.findElements(By.cssSelector("[name=remove_cart_item]"));
    }

    public void removeProduct(WebElement element) {
        int numberRowsProduct = buttonsRemoveProduct().size();
        wait.until(visibilityOf(element));
        element.click();
        wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("td.item"), numberRowsProduct - 1));
    }
}
