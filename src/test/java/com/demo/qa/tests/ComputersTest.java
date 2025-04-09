package com.demo.qa.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class ComputersTest extends BaseTest {

    @Test
    public void testDesktopPurchaseAndOrderValidation() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Click on "Computers" and then select "Desktops"
        driver.findElement(By.linkText("Computers")).click();
        driver.findElement(By.linkText("Desktops")).click();

        // Sort desktops by all available options in the sorting dropdown
        for (int i = 0; i < new Select(driver.findElement(By.id("products-orderby"))).getOptions().size(); i++) {
            WebElement sortDropdown = driver.findElement(By.id("products-orderby"));
            Select select = new Select(sortDropdown);
            String optionText = select.getOptions().get(i).getText();

            select.selectByVisibleText(optionText);

            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".product-item")));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 🛒 Visit each desktop product individually and add it to cart
        List<WebElement> productLinks = driver.findElements(By.cssSelector(".product-title a"));
        System.out.println("🖥️ Found " + productLinks.size() + " desktop product(s).");

        for (WebElement productLink : productLinks) {
            String productName = productLink.getText();
            String productUrl = productLink.getAttribute("href");

            System.out.println("➡️ Visiting product: " + productName);
            driver.get(productUrl);

            try {
                WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[value='Add to cart']")));
                // Handle options for Simple Computer
                if (productName.equalsIgnoreCase("Simple Computer")) {
                    WebElement slowOption = driver.findElement(By.id("product_attribute_75_5_31_96"));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", slowOption);
                    System.out.println("⚙️ Selected 'Slow' processor for Simple Computer");
                }

                // Click the button
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCartButton);

                try {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("bar-notification")));
                    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("bar-notification")));
                } catch (Exception e) {
                    System.out.println("⚠️ No bar notification for " + productName + " (may still have added to cart).");
                }

            } catch (Exception e) {
                System.out.println("❌ Could not add " + productName + ": " + e.getMessage());
            }
        }

        // ✅ Navigate to the cart and validate the total price
        driver.findElement(By.linkText("Shopping cart")).click();
        double totalPrice = 0.0;

        List<WebElement> prices = driver.findElements(By.cssSelector(".product-unit-price"));
        for (WebElement price : prices) {
            totalPrice += Double.parseDouble(price.getText().replace("$", "").trim());
        }

        WebElement cartTotalElement = driver.findElement(By.cssSelector(".cart-total-right strong"));
        double cartTotal = Double.parseDouble(cartTotalElement.getText().replace("$", "").trim());

        Assert.assertEquals(cartTotal, totalPrice, "Total price in cart doesn't match!");

        // 🗑️ Remove an item from the cart
        driver.findElement(By.cssSelector(".remove-from-cart input")).click();
        driver.findElement(By.name("updatecart")).click();

        // 🔄 Recalculate total after item removed
        totalPrice = 0.0;
        prices = driver.findElements(By.cssSelector(".product-unit-price"));
        for (WebElement price : prices) {
            totalPrice += Double.parseDouble(price.getText().replace("$", "").trim());
        }

        cartTotalElement = driver.findElement(By.cssSelector(".cart-total-right strong"));
        cartTotal = Double.parseDouble(cartTotalElement.getText().replace("$", "").trim());

        Assert.assertEquals(cartTotal, totalPrice, "Updated total price in cart doesn't match!");

        // ✅ Checkout process
        driver.findElement(By.cssSelector(".checkout-button")).click();

        // Fill in billing details
        driver.findElement(By.id("BillingNewAddress_FirstName")).sendKeys("John");
        driver.findElement(By.id("BillingNewAddress_LastName")).sendKeys("Doe");
        driver.findElement(By.id("BillingNewAddress_Email")).sendKeys("johndoe@example.com");
        driver.findElement(By.id("BillingNewAddress_City")).sendKeys("New York");
        driver.findElement(By.id("BillingNewAddress_Address1")).sendKeys("123 Main Street");
        driver.findElement(By.id("BillingNewAddress_ZipPostalCode")).sendKeys("10001");
        driver.findElement(By.id("BillingNewAddress_PhoneNumber")).sendKeys("1234567890");
        new Select(driver.findElement(By.id("BillingNewAddress_CountryId"))).selectByVisibleText("United States");
        driver.findElement(By.cssSelector("input.button-1.new-address-next-step-button")).click();

        // Continue through checkout steps
        driver.findElement(By.cssSelector("input.button-1.shipping-method-next-step-button")).click();
        driver.findElement(By.cssSelector("input.button-1.payment-method-next-step-button")).click();
        driver.findElement(By.cssSelector("input.button-1.payment-info-next-step-button")).click();
        driver.findElement(By.cssSelector("input.button-1.confirm-order-next-step-button")).click();

        // Confirm checkout success
        WebElement orderConfirmation = driver.findElement(By.cssSelector(".order-completed"));
        Assert.assertTrue(orderConfirmation.isDisplayed(), "Order confirmation not displayed!");

        // Navigate to My Account and check orders
        driver.findElement(By.linkText("My account")).click();
        driver.findElement(By.linkText("Orders")).click();
        List<WebElement> orders = driver.findElements(By.cssSelector(".order-number"));
        Assert.assertTrue(orders.size() > 0, "No orders found in My Account!");
    }
}
