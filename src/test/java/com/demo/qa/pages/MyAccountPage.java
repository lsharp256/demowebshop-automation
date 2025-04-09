package com.demo.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.util.List;

public class MyAccountPage {
    WebDriver driver;

    public MyAccountPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isOrderVisible() {
        driver.findElement(By.linkText("My account")).click();
        driver.findElement(By.linkText("Orders")).click();
        List<?> orders = driver.findElements(By.cssSelector(".order-number"));
        return orders.size() > 0;
    }
}
