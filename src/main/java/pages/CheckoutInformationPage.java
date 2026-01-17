package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class CheckoutInformationPage extends BasePage{

    public CheckoutInformationPage(AppiumDriver driver) {
        super(driver);
    }

    @AndroidFindBy(accessibility = "test-First Name")
    private WebElement firstNameInput;

    @AndroidFindBy(accessibility = "test-Last Name")
    private WebElement lastNameInput;

    @AndroidFindBy(accessibility = "test-Zip/Postal Code")
    private WebElement zipCodeInput;

    @AndroidFindBy(accessibility = "test-CONTINUE")
    private WebElement continueButton;

    public void fillCheckoutInformation(String firstName, String lastName, String zipNumber) {
        sendKeys(firstNameInput, firstName);
        sendKeys(lastNameInput, lastName);
        sendKeys(zipCodeInput, zipNumber);
    }

    public void clickContinueButton() {
        click(continueButton);
    }
}
