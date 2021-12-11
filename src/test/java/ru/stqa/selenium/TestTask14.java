package ru.stqa.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Set;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class TestTask14 extends BaseTest{

    @Test
    public void isOpenNewWindowOnExternalLink(){
        String newWindow = "", mainWindow;
        Set windowsHandles;
        loginAdmin();
        driver.get(driver.getCurrentUrl() + "/?app=countries&doc=countries");
        wait.until(titleIs("Countries | My Store"));
        driver.findElement(By.cssSelector("a[href *= edit_country]:not([title])")).click();
        wait.until(titleIs("Add New Country | My Store"));
        List<WebElement> externalLinks = driver.findElements(By.xpath("//i[contains(@class,'fa-external-link')]/.."));
        mainWindow = driver.getWindowHandle();
        windowsHandles = driver.getWindowHandles();
        for (WebElement link:externalLinks){
            String href = link.getAttribute("href");
            link.click();
            try {
                newWindow = wait.until(anyWindowOtherThan(windowsHandles));
            }catch (TimeoutException e){
                System.out.println("Ссылка " + href + " не открывается в новом окне");
            }
            driver.switchTo().window(newWindow);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("title")));
            driver.close();
            driver.switchTo().window(mainWindow);
        }
    }
}
