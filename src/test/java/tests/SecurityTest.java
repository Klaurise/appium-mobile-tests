package tests;

import base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import data.LoginData;
import pages.LoginPage;
import pages.ProductsPage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SecurityTest extends BaseTest {

    // Brute Force Protection test demonstrates a security vulnerability
    // in the application.
    // After multiple failed login attempts, the account should be locked
    // or a CAPTCHA should be displayed to prevent brute force attacks.
    // Current behavior: The application allows unlimited login attempts,
    // which is a security risk.
    // Recommendation: Implement account lockout after 3 failed attempts
    // or introduce CAPTCHA verification.

    @Test
    @Tag("Regression")
    @DisplayName("Brute Force Protection")
    public void testBruteForceProtection() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);

        for (int i = 0; i < 5; i++) {
            loginPage.login(LoginData.STANDARD_USER.getUsername(), loginPage.generateRandomPassword());
            assertTrue((loginPage.isElementDisplayed(loginPage.getErrorBanner())));
        }
        loginPage.login(LoginData.STANDARD_USER.getUsername(), LoginData.STANDARD_USER.getPassword());
        assertFalse(productsPage.isProductsPageVisible());
    }
}
