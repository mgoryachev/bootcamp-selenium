package ru.stqa.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class TestTask13 extends BaseTest{

    @Test
    public void checkAddRemoveProductCart(){
        openSite();
        int quantity = Integer.parseInt(driver.findElement(By.cssSelector("#cart .quantity")).getText());
        while (quantity <= 3) {
            driver.findElement(By.cssSelector(".product a.link")).click();
            wait.until(d -> d.findElement(By.cssSelector("button[name=add_cart_product]")));
            driver.findElement(By.cssSelector("button[name=add_cart_product]")).click();

            quantity = Integer.parseInt(driver.findElement(By.cssSelector("#cart .quantity")).getText());
            openSite();
        }
        wait.until(titleIs("sdfsdf"));
    }
}
