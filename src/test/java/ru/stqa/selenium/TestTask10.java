package ru.stqa.selenium;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;

import static org.junit.Assert.assertTrue;

public class TestTask10 extends BaseTest {

    WebElement campaignsBox, product;

    @Before
    public void init(){
        openSite();
        campaignsBox = driver.findElement(By.cssSelector("div#box-campaigns"));
        product = campaignsBox.findElement(By.cssSelector(".product"));
    }

    @Test
    public void equalsName(){
        String nameProductMainSheet, nameProductDetailsSheet;

        nameProductMainSheet = product.findElement(By.cssSelector(".name")).getText();
        product.findElement(By.cssSelector("a.link")).click();

        nameProductDetailsSheet = driver.findElement(By.cssSelector("[itemprop=name]")).getText();

        assertTrue("Название товаров на главной странице и странице товара не соответствует",
                nameProductDetailsSheet.equals(nameProductMainSheet));
    }

    @Test
    public void equlsPricies(){
        String regularPriceMainSheet, regularPriceDetailsSheet;
        String campaignPriceMainSheet, campaignPriceDetailsSheet;

        regularPriceMainSheet = product.findElement(By.cssSelector(".regular-price")).getText();
        campaignPriceMainSheet = product.findElement(By.cssSelector(".campaign-price")).getText();
        product.findElement(By.cssSelector("a.link")).click();

        regularPriceDetailsSheet = driver.findElement(By.cssSelector(".information .regular-price")).getText();
        campaignPriceDetailsSheet = driver.findElement(By.cssSelector(".information .campaign-price")).getText();

        assertTrue("Регулярная цена товара на главной странице и странице товара не совпадает",
                regularPriceDetailsSheet.equals(regularPriceMainSheet));
        assertTrue("Акционная цена товара на главной странице и странице товара не совпадает",
                campaignPriceDetailsSheet.equals(campaignPriceMainSheet));

    }

    @Test
    public void isCrossedGreyRegularPrice(){
        assertTrue("Регулярная цена не зачеркнута на главной странице",
                isCssCorrect(product.findElement(By.cssSelector(".regular-price")), "text-decoration-line", "line-through"));
        assertTrue("Цвет регулярной цены на главной странице не серый",
                isGrey(product.findElement(By.cssSelector(".regular-price"))));

        product.findElement(By.cssSelector("a.link")).click();
        product = driver.findElement(By.cssSelector("#box-product"));

        assertTrue("Регулярная цена не зачеркнута на странице продукта",
                isCssCorrect(product.findElement(By.cssSelector(".regular-price")), "text-decoration-line", "line-through"));
        assertTrue("Цвет регулярной цены на странице продукта не серый",
                isGrey(product.findElement(By.cssSelector(".regular-price"))));
    }

    @Test
    public void isBoldRedCampaignPrice(){
        assertTrue("Акционная цена не выделена жирным на главной странице",
                isBold(product.findElement(By.cssSelector(".campaign-price"))));
        assertTrue("Цвет акционной цены на главной странице не красный",
                isRed(product.findElement(By.cssSelector(".campaign-price"))));

        product.findElement(By.cssSelector("a.link")).click();
        product = driver.findElement(By.cssSelector("#box-product"));

        assertTrue("Акционная цена не выделена жирным на странице продукта",
                isBold(product.findElement(By.cssSelector(".campaign-price"))));
        assertTrue("Цвет акционной цены на странице продукта не красный",
                isRed(product.findElement(By.cssSelector(".campaign-price"))));
    }

    @Test
    public void sizePrice(){
        float sizeFontRegularPrice, sizeFontCampaignPrice;

        sizeFontRegularPrice = Float.parseFloat(product.findElement(By.cssSelector(".regular-price"))
                .getCssValue("font-size").replace("px", ""));
        sizeFontCampaignPrice = Float.parseFloat(product.findElement(By.cssSelector(".campaign-price"))
                .getCssValue("font-size").replace("px", ""));
        assertTrue("Шрифт акционной цены не большой шрифта регулярной на главной странице",
                sizeFontCampaignPrice > sizeFontRegularPrice);

        product.findElement(By.cssSelector("a.link")).click();
        product = driver.findElement(By.cssSelector("#box-product"));

        sizeFontRegularPrice = Float.parseFloat(product.findElement(By.cssSelector(".regular-price"))
                .getCssValue("font-size").replace("px", ""));
        sizeFontCampaignPrice = Float.parseFloat(product.findElement(By.cssSelector(".campaign-price"))
                .getCssValue("font-size").replace("px", ""));
        assertTrue("Шрифт акционной цены не большой шрифта регулярной на странице продукта",
                sizeFontCampaignPrice > sizeFontRegularPrice);
    }

    public boolean isCssCorrect(WebElement webElement, String style, String value){
        return value.equals(webElement.getCssValue(style));
    }

    public boolean isGrey(WebElement webElement){
        Color color = Color.fromString(webElement.getCssValue("color"));
        return color.getColor().getRed() == color.getColor().getGreen() | color.getColor().getRed() == color.getColor().getBlue();
    }

    public boolean isBold(WebElement webElement){
        return Integer.parseInt(webElement.getCssValue("font-weight")) > 599;
    }

    public boolean isRed(WebElement webElement){
        Color color = Color.fromString(webElement.getCssValue("color"));
        return color.getColor().getGreen() == 0 | color.getColor().getBlue() == 0;
    }
}
