package com.demo.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class CheckoutPage {
    WebDriver driver;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillField(By locator, String value) {
        WebElement field = driver.findElement(locator);
        field.clear();
        field.sendKeys(value);
    }

    public void completeCheckout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://demowebshop.tricentis.com/onepagecheckout");

        if (!driver.findElements(By.id("billing-address-select")).isEmpty()) {
            Select dropdown = new Select(driver.findElement(By.id("billing-address-select")));
            dropdown.selectByVisibleText("New Address");

            fillField(By.id("BillingNewAddress_FirstName"), "John");
            fillField(By.id("BillingNewAddress_FirstName"), "John");
            fillField(By.id("BillingNewAddress_LastName"), "Doe");
            fillField(By.id("BillingNewAddress_Email"), "johndoe@example.com");
            new Select(driver.findElement(By.id("BillingNewAddress_CountryId"))).selectByVisibleText("United States");
            fillField(By.id("BillingNewAddress_City"), "New York");
            fillField(By.id("BillingNewAddress_Address1"), "123 Main Street");
            fillField(By.id("BillingNewAddress_ZipPostalCode"), "10001");
            fillField(By.id("BillingNewAddress_PhoneNumber"), "1234567890");
        }

        // Click continue for billing
        WebElement continueBilling = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#billing-buttons-container .new-address-next-step-button")));
        continueBilling.click();

        WebElement pickUpButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("PickUpInStore")));
        pickUpButton.click();

        // Wait for shipping section and click continue
        System.out.println("📦 Waiting for shipping section...");
        WebElement continueShipping = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#shipping-buttons-container .new-address-next-step-button")));
        continueShipping.click();

        // Wait for payment method section and click continue
        WebElement continuePaymentMethod = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input.button-1.payment-method-next-step-button")));
        continuePaymentMethod.click();

        // Wait for payment info section and click continue
        WebElement continuePaymentInfo = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input.button-1.payment-info-next-step-button")));
        continuePaymentInfo.click();

        // Wait for confirm order and click
        WebElement confirmOrder = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input.button-1.confirm-order-next-step-button")));
        confirmOrder.click();
    }
}