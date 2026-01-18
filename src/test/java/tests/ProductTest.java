package tests;

import base.BaseTest;
import data.CheckoutData;
import data.LoginData;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.*;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest extends BaseTest {

    private final String AMOUNT = "1";
    private final String PRODUCT_NAME = "Sauce Labs Onesie";

    @Test
    @Tag("Regression")
    @DisplayName("Buy product, complete the order and logout")
    public void completeOrder() {
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
        });

        Allure.step("Fill checkout information", () -> {
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

        Allure.step("Logout from application", () -> {
            checkoutCompletedPage.clickBackHomeButton();
            sideMenu.logout();
            assertTrue(loginPage.isLoginPageVisible());
        });
    }
}