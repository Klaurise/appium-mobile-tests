package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class SideMenu extends BasePage{

    public SideMenu(AppiumDriver driver) {
        super(driver);
    }

    @AndroidFindBy(accessibility = "test-Menu")
    private WebElement sideMenuButton;

    @AndroidFindBy(accessibility = "test-LOGOUT")
    private WebElement logout;

    public void logout(){
        click(sideMenuButton);
        click(logout);
    }
}
