package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import lombok.Getter;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class CheckoutOverviewPage extends BasePage {

    private static final String FINISH_BUTTON_DESCRIPTION = "FINISH";

    public CheckoutOverviewPage(AppiumDriver driver) {
        super(driver);
    }

    @Getter
    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"CHECKOUT: OVERVIEW\")")
    private WebElement checkoutOverviewHeader;

    @AndroidFindBy(accessibility = "test-Amount")
    private WebElement itemAmount;

    public boolean isHeaderVisible(){
        return isElementDisplayed(checkoutOverviewHeader);
    }

    public void clickFinishButton() {
        scrollAndClick(FINISH_BUTTON_DESCRIPTION);
    }

    public String getItemAmount() {
        return getValue(itemAmount);
    }

    public boolean isItemTotalCorrect(String expectedPrice) {
        String itemTotalText = "Item total: " + expectedPrice;
        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"" + itemTotalText + "\")"));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
