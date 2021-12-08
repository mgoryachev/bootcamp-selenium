package ru.stqa.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import java.util.Date;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import static org.junit.Assert.assertTrue;

public class TestTask11 extends BaseTest{

    @Test
    public void testAccount(){
        String userMail = "test-user-" + genUniquePart() + "@q.a";
        String userPassword = "password";

        createAccount(userMail, userPassword);
        driver.findElement(By.cssSelector("a[href $=\\/logout")).click();
        loginCustomer(userMail, userPassword);
        driver.findElement(By.cssSelector("a[href $=\\/logout")).click();

        wait.until(titleIs("Online Store | My Store"));
    }


    private String genUniquePart(){
        String str = new Date().toString();
        str = str.replace(" ", "");
        return  str.replace(":", "");
    }

    private void createAccount(String mail, String password){
        openSite();
        driver.findElement(By.cssSelector("a[href *= create_account]")).click();
        wait.until(titleIs("Create Account | My Store"));
        driver.findElement(By.cssSelector("[name=firstname]")).sendKeys("testFirstName");
        driver.findElement(By.cssSelector("[name=lastname]")).sendKeys("testLastName");
        driver.findElement(By.cssSelector("[name=address1]")).sendKeys("testStreet, 7");
        driver.findElement(By.cssSelector("[name=postcode]")).sendKeys("12345");
        driver.findElement(By.cssSelector("[name=city]")).sendKeys("testCity");
        driver.findElement(By.cssSelector(".select2")).click();
        driver.findElement(By.cssSelector(".select2-results__option[id $=-US]")).click();

        driver.findElement(By.cssSelector("select[name=zone_code]")).click();

        wait.until(d -> d.findElement(By.cssSelector("[value=CA]")));
        Select select = new Select(driver.findElement(By.cssSelector("select[name=zone_code]")));
        select.selectByValue("CA");

        driver.findElement(By.cssSelector("[name=email]")).sendKeys(mail);
        driver.findElement(By.cssSelector("[name=phone]")).sendKeys("+79999999999");
        driver.findElement(By.cssSelector("[name=password]")).sendKeys(password);
        driver.findElement(By.cssSelector("[name=confirmed_password]")).sendKeys(password);
        driver.findElement(By.cssSelector("[name=create_account]")).click();

        wait.until(d -> d.findElement(By.cssSelector(".notice")));
        assertTrue("Аккаунт создать не удалось", isElementPresent(By.cssSelector(".notice.success")));
    }

    private void loginCustomer(String mail, String password) {
        driver.findElement(By.cssSelector("input[name=email]")).sendKeys(mail);
        driver.findElement(By.cssSelector("input[name=password]")).sendKeys(password);
        driver.findElement(By.cssSelector("button[name=login]")).click();

        wait.until(d -> d.findElement(By.cssSelector(".notice")));
        assertTrue("Не удалось войти в аккаунт", isElementPresent(By.cssSelector(".notice.success")));
    }
    
}
