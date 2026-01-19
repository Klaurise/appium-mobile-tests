package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class ProductDetailsPage extends BasePage {

    public ProductDetailsPage(AndroidDriver driver) {
        super(driver);
    }

    private static final String ADD_TO_CART_BUTTON = "ADD TO CART";

    @AndroidFindBy(accessibility = "test-Price")
    private WebElement productPrice;

    @AndroidFindBy(accessibility = "test-ADD TO CART")
    private WebElement addToCartButton;

    public String getProductPrice() {
        return productPrice.getText();
    }

    public void clickAddToCartButton() {
        scrollAndClick(ADD_TO_CART_BUTTON);
    }

    public String getCartItemCount() {
        try {
            WebElement cartBadge = driver.findElement(
                    AppiumBy.xpath("//android.view.ViewGroup[@content-desc='test-Cart']//android.widget.TextView")
            );
            return cartBadge.getText();
        } catch (Exception e) {
            return "";
        }
    }
}
