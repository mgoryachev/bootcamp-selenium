package ru.stqa.selenium.tests;

import org.junit.Test;

public class TestTask19 extends TestBase {

    @Test
    public void checkAddRemoveProductCart(){
        int numberProductInCart = 3;
        app.openMainPage();
        app.addToCart(numberProductInCart);
        app.openCartMainPage();
        app.clearingCart();
    }
}
