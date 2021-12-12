package ru.stqa.selenium.app;

import org.openqa.selenium.By;
import org.openqa.selenium.HasCapabilities;
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
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.stqa.selenium.pages.CartPage;
import ru.stqa.selenium.pages.MainPage;
import ru.stqa.selenium.pages.ProductPage;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class Application {

    private WebDriverWait wait;
    private EventFiringWebDriver driver;
    public static Properties props;
    private MainPage mainPage;
    private ProductPage productPage;
    private CartPage cartPage;

    public Application() {
        props = new Properties();
        try {
            props.load(Application.class.getResourceAsStream("/test.properties"));
            switch (props.getProperty("browser")) {
                case "Firefox":
                    DesiredCapabilities caps = new DesiredCapabilities();
                    if (props.getProperty("initializationMethod").equals("old")) {
                        caps.setCapability(FirefoxDriver.MARIONETTE, false);
                        driver = new EventFiringWebDriver(new FirefoxDriver(caps));
                    } else driver = new EventFiringWebDriver(new FirefoxDriver(new FirefoxOptions()
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
        } catch (IOException e) {
            System.out.println(e);
        }

        System.out.println(((HasCapabilities) driver).getCapabilities());
        wait = new WebDriverWait(driver, 10);

        mainPage = new MainPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
    }

    public void quit() {
        driver.quit();
    }

    public void addToCart(int numberProduct) {
        mainPage.open();
        int quantity = Integer.parseInt(mainPage.quantityInCart().getText());
        while (quantity < numberProduct) {
            mainPage.openProductPage(mainPage.products().get(0));
            if (productPage.isSelectorSize()) productPage.selectSize("Small");
            productPage.addToCart();
            mainPage.open();
            quantity = Integer.parseInt(mainPage.quantityInCart().getText());
        }
    }

    public void clearingCart(){
        int numberRowsProduct;
        mainPage.openCart();
        numberRowsProduct = cartPage.buttonsRemoveProduct().size();
        while(numberRowsProduct > 0) {
            cartPage.removeProduct(cartPage.buttonsRemoveProduct().get(0));
            numberRowsProduct = driver.findElements(By.cssSelector("td.item")).size();
        }
    }
}
