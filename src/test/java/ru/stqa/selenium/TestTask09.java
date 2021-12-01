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
    public void isSortedCountry(){
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
            int numberZones = Integer.parseInt(countryRows.get(i).findElement(By.cssSelector("td:nth-child(6)")).getText());
            if (numberZones > 0){
                hrefEditCountry.click();
                wait.until(titleIs("Edit Country | My Store"));

                List<WebElement> zoneRows = driver.findElements(locatorZoneRows);
                int numberZonesRows = zoneRows.size() - 1;
                List<String> zones = new ArrayList<>();
                for (int j = 0; j < numberZonesRows; j++) zones.add(zoneRows.get(j).findElement(By.cssSelector("td:nth-child(3)")).getText());

                assertTrue("Список зон для " + countries.get(i) + " не отсортирован", Ordering.natural().isOrdered(zones));

                driver.get(props.getProperty("baseUrl")+"/admin/?app=countries&doc=countries");
                wait.until(titleIs("Countries | My Store"));
                countryRows = driver.findElements(locatorCountriesRows);
            }
        }
        assertTrue("Список стран не отсортирован", Ordering.natural().isOrdered( countries));
    }

    @Test
    public void isSortedGeoZones(){
        By locatorGeoZonesRows = By.cssSelector("[name = geo_zones_form] a[href *= edit_geo_zone]:not([title])");
        By locatorZones = By.cssSelector("select[name*=zone_code]>option[selected]");

        loginAdmin();
        driver.get(driver.getCurrentUrl() + "/?app=geo_zones&doc=geo_zones");
        wait.until(titleIs("Geo Zones | My Store"));

        List<WebElement> geoZones = driver.findElements(locatorGeoZonesRows);
        for (int i = 0; i < geoZones.size(); i++){
            String selectedGeoZone = geoZones.get(i).getText();
            geoZones.get(i).click();
            wait.until(titleIs("Edit Geo Zone | My Store"));

            List<WebElement> zones = driver.findElements(locatorZones);
            ArrayList<String> selectedZones = new ArrayList<>();
            for (int j = 0; j < zones.size(); j++) selectedZones.add(zones.get(j).getText());

            assertTrue("Список зон для " + selectedGeoZone + " не отсортирован", Ordering.natural().isOrdered(selectedZones));

            driver.get(driver.getCurrentUrl() + "/?app=geo_zones&doc=geo_zones");
            wait.until(titleIs("Geo Zones | My Store"));
            geoZones = driver.findElements(locatorGeoZonesRows);
        }

    }
}
