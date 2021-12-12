package ru.stqa.selenium;

import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.Set;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class BaseTest {

    public static EventFiringWebDriver driver;
    public static WebDriverWait wait;
    public static Properties props;

    public static class MyListener extends AbstractWebDriverEventListener {

    }

    @Before
    public void start() throws IOException {
        if (driver != null) return;
        props = new Properties();
        props.load(BaseTest.class.getResourceAsStream("/test.properties"));

        switch(props.getProperty("browser")){
            case "Firefox":
                DesiredCapabilities caps = new DesiredCapabilities();
                if (props.getProperty("initializationMethod").equals("old")){
                    caps.setCapability(FirefoxDriver.MARIONETTE, false);
                    driver = new EventFiringWebDriver(new FirefoxDriver(caps));
                }
                else driver = new EventFiringWebDriver(new FirefoxDriver(new FirefoxOptions()
                        .setBinary("C:\\Program Files\\Firefox Nightly\\firefox.exe")));
                break;
            case "Chrome":
                driver = new EventFiringWebDriver(new ChromeDriver());
                break;
            case "Internet Explorer":
                driver = new EventFiringWebDriver(new InternetExplorerDriver());
                break;
            case "Edge":
                driver = new EventFiringWebDriver(new EdgeDriver());
                break;
            case "rm-ch":
                driver = new EventFiringWebDriver(new RemoteWebDriver(new URL(props.getProperty("hub")), new ChromeOptions()));
                break;
            case "rm-ie":
                driver = new EventFiringWebDriver(new RemoteWebDriver(new URL(props.getProperty("hub")), new InternetExplorerOptions()));
                break;
            case "rm-ff":
                driver = new EventFiringWebDriver(new RemoteWebDriver(new URL(props.getProperty("hub")), new FirefoxOptions()));
                break;
            case "rm-edge":
                driver = new EventFiringWebDriver(new RemoteWebDriver(new URL(props.getProperty("hub")), new EdgeOptions()));
        }
        driver.register(new MyListener());
        System.out.println(((HasCapabilities) driver).getCapabilities());
        wait = new WebDriverWait(driver, 10);

        Runtime.getRuntime().addShutdownHook(
                new Thread(()->{driver.quit();driver = null;}));
    }
    public boolean isElementPresent (By locator){
        return driver.findElements(locator).size()>0;
    }
    public boolean isElementPresent (WebElement webElement, By locator){
        return  webElement.findElements(locator).size()>0;
    }

    public void openSite() {
        driver.get(props.getProperty("baseUrl") + "/" + props.getProperty("lang") + "/");
        wait.until(titleIs("Online Store | My Store"));
    }
    public void loginAdmin() {
        driver.get(props.getProperty("baseUrl")+"/admin");
        if (isElementPresent(By.cssSelector("li#app-"))) return;
        driver.findElement(By.name("username")).sendKeys(props.getProperty("login"));
        driver.findElement(By.name("password")).sendKeys(props.getProperty("password"));
        driver.findElement(By.name("login")).click();
        wait.until(titleIs("My Store"));
    }

    public ExpectedCondition<String> anyWindowOtherThan(Set oldWindows){
        return new ExpectedCondition<String>(){
            public String apply(WebDriver driver){
                Set<String> handles = driver.getWindowHandles();
                handles.removeAll(oldWindows);
                return handles.size() > 0 ? handles.iterator().next() : null;
            }
        };
    }
}