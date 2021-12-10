package ru.stqa.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class TestTask12 extends BaseTest{

    @Test
    public void checkAddProduct(){
        Path pic = Path.of("src\\test\\resources\\seal.jpg").toAbsolutePath();
        String absolutePathPic = pic.toString();
        String nameProduct = "Чудо-юдо";
        int numberProducts = 0;

        loginAdmin();
        driver.findElement(By.cssSelector("a[href$='app=catalog&doc=catalog']")).click();
        wait.until(d->d.findElement(By.cssSelector("a[href$='app=catalog&doc=edit_product']")));

        List<WebElement> linksData = driver.findElements(By.cssSelector("table.dataTable tr.row a:not([title])"));
        numberProducts =  countProducts(linksData, nameProduct);

        driver.findElement(By.cssSelector("a[href$='app=catalog&doc=edit_product']")).click();
        driver.findElement(By.cssSelector("input[name=status][value='1']")).click();

        driver.findElement(By.cssSelector("input[name^=name]")).sendKeys(nameProduct);
        driver.findElement(By.cssSelector("input[name^=product_groups][value='1-3'")).click();

        WebElement inputQuantity = driver.findElement(By.cssSelector("input[name=quantity]"));
        Actions builder = new Actions(driver);
        builder.click(inputQuantity).keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform();
        builder.sendKeys("20").perform();
        Select soldOutStatus = new Select(driver.findElement(By.cssSelector("select[name=sold_out_status_id]")));
        soldOutStatus.selectByValue("");

        driver.findElement(By.cssSelector("input[name^=new_images")).sendKeys(absolutePathPic);

        driver.findElement(By.cssSelector("a[href='#tab-information']")).click();
        wait.until(d -> d.findElement(By.cssSelector(".trumbowyg-editor")));

        driver.findElement(By.cssSelector(".trumbowyg-editor")).sendKeys("Чудо-юдо рыба-кит");
        driver.findElement(By.cssSelector("input[name^=head_title]")).sendKeys("Чудо-юдо");
        driver.findElement(By.cssSelector("a[href='#tab-prices']")).click();
        wait.until(d -> d.findElement(By.cssSelector("input[name='prices[USD]']")));

        driver.findElement(By.cssSelector("input[name='prices[USD]']")).sendKeys("12");
        driver.findElement(By.cssSelector("input[name='prices[EUR]']")).sendKeys("10");
        driver.findElement(By.cssSelector("button[name=save][value=Save]")).click();

        driver.findElement(By.cssSelector("a[href$='app=catalog&doc=catalog']")).click();
        wait.until(d -> d.findElement(By.cssSelector("a[href$='app=catalog&doc=edit_product']")));

        linksData = driver.findElements(By.cssSelector("table.dataTable tr.row a:not([title])"));
        assertTrue("Продукт не добавлен", numberProducts + 1 == countProducts(linksData, nameProduct));
    }

    private int countProducts(List<WebElement> elements, String value){
        int count = 0;
        for (WebElement element:elements) if (value.equals(element.getText())) count++;
        return count;
    }
}
