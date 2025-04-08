package com.demo.qa.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.demo.qa.utils.ConfigReader;

public class LoginTest extends BaseTest {

    @Test
    public void loginWithValidCredentials() {
        driver.get("https://demowebshop.tricentis.com/login");

        String email = ConfigReader.get("email");
        String password = ConfigReader.get("password");

        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("Password")).sendKeys(password);

        driver.findElement(By.cssSelector("input.login-button")).click();

        WebElement logoutLink = driver.findElement(By.className("ico-logout"));
        Assert.assertTrue(logoutLink.isDisplayed(), "Login failed!");
    }

    @Test
    public void loginWithInvalidCredentials() {
        driver.get("https://demowebshop.tricentis.com/login");

        String email = "invaliduser@test.com"; // Intentionally wrong
        String wrongPassword = "InvalidPass123";

        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("Password")).sendKeys(wrongPassword);
        driver.findElement(By.cssSelector("input.login-button")).click();

        WebElement errorBox = driver.findElement(By.cssSelector("div.validation-summary-errors"));

        // You can also break this into smaller asserts if you want to check both lines
        String errorText = errorBox.getText();

        Assert.assertTrue(errorText.contains("Login was unsuccessful"), "Expected login failure message missing");
        Assert.assertTrue(errorText.contains("No customer account found"), "Expected 'No customer account found' message missing");
    }

}
