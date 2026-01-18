package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class CheckoutCompletedPage extends BasePage {

    public CheckoutCompletedPage(AppiumDriver driver) {
        super(driver);
    }

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"THANK YOU FOR YOU ORDER\")")
    private WebElement thankYouTitle;

    @AndroidFindBy(accessibility = "test-BACK HOME")
    private WebElement backHomeButton;

    public boolean isCheckoutCompleted() {
        return isElementDisplayed(thankYouTitle) && isElementDisplayed(backHomeButton);
    }

    public void clickBackHomeButton() {
        click(backHomeButton);
        waitForInvisibility(backHomeButton);
    }
}
