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

    LoginPage loginPage = new LoginPage(driver);
    ProductsPage productsPage = new ProductsPage(driver);
    ProductDetailsPage productDetailsPage = new ProductDetailsPage(driver);
    CartPage cartPage = new CartPage(driver);
    CheckoutInformationPage checkoutInformationPage = new CheckoutInformationPage(driver);
    CheckoutOverviewPage checkoutOverviewPage = new CheckoutOverviewPage(driver);
    CheckoutCompletedPage checkoutCompletedPage = new CheckoutCompletedPage(driver);
    SideMenu sideMenu = new SideMenu(driver);

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
    @Tag("Smoke")
    @DisplayName("Buy product, complete the order and logout")
    public void completeOrderTest() {

        final String AMOUNT = "1";

        Allure.step("Login to application", () -> {
            loginPage.login(
                    LoginData.STANDARD_USER.getUsername(),
                    LoginData.STANDARD_USER.getPassword());
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

    @Test
    @Tag("Regression")
    @DisplayName("Validate error messages for empty required fields")
    public void emptyRequiredFieldsTest() {

        CheckoutData firstName = CheckoutData.builder()
                .firstName("@^&AnS1")
                .build();
        CheckoutData lastName = CheckoutData.builder()
                .lastName("_____")
                .build();
        CheckoutData zipCode = CheckoutData.builder()
                .zipCode("ABCDEFGHIJKLMNOPRSTUWXYZ")
                .build();

        Allure.step("Login and proceed to Checkout Information Screen", () -> {
            loginPage.login(
                    LoginData.STANDARD_USER.getUsername(),
                    LoginData.STANDARD_USER.getPassword());
            productsPage.openProductDetailsView(PRODUCT_NAME);
            productDetailsPage.clickAddToCartButton();
            cartPage.openCart();
            cartPage.clickCheckoutButton();
            assertTrue(checkoutInformationPage
                    .isElementDisplayed(checkoutInformationPage.getCheckoutInformationHeader()));
        });

        Allure.step("Verify empty First Name field error", () -> {
            checkoutInformationPage.clickContinueButton();
            assertEquals(
                    "First Name is required",
                    checkoutInformationPage.getFirstNameErrorText().getText());
        });

        Allure.step("Verify empty Last Name field error", () -> {
            checkoutInformationPage.fillFirstName(firstName);
            checkoutInformationPage.clickContinueButton();
            assertEquals(
                    "Last Name is required",
                    checkoutInformationPage.getLastNameErrorText().getText());
        });

        Allure.step("Verify empty Zip/Postal Code field error", () -> {
            checkoutInformationPage.fillLastName(lastName);
            checkoutInformationPage.clickContinueButton();
            assertEquals(
                    "Postal Code is required",
                    checkoutInformationPage.getZipCodeErrorText().getText());
        });

        Allure.step("Checkout Information screen completed", () -> {
            checkoutInformationPage.fillZipCode(zipCode);
            checkoutInformationPage.clickContinueButton();
            assertTrue(checkoutOverviewPage.isHeaderVisible());
        });
    }
}
