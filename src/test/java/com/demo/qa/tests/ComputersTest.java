package com.demo.qa.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;

public class ComputersTest extends BaseTest {
    @Test
    public void testDesktopPurchaseAndOrderValidation() {
        // Click on "Computers" and then select "Desktops"
        driver.findElement(By.linkText("Computers")).click();
        driver.findElement(By.linkText("Desktops")).click();

        // Sort desktops by all the available options in the sorting dropdown
        WebElement sortDropdown = driver.findElement(By.id("products-orderby")); // Replace with actual ID
        Select select = new Select(sortDropdown);
        List<WebElement> sortingOptions = select.getOptions();

        for (WebElement option : sortingOptions) {
            select.selectByVisibleText(option.getText()); // Sort the products
            // (Optional) Validate sorting behavior if necessary, e.g., check page order
        }

        // Add all available desktops to the cart
        List<WebElement> addToCartButtons = driver.findElements(By.cssSelector(".product-box-add-to-cart-button")); // Replace with actual selector
        for (WebElement button : addToCartButtons) {
            button.click();
        }

        // Navigate to the cart and validate the total price
        driver.findElement(By.linkText("Shopping cart")).click();
        double totalPrice = 0.0;

        List<WebElement> prices = driver.findElements(By.cssSelector(".product-unit-price")); // Replace with actual selector
        for (WebElement price : prices) {
            totalPrice += Double.parseDouble(price.getText().replace("$", ""));
        }

        WebElement cartTotalElement = driver.findElement(By.cssSelector(".cart-total-price")); // Replace with actual selector
        double cartTotal = Double.parseDouble(cartTotalElement.getText().replace("$", ""));

        Assert.assertEquals(totalPrice, cartTotal, "Total price in cart doesn't match!");

        // Remove an item from the cart
        driver.findElement(By.cssSelector(".remove-from-cart input")).click(); // Click remove (Use proper selector)
        driver.findElement(By.name("updatecart")).click(); // Update cart after removing

        // Validate updated total price in cart
        totalPrice = 0.0;
        prices = driver.findElements(By.cssSelector(".product-unit-price"));
        for (WebElement price : prices) {
            totalPrice += Double.parseDouble(price.getText().replace("$", ""));
        }
        cartTotal = Double.parseDouble(cartTotalElement.getText().replace("$", ""));

        Assert.assertEquals(totalPrice, cartTotal, "Updated total price in cart doesn't match!");

        // Checkout process
        driver.findElement(By.cssSelector(".checkout")).click(); // Proceed to checkout (Use proper selector)

        // Fill in required details for checkout
        driver.findElement(By.id("BillingNewAddress_FirstName")).sendKeys("John"); // Example data
        driver.findElement(By.id("BillingNewAddress_LastName")).sendKeys("Doe");
        driver.findElement(By.id("BillingNewAddress_Email")).sendKeys("johndoe@example.com");
        driver.findElement(By.id("BillingNewAddress_City")).sendKeys("New York");
        driver.findElement(By.id("BillingNewAddress_Address1")).sendKeys("123 Main Street");
        driver.findElement(By.id("BillingNewAddress_ZipPostalCode")).sendKeys("10001");
        driver.findElement(By.id("BillingNewAddress_PhoneNumber")).sendKeys("1234567890");
        driver.findElement(By.id("BillingNewAddress_CountryId")).click();
        new Select(driver.findElement(By.id("BillingNewAddress_CountryId"))).selectByVisibleText("United States");
        driver.findElement(By.cssSelector(".continue-button")).click(); // Move to next steps of checkout

        // Complete checkout process (Shipping and Payment steps may vary)
        driver.findElement(By.cssSelector(".confirm-order-next-step-button")).click(); // Example final step button

        // Validate checkout success
        WebElement orderConfirmation = driver.findElement(By.cssSelector(".order-completed")); // Use proper locator
        Assert.assertTrue(orderConfirmation.isDisplayed(), "Order confirmation not displayed!");

        // Navigate to "My Account" and validate the order
        driver.findElement(By.linkText("My account")).click();
        driver.findElement(By.linkText("Orders")).click();
        List<WebElement> orders = driver.findElements(By.cssSelector(".order-number"));
        Assert.assertTrue(orders.size() > 0, "No orders found in my account!");
    }
}