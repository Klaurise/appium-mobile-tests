package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class ProductDetailsPage extends BasePage {

    private static final String ADD_TO_CART_BUTTON = "ADD TO CART";

    public ProductDetailsPage(AppiumDriver driver) {
        super(driver);
    }

    @AndroidFindBy(accessibility = "test-Price")
    private WebElement productPrice;

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
