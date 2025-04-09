package com.demo.qa.tests;

import com.demo.qa.pages.*;
import com.demo.qa.utils.ConfigReader;
import com.demo.qa.utils.LoginHelper;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckoutTest extends BaseTest {

    @Test
    public void completeCheckoutFlow() {
        String email = ConfigReader.get("email");
        String password = ConfigReader.get("password");

        LoginHelper.login(driver, email, password);

        HomePage homePage = new HomePage(driver);
        homePage.navigateToDesktops();

        DesktopsPage desktopsPage = new DesktopsPage(driver);
        desktopsPage.sortAllOptions();
        desktopsPage.addAllDesktopsToCart();

        CartPage cartPage = new CartPage(driver);
        cartPage.openCart();

        double expectedTotal = cartPage.getTotalPrice();
        double displayedTotal = cartPage.getCartTotalFromPage();
        Assert.assertEquals(displayedTotal, expectedTotal, "Cart total mismatch!");

        cartPage.removeFirstItem();

        expectedTotal = cartPage.getTotalPrice();
        displayedTotal = cartPage.getCartTotalFromPage();
        Assert.assertEquals(displayedTotal, expectedTotal, "Updated cart total mismatch!");

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.completeCheckout();
        Assert.assertTrue(checkoutPage.isOrderConfirmed(), "Order was not confirmed");

        MyAccountPage myAccountPage = new MyAccountPage(driver);
        Assert.assertTrue(myAccountPage.isOrderVisible(), "Order not found under My Account");
    }
}
