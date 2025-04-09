package com.demo.qa.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginHelper {
    public static void login(WebDriver driver, String email, String password) {
        driver.get("https://demowebshop.tricentis.com/login");

        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("Password")).sendKeys(password);
        driver.findElement(By.cssSelector("input.login-button")).click();
    }
}
