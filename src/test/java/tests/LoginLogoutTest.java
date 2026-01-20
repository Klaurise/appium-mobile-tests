package tests;

import base.BaseTest;
import io.qameta.allure.Allure;
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

        Allure.step("Login with valid credentials", () -> {
            loginPage.login(
                    LoginData.STANDARD_USER.getUsername(),
                    LoginData.STANDARD_USER.getPassword());
            assertTrue(productsPage.isProductsPageVisible());
        });

        Allure.step("Logout from application", () -> {
            sideMenu.logout();
            assertTrue(loginPage.isLoginPageVisible());
        });
    }

    @Test
    @Tag("Regression")
    @DisplayName("Locked out user")
    public void testLockedOutLogin() {
        LoginPage loginPage = new LoginPage(driver);

        Allure.step("Login with locked out user credentials", () -> {
            loginPage.login(
                    LoginData.LOCKED_OUT_USER.getUsername(),
                    LoginData.LOCKED_OUT_USER.getPassword());
        });

        Allure.step("Verify error message", () -> {
            assertTrue(loginPage.isElementDisplayed(loginPage.getErrorBanner()));
            assertEquals(
                    "Sorry, this user has been locked out.",
                    loginPage.getErrorText());
        });
    }

    @Test
    @Tag("Smoke")
    @DisplayName("Invalid login")
    public void testInvalidCredentials() {
        LoginPage loginPage = new LoginPage(driver);

        Allure.step("Login with invalid credentials", () -> {
            loginPage.login(
                    LoginData.INVALID_USER.getUsername(),
                    LoginData.INVALID_USER.getPassword());
        });

        Allure.step("Verify error message", () -> {
            assertTrue(loginPage.isElementDisplayed(loginPage.getErrorBanner()));
            assertEquals(
                    "Username and password do not match any user in this service.",
                    loginPage.getErrorText());
        });
    }
}