package ru.stqa.selenium;

import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.Properties;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class BaseTest {

    public static WebDriver driver;
    public static WebDriverWait wait;
    public static Properties props;
    public static int num;

    @Before
    public void start() throws IOException {
        if (driver != null) return;
        props = new Properties();
        props.load(BaseTest.class.getResourceAsStream("/test.properties"));
        if (props.getProperty("browser", "Firefox").equals("Firefox")){
            DesiredCapabilities caps = new DesiredCapabilities();
            if (props.getProperty("initializationMethod").equals("old")){
                caps.setCapability(FirefoxDriver.MARIONETTE, false);
                driver = new FirefoxDriver(caps);
            }
            else driver = new FirefoxDriver(new FirefoxOptions().setBinary("C:\\Program Files\\Firefox Nightly\\firefox.exe"));
        }
        else if (props.getProperty("browser").equals("Chrome"))
                driver = new ChromeDriver();
        else if (props.getProperty("browser").equals("Internet Explorer"))
                driver = new InternetExplorerDriver();
        else if (props.getProperty("browser").equals("Edge"))
                driver = new EdgeDriver();
        System.out.println(((HasCapabilities) driver).getCapabilities());
        wait = new WebDriverWait(driver, 10);

        Runtime.getRuntime().addShutdownHook(
                new Thread(()->{driver.quit();driver = null;}));
    }
    public boolean isElementPresent (By locator){
        return driver.findElements(locator).size()>0;
    }

    public void loginAdmin() {
        driver.get(props.getProperty("baseUrl")+"/admin");
        driver.findElement(By.name("username")).sendKeys(props.getProperty("login"));
        driver.findElement(By.name("password")).sendKeys(props.getProperty("password"));
        driver.findElement(By.name("login")).click();
        wait.until(titleIs("My Store"));
    }
}
