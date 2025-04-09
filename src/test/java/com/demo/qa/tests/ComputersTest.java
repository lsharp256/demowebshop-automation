package com.demo.qa.tests;

import com.demo.qa.pages.CheckoutPage;
import com.demo.qa.utils.ConfigReader;
import com.demo.qa.utils.LoginHelper;
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
import java.util.stream.Collectors;

public class ComputersTest extends BaseTest {

    @Test
    public void testDesktopAddToCartAndValidatePrices() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Navigate to desktops
        driver.findElement(By.linkText("Computers")).click();
        driver.findElement(By.linkText("Desktops")).click();

        // Sort desktops
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

        // Get product info
        List<WebElement> productLinks = driver.findElements(By.cssSelector(".product-title a"));
        List<String[]> products = productLinks.stream()
                .map(el -> new String[]{el.getText(), el.getAttribute("href")})
                .collect(Collectors.toList());

        System.out.println("🖥️ Found " + products.size() + " desktop product(s).");

        for (String[] product : products) {
            String productName = product[0];
            String productUrl = product[1];

            System.out.println("➡️ Visiting product: " + productName);
            driver.get(productUrl);

            try {
                WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[value='Add to cart']")));

                // Handle configs
                if (productName.equalsIgnoreCase("Simple Computer")) {
                    WebElement slowOption = driver.findElement(By.id("product_attribute_75_5_31_96"));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", slowOption);
                    System.out.println("⚙️ Selected 'Slow' processor for Simple Computer");
                }

                if (productName.equalsIgnoreCase("Build your own computer")) {
                    WebElement hddOption = driver.findElement(By.id("product_attribute_16_3_6_18")); // 320 GB
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", hddOption);
                    System.out.println("💾 Selected '320 GB' HDD for Build your own computer");
                }

                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCartButton);
                System.out.println("🛒 Added to cart via product page");

                try {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("bar-notification")));
                    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("bar-notification")));
                } catch (Exception e) {
                    System.out.println("⚠️ No bar notification for " + productName);
                }

            } catch (Exception e) {
                System.out.println("❌ Could not add " + productName + ": " + e.getMessage());
            }
        }

        // 🛒 Validate cart
        driver.findElement(By.linkText("Shopping cart")).click();
        double totalPrice = 0.0;

        List<WebElement> prices = driver.findElements(By.cssSelector(".product-unit-price"));
        for (WebElement price : prices) {
            totalPrice += Double.parseDouble(price.getText().trim());
        }

        WebElement cartTotalElement = driver.findElement(By.cssSelector(".cart-total-right strong"));
        double cartTotal = Double.parseDouble(cartTotalElement.getText().trim());

        Assert.assertEquals(cartTotal, totalPrice, "Cart total mismatch!");

        // 🗑️ Remove an item
        driver.findElement(By.cssSelector(".remove-from-cart input")).click();
        driver.findElement(By.name("updatecart")).click();

        // 🔁 Revalidate total
        totalPrice = 0.0;
        prices = driver.findElements(By.cssSelector(".product-unit-price"));
        for (WebElement price : prices) {
            totalPrice += Double.parseDouble(price.getText().trim());
        }

        cartTotalElement = driver.findElement(By.cssSelector(".cart-total-right strong"));
        cartTotal = Double.parseDouble(cartTotalElement.getText().trim());

        Assert.assertEquals(cartTotal, totalPrice, "Cart total mismatch after item removal!");

        // ✅ Accept terms of service and go to checkout
        WebElement termsCheckbox = driver.findElement(By.id("termsofservice"));
        if (!termsCheckbox.isSelected()) {
            termsCheckbox.click();
        }

        driver.findElement(By.id("checkout")).click();

        // 🔍 Assert URL
        wait.until(ExpectedConditions.urlContains("checkoutasguest"));
        Assert.assertTrue(driver.getCurrentUrl().contains("checkoutasguest"), "Did not navigate to onepagecheckout page!");

        LoginHelper.login(driver, ConfigReader.get("email"), ConfigReader.get("password"));

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.completeCheckout();
    }
}
