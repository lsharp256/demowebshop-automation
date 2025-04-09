package com.demo.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage {
    WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    private By cartLink = By.linkText("Shopping cart");
    private By unitPrices = By.cssSelector(".product-unit-price");
    private By cartTotal = By.cssSelector(".cart-total-right");
    private By removeCheckbox = By.cssSelector(".remove-from-cart input");
    private By updateCart = By.name("updatecart");

    public void openCart() {
        driver.findElement(cartLink).click();
    }

    public double getTotalPrice() {
        List<WebElement> prices = driver.findElements(unitPrices);
        double total = 0;
        for (WebElement price : prices) {
            total += Double.parseDouble(price.getText().replace("$", ""));
        }
        return total;
    }

    public double getCartTotalFromPage() {
        WebElement total = driver.findElement(cartTotal);
        return Double.parseDouble(total.getText().replace("$", ""));
    }

    public void removeFirstItem() {
        driver.findElement(removeCheckbox).click();
        driver.findElement(updateCart).click();
    }
}
