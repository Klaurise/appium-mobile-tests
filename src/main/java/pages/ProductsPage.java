package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class ProductsPage extends BasePage {

    public ProductsPage(AppiumDriver driver) {
        super(driver);
    }

    @AndroidFindBy(accessibility = "test-Cart")
    private WebElement cardButton;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"PRODUCTS\")")
    private WebElement productsTitle;

    @AndroidFindBy(accessibility = "test-Modal Selector Button")
    private WebElement filterOption;

    public boolean isProductsPageVisible() {
        return isElementDisplayed(cardButton) && isElementDisplayed(productsTitle) && isElementDisplayed(filterOption);
    }

    public void openProductDetailsView(String productName) {
        scrollAndClick(productName);
    }
}
