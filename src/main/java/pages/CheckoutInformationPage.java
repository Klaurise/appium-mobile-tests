package pages;

import data.CheckoutData;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import lombok.Getter;
import org.openqa.selenium.WebElement;

@Getter
public class CheckoutInformationPage extends BasePage {

    public CheckoutInformationPage(AndroidDriver driver) {
        super(driver);
    }

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"CHECKOUT: INFORMATION\")")
    private WebElement checkoutInformationHeader;

    @AndroidFindBy(accessibility = "test-First Name")
    private WebElement firstNameInput;

    @AndroidFindBy(accessibility = "test-Last Name")
    private WebElement lastNameInput;

    @AndroidFindBy(accessibility = "test-Zip/Postal Code")
    private WebElement zipCodeInput;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"First Name is required\")")
    private WebElement firstNameErrorText;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Last Name is required\")")
    private WebElement lastNameErrorText;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Postal Code is required\")")
    private WebElement zipCodeErrorText;

    @AndroidFindBy(accessibility = "test-CONTINUE")
    private WebElement continueButton;

    public void fillCheckoutInformation(CheckoutData checkoutData) {
        fillFirstName(checkoutData);
        fillLastName(checkoutData);
        fillZipCode(checkoutData);
    }

    public void fillFirstName(CheckoutData checkoutData) {
        sendKeys(firstNameInput, checkoutData.getFirstName());
    }

    public void fillLastName(CheckoutData checkoutData) {
        sendKeys(lastNameInput, checkoutData.getLastName());
    }

    public void fillZipCode(CheckoutData checkoutData) {
        sendKeys(zipCodeInput, checkoutData.getZipCode());
    }

    public void clickContinueButton() {
        click(continueButton);
    }
}
