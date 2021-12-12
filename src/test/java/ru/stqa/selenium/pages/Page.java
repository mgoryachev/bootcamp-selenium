package ru.stqa.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.stqa.selenium.app.Application;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class Page {

    protected EventFiringWebDriver driver;
    protected WebDriverWait wait;

    public Page(EventFiringWebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
    }

    public void open() {
        driver.get(Application.props.getProperty("baseUrl"));
        wait.until(presenceOfElementLocated(By.tagName("title")));
    }

    public void open(String url) {
        driver.get(Application.props.getProperty("baseUrl") + url);
        wait.until(presenceOfElementLocated(By.tagName("title")));
    }

    public WebElement quantityInCart() { return driver.findElement(By.cssSelector("#cart .quantity"));}

    public boolean isElementPresent (By locator){
        return driver.findElements(locator).size()>0;
    }
}
