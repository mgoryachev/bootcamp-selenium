package ru.stqa.selenium;

import org.junit.Test;
import org.openqa.selenium.By;

import static org.junit.Assert.assertTrue;

public class TestTask07 extends BaseTest{

    String childCssSelector = ".selected > ul > li";
    String mainCssSelector = "li#app-";

    @Test
    public void testAdminMenu(){
        loginAdmin();
        clickItem(mainCssSelector);
    }

    void clickItem(String str){
        int j;
        if (str.equals(mainCssSelector)) j = 0;
            else j = 1;
        By locator = By.cssSelector(str);
        int numberMenuItem = driver.findElements(locator).size();
        for (int i = j; i < numberMenuItem; i++) {
            driver.findElements(locator).get(i).click();
            assertTrue("Нет заголовка по пути " + driver.getCurrentUrl(), isElementPresent(By.cssSelector("h1")));
            System.out.println("Заголовок - " + driver.findElement(By.cssSelector("h1")).getText());
            if (isElementPresent(By.cssSelector(str + childCssSelector))) clickItem(str + childCssSelector);
        }
    }
}
