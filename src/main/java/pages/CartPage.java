package pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import lombok.Getter;
import org.openqa.selenium.WebElement;

@Getter
public class CartPage extends BasePage {

    public CartPage(AndroidDriver driver) {
        super(driver);
    }

    @AndroidFindBy(accessibility = "test-Amount")
    private WebElement itemAmount;

    @AndroidFindBy(accessibility = "test-Price")
    private WebElement itemPrice;

    @AndroidFindBy(accessibility = "test-Cart")
    private WebElement cartButton;

    @AndroidFindBy(accessibility = "test-REMOVE")
    private WebElement removeButton;

    @AndroidFindBy(accessibility = "test-CONTINUE SHOPPING")
    private WebElement continueShoppingButton;

    @AndroidFindBy(accessibility = "test-CHECKOUT")
    private WebElement checkoutButton;

    public String getItemPrice() {
        return getChildTextViewValue(itemPrice);
    }

    public String getItemAmount() {
        return getChildTextViewValue(itemAmount);
    }

    public void openCart() {
        click(cartButton);
    }

    public void clickCheckoutButton() {
        click(checkoutButton);
    }
}
