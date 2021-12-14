package ru.stqa.selenium.cucumber;

import io.cucumber.java8.En;
import ru.stqa.selenium.app.Application;

public class AddRemoveProductIncartStepdefs extends CucumberTestBase {

    public AddRemoveProductIncartStepdefs() {
        When("We add {int} products to cart", (Integer arg0) -> {
            app.addToCart(arg0);
        });
        And("open website litecart", () -> {
            app.openMainPage();
        });
        And("remove the products from the cart", () -> {
            app.clearingCart();
        });
        And("open cart", () -> {
            app.openCartMainPage();
        });
    }
}
