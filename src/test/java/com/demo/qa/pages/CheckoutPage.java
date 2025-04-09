package com.demo.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class CheckoutPage {
    WebDriver driver;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public void completeCheckout() {
        driver.findElement(By.cssSelector(".checkout-button")).click();

        driver.findElement(By.id("BillingNewAddress_FirstName")).sendKeys("John");
        driver.findElement(By.id("BillingNewAddress_LastName")).sendKeys("Doe");
        driver.findElement(By.id("BillingNewAddress_Email")).sendKeys("johndoe@example.com");
        driver.findElement(By.id("BillingNewAddress_City")).sendKeys("New York");
        driver.findElement(By.id("BillingNewAddress_Address1")).sendKeys("123 Main Street");
        driver.findElement(By.id("BillingNewAddress_ZipPostalCode")).sendKeys("10001");
        driver.findElement(By.id("BillingNewAddress_PhoneNumber")).sendKeys("1234567890");

        new Select(driver.findElement(By.id("BillingNewAddress_CountryId"))).selectByVisibleText("United States");

        driver.findElement(By.cssSelector("input.button-1.new-address-next-step-button")).click();
        driver.findElement(By.cssSelector("input.button-1.shipping-method-next-step-button")).click();
        driver.findElement(By.cssSelector("input.button-1.payment-method-next-step-button")).click();
        driver.findElement(By.cssSelector("input.button-1.payment-info-next-step-button")).click();
        driver.findElement(By.cssSelector("input.button-1.confirm-order-next-step-button")).click();
    }

    public boolean isOrderConfirmed() {
        return driver.findElement(By.className("order-completed")).isDisplayed();
    }
}
