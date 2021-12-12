package ru.stqa.selenium;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class TestTask17 extends BaseTest{
    @Test
    public void noLogsOnBrowser(){
        By locatorProducts = By.cssSelector("table.dataTable tr.row a[href*='category_id=1']:not([title])");
        String pathProductsCategory = props.getProperty("baseUrl")+"/admin/?app=catalog&doc=catalog&category_id=1";
        loginAdmin();
        driver.get(pathProductsCategory);
        int numberLinks = driver.findElements(locatorProducts).size();
        for (int i = 0; i < numberLinks; i++){
            driver.findElements(locatorProducts).get(i).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("title")));
            Assert.assertFalse("В логах браузера есть записи" + driver.manage().logs().get("browser").getAll(),
                    driver.manage().logs().get("browser").getAll().size() > 0);
            driver.get(pathProductsCategory);
        }
    }
}
