package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import lombok.Getter;
import org.openqa.selenium.WebElement;

import java.util.concurrent.ThreadLocalRandom;

@Getter
public class LoginPage extends BasePage {

    public LoginPage(AndroidDriver driver) {
        super(driver);
    }

    @AndroidFindBy(accessibility = "test-Username")
    private WebElement usernameField;

    @AndroidFindBy(accessibility = "test-Password")
    private WebElement passwordField;

    @AndroidFindBy(accessibility = "test-LOGIN")
    private WebElement loginButton;

    @AndroidFindBy(accessibility = "test-Error message")
    private WebElement errorBanner;

    public void enterUsername(String username) {
        sendKeys(usernameField, username);
    }

    public void enterPassword(String password) {
        sendKeys(passwordField, password);
    }

    public void clickLoginButton() {
        click(loginButton);
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public boolean isLoginPageVisible() {
        return isElementDisplayed(usernameField) && isElementDisplayed(passwordField) && isElementDisplayed(loginButton);
    }

    public String getErrorText() {
        WebElement errorText = errorBanner.findElement(
                AppiumBy.xpath(".//android.widget.TextView")
        );
        return errorText.getText();
    }

    public String generateRandomPassword() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(100, 10000));
    }
}
