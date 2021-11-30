package ru.stqa.selenium;

import com.google.common.collect.Ordering;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import static org.junit.Assert.assertTrue;

public class TestTask09 extends BaseTest{

    @Test
    public void sortedCountry(){
        loginAdmin();
        driver.get(driver.getCurrentUrl() + "/?app=countries&doc=countries");
        wait.until(titleIs("Countries | My Store"));

        List<String> countries = new ArrayList<>();
        By locatorCountriesRows = By.cssSelector(".dataTable tr:not(.header):not(.footer)");
        By locatorZoneRows = By.cssSelector("#table-zones tr:not(.header)");
        List<WebElement> countryRows = driver.findElements(locatorCountriesRows);
        int numberCountries = countryRows.size();

        for (int i = 0; i < numberCountries; i++){
            WebElement hrefEditCountry = countryRows.get(i).findElement(By.cssSelector("a[href *= edit_country]:not([title])"));
            countries.add(hrefEditCountry.getAttribute("textContent"));
            int numberZones = Integer.parseInt(countryRows.get(i).findElement(By.cssSelector("td:nth-child(6)")).getAttribute("textContent"));
            if (numberZones > 0){
                hrefEditCountry.click();
                wait.until(titleIs("Edit Country | My Store"));

                List<WebElement> zoneRows = driver.findElements(locatorZoneRows);
                int numberZonesRows = zoneRows.size() - 1;
                List<String> zones = new ArrayList<>();
                for (int j = 0; j < numberZonesRows; j++){
                    zones.add(zoneRows.get(j).findElement(By.cssSelector("td:nth-child(3)")).getText());
                }

                try {
                    assertTrue(Ordering.natural().isOrdered(zones));
                }
                catch (AssertionError e){
                    System.out.println("Список зон для " + countries.get(i) + " не отсортирован");
                    throw e;
                }
                System.out.println("Список зон для " + countries.get(i) + " отсортирован");

                driver.get(props.getProperty("baseUrl")+"/admin/?app=countries&doc=countries");
                wait.until(titleIs("Countries | My Store"));
                countryRows = driver.findElements(locatorCountriesRows);
            }
        }
        try {
            assertTrue(Ordering.natural().isOrdered(countries));
        }
        catch (AssertionError e){
            System.out.println("Список стран не отсортирован");
            throw e;
        }
        System.out.println("Список стран отсортирован");
    }

    @Test
    public void sortedZones(){
        loginAdmin();
        driver.get(driver.getCurrentUrl() + "/?app=geo_zones&doc=geo_zones");
        wait.until(titleIs("Geo Zones | My Store"));

    }
}
