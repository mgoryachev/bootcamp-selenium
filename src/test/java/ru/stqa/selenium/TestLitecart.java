package ru.stqa.selenium;

import org.junit.Test;
import org.openqa.selenium.By;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class TestLitecart extends BaseTest{

    @Test
    public void loginAdminTest() {
        driver.get(props.getProperty("baseUrl")+"/admin");
        driver.findElement(By.name("username")).sendKeys(props.getProperty("login"));
        driver.findElement(By.name("password")).sendKeys(props.getProperty("password"));
        driver.findElement(By.name("login")).click();
        wait.until(titleIs("My Store"));
    }

}
