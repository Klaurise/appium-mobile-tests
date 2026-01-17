package tests;

import base.BaseTest;
import data.LoginData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.*;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest extends BaseTest {

    private final String PRODUCT_NAME = "Sauce Labs Onesie";
    private final String AMOUNT = "1";
    private final String FIRST_NAME = "Jane";
    private final String LAST_NAME = "Doe";
    private final String ZIP_CODE = "02-310 Alabama";

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

        loginPage.login(LoginData.STANDARD_USER.getUsername(), LoginData.STANDARD_USER.getPassword());
        assertTrue(productsPage.isProductsPageVisible());

        productsPage.openProductDetailsView(PRODUCT_NAME);
        assertTrue(productDetailsPage.isProductNameDisplayed(PRODUCT_NAME));
        String price = productDetailsPage.getProductPrice();

        productDetailsPage.clickAddToCartButton();
        assertEquals(AMOUNT, productDetailsPage.getCartItemCount());

        cartPage.openCart();
        assertTrue(cartPage.isProductNameDisplayed(PRODUCT_NAME));
        assertEquals(price, cartPage.getItemPrice());
        assertEquals(AMOUNT, cartPage.getItemAmount());
        assertTrue(cartPage.isElementDisplayed(cartPage.getRemoveButton()));
        assertTrue(cartPage.isElementDisplayed(cartPage.getContinueShoppingButton()));

        cartPage.clickCheckoutButton();
        checkoutInformationPage.fillCheckoutInformation(FIRST_NAME, LAST_NAME, ZIP_CODE);
        checkoutInformationPage.clickContinueButton();
        assertEquals(AMOUNT, checkoutOverviewPage.getItemAmount());
        assertTrue(checkoutOverviewPage.isItemTotalCorrect(price));

        checkoutOverviewPage.clickFinishButton();
        assertTrue(checkoutCompletedPage.isCheckoutCompleted());

        checkoutCompletedPage.clickBackHomeButton();
        sideMenu.logout();
        assertTrue(loginPage.isLoginPageVisible());
    }
}
