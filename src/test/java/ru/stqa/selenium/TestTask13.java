package ru.stqa.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class TestTask13 extends BaseTest{

    @Test
    public void checkAddRemoveProductCart(){
        int numberProductInCart = 3;

        fillingCart(numberProductInCart);
        clearingCart();
    }

    public void fillingCart (int numberProduct) {
        openSite();
        int quantity = Integer.parseInt(driver.findElement(By.cssSelector("#cart .quantity")).getText());
        while (quantity < numberProduct) {
            driver.findElement(By.cssSelector(".product a.link")).click();
            wait.until(d -> d.findElement(By.cssSelector("button[name=add_cart_product]")));
            if (isElementPresent(By.cssSelector("select"))) {
                Select selOption = new Select(driver.findElement(By.cssSelector("select")));
                selOption.selectByValue("Small");
            }
            driver.findElement(By.cssSelector("button[name=add_cart_product]")).click();
            wait.until(ExpectedConditions.textToBe(By.cssSelector("#cart .quantity"), String.valueOf(quantity + 1)));

            quantity = Integer.parseInt(driver.findElement(By.cssSelector("#cart .quantity")).getText());
            openSite();
        }
    }

    public void clearingCart(){
        int numberRowsProduct;

        openSite();
        driver.findElement(By.cssSelector("a.link[href$=checkout")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[name=confirm_order]")));

        List<WebElement> rowsProduct = driver.findElements(By.cssSelector("[name=remove_cart_item]"));
        numberRowsProduct = driver.findElements(By.cssSelector("td.item")).size();
        while(numberRowsProduct > 0) {
            wait.until(visibilityOf(rowsProduct.get(0)));
            driver.findElement(By.cssSelector("[name=remove_cart_item]")).click();
            wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("td.item"), numberRowsProduct - 1));
            rowsProduct = driver.findElements(By.cssSelector("[name=remove_cart_item]"));
            numberRowsProduct = driver.findElements(By.cssSelector("td.item")).size();
        }
    }
}
