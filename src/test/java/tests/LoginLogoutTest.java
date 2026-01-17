package tests;

import base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import data.LoginData;
import pages.LoginPage;
import pages.ProductsPage;
import pages.SideMenu;

import static org.junit.jupiter.api.Assertions.*;

public class LoginLogoutTest extends BaseTest {

    @Test
    @Tag("Smoke")
    @DisplayName("Successful login and logout")
    public void testSuccessfulLoginAndLogout() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);
        SideMenu sideMenu = new SideMenu(driver);

        loginPage.login(LoginData.STANDARD_USER.getUsername(), LoginData.STANDARD_USER.getPassword());
        assertTrue(productsPage.isProductsPageVisible());

        sideMenu.logout();
        assertTrue(loginPage.isLoginPageVisible());
    }

    @Test
    @Tag("Regression")
    @DisplayName("Locked out user")
    public void testLockedOutLogin() {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(LoginData.LOCKED_OUT_USER.getUsername(), LoginData.LOCKED_OUT_USER.getPassword());
        assertTrue((loginPage.isElementDisplayed(loginPage.getErrorBanner())));
        assertEquals("Sorry, this user has been locked out.", loginPage.getErrorText());
    }

    @Test
    @Tag("Smoke")
    @DisplayName("Invalid login")
    public void testInvalidCredentials() {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(LoginData.INVALID_USER.getUsername(), LoginData.INVALID_USER.getPassword());
        assertTrue(loginPage.isElementDisplayed(loginPage.getErrorBanner()));
        assertEquals("Username and password do not match any user in this service.", loginPage.getErrorText());
    }
}
