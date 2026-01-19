package pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class ProductsPage extends BasePage {

    public ProductsPage(AndroidDriver driver) {
        super(driver);
    }

    @AndroidFindBy(accessibility = "test-Cart")
    private WebElement cartButton;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"PRODUCTS\")")
    private WebElement productsTitle;

    @AndroidFindBy(accessibility = "test-Modal Selector Button")
    private WebElement filterOption;

    public boolean isProductsPageVisible() {
        return isElementDisplayed(cartButton) && isElementDisplayed(productsTitle) && isElementDisplayed(filterOption);
    }

    public void openProductDetailsView(String productName) {
        scrollAndClick(productName);
    }
}
