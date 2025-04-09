package com.demo.qa.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class DesktopsPage {
    WebDriver driver;

    public DesktopsPage(WebDriver driver) {
        this.driver = driver;
    }

    private By sortDropdown = By.id("products-orderby");
    private By addToCartButtons = By.cssSelector(".product-box-add-to-cart-button");

    public void sortAllOptions() {
        Select select = new Select(driver.findElement(sortDropdown));
        for (WebElement option : select.getOptions()) {
            select.selectByVisibleText(option.getText());
        }
    }

    public void addAllDesktopsToCart() {
        List<WebElement> buttons = driver.findElements(addToCartButtons);
        for (WebElement button : buttons) {
            button.click();
        }
    }
}
