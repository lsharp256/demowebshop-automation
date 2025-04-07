package com.demo.qa.tests;

import com.demo.qa.pages.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.UUID;

public class RegisterTest extends BaseTest {

    @Test
    public void testClickRegister() {
        HomePage home = new HomePage(driver);
        home.clickRegister();

        String expectedTitle = "Demo Web Shop. Register";
        assert driver.getTitle().equals(expectedTitle) : "Register page title mismatch";
    }

    @Test
    public void registerNewUser() {
        driver.get("https://demowebshop.tricentis.com/register");

        // Select Gender
        WebElement genderMale = driver.findElement(By.id("gender-male"));
        genderMale.click();

        // Fill in First Name and Last Name
        driver.findElement(By.id("FirstName")).sendKeys("Leroy");
        driver.findElement(By.id("LastName")).sendKeys("Sharp");

        // Dynamic email to avoid duplicate errors
        String email = "leroy_" + UUID.randomUUID().toString().substring(0, 5) + "@test.com";
        driver.findElement(By.id("Email")).sendKeys(email);

        // Fill in password and confirm
        driver.findElement(By.id("Password")).sendKeys("Test1234!");
        driver.findElement(By.id("ConfirmPassword")).sendKeys("Test1234!");

        // Submit the form
        driver.findElement(By.id("register-button")).click();

        // Assert success
        WebElement result = driver.findElement(By.className("result"));
        Assert.assertTrue(result.getText().contains("Your registration completed"), "Registration failed!");
    }
}
