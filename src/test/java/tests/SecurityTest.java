package tests;

import base.BaseTest;
import io.qameta.allure.Allure;
import io.qameta.allure.Issue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import data.LoginData;
import pages.LoginPage;
import pages.ProductsPage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SecurityTest extends BaseTest {

    // BUG
    // Brute Force Protection test demonstrates a security vulnerability
    // in the application.
    //
    // Current behavior:
    // - the application allows unlimited login attempts, which is
    // a security risk
    //
    // Recommendation:
    // - implement account lockout after 3 failed attempts or introduce
    // CAPTCHA verification
    //
    // This is an application bug, not a test issue.

    @Test
    @Issue("JIRA-251")
    @Tag("Regression")
    @DisplayName("Brute Force Protection")
    public void testBruteForceProtection() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);

        Allure.step("Perform multiple failed login attempts", () -> {
            for (int i = 0; i < 5; i++) {
                loginPage.login(LoginData.STANDARD_USER.getUsername(), loginPage.generateRandomPassword());
                assertTrue(loginPage.isElementDisplayed(loginPage.getErrorBanner()));
            }
        });

        Allure.step("Verify account is locked after brute force attempts", () -> {
            loginPage.login(LoginData.STANDARD_USER.getUsername(), LoginData.STANDARD_USER.getPassword());
            assertFalse(productsPage.isProductsPageVisible());
        });
    }
}