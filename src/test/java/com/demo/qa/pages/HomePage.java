package com.demo.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    private WebDriver driver;

    private By registerLink = By.className("ico-register");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickRegister() {
        driver.findElement(registerLink).click();
    }

    private By computersTab = By.linkText("Computers");
    private By desktopsLink = By.linkText("Desktops");

    public void navigateToDesktops() {
        driver.findElement(computersTab).click();
        driver.findElement(desktopsLink).click();
    }
}
