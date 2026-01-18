package tests;

import base.BaseTest;
import data.CheckoutData;
import data.LoginData;
import io.qameta.allure.Allure;
import io.qameta.allure.Issue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.*;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest extends BaseTest {

    private final String PRODUCT_NAME = "Sauce Labs Onesie";

    // BUG
    // Empty Cart Checkout Vulnerability
    //
    // Description:
    // The application allows users to proceed to checkout with
    // an empty shopping cart.
    //
    // Current Behavior:
    // - user can click "CHECKOUT" button with zero items in cart
    // - no validation or error message is displayed
    //
    // Expected Behavior:
    // - checkout button should be disabled when cart is empty
    // - or a warning message should inform the user to add items first
    //
    // This is an application bug, not a test issue.

    @Test
    @Issue("JIRA-48")
    @Tag("Regression")
    @DisplayName("Buy product, complete the order and logout")
    public void completeOrder() {

        final String AMOUNT = "1";

        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);
        ProductDetailsPage productDetailsPage = new ProductDetailsPage(driver);
        CartPage cartPage = new CartPage(driver);
        CheckoutInformationPage checkoutInformationPage = new CheckoutInformationPage(driver);
        CheckoutOverviewPage checkoutOverviewPage = new CheckoutOverviewPage(driver);
        CheckoutCompletedPage checkoutCompletedPage = new CheckoutCompletedPage(driver);
        SideMenu sideMenu = new SideMenu(driver);

        Allure.step("Login to application", () -> {
            loginPage.login(LoginData.STANDARD_USER.getUsername(), LoginData.STANDARD_USER.getPassword());
            assertTrue(productsPage.isProductsPageVisible());
        });

        Allure.step("Select product from catalog", () -> {
            productsPage.openProductDetailsView(PRODUCT_NAME);
            assertTrue(productDetailsPage.isProductNameDisplayed(PRODUCT_NAME));
        });

        String price = productDetailsPage.getProductPrice();

        Allure.step("Add product to cart", () -> {
            productDetailsPage.clickAddToCartButton();
            assertEquals(AMOUNT, productDetailsPage.getCartItemCount());
        });

        Allure.step("Open cart and verify product", () -> {
            cartPage.openCart();
            assertTrue(cartPage.isProductNameDisplayed(PRODUCT_NAME));
            assertEquals(price, cartPage.getItemPrice());
            assertEquals(AMOUNT, cartPage.getItemAmount());
            assertTrue(cartPage.isElementDisplayed(cartPage.getRemoveButton()));
            assertTrue(cartPage.isElementDisplayed(cartPage.getContinueShoppingButton()));
        });

        Allure.step("Proceed to checkout", () -> {
            cartPage.clickCheckoutButton();
            checkoutInformationPage.fillCheckoutInformation(CheckoutData.STANDARD_ADDRESS);
            checkoutInformationPage.clickContinueButton();
        });

        Allure.step("Review order summary", () -> {
            assertEquals(AMOUNT, checkoutOverviewPage.getItemAmount());
            assertTrue(checkoutOverviewPage.isItemTotalCorrect(price));
        });

        Allure.step("Finish transaction", () -> {
            checkoutOverviewPage.clickFinishButton();
            assertTrue(checkoutCompletedPage.isCheckoutCompleted());
        });

        Allure.step("Check cart after transaction", () -> {
            checkoutCompletedPage.clickBackHomeButton();
            assertTrue(productsPage.isProductsPageVisible());
            cartPage.openCart();
            assertFalse(cartPage.isElementDisplayed(cartPage.getCheckoutButton()));
        });

        Allure.step("Logout from application", () -> {
            sideMenu.logout();
            assertTrue(loginPage.isLoginPageVisible());
        });
    }
}
