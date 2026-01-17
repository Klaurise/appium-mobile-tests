package base;

import drivers.DriverManager;
import io.appium.java_client.AppiumDriver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import java.util.Map;

public class BaseTest {

    protected static AppiumDriver driver;
    private final String APP_ID = "com.swaglabsmobileapp";

    @BeforeAll
    public static void setUp() {
        System.out.println("===> Starting test setup <===");
        DriverManager.initializeDriver();
        driver = DriverManager.getDriver();
        System.out.println("===> Test setup completed <===");
    }

    @AfterEach
    public void resetApp() {
        System.out.println("===> Resetting app <===");
        driver.executeScript("mobile: clearApp", Map.of("appId", APP_ID));
        driver.executeScript("mobile: activateApp", Map.of("appId", APP_ID));
        System.out.println("===> App reset completed <===");
    }

    @AfterAll
    public static void tearDown() {
        System.out.println("===> Starting test teardown <===");
        DriverManager.quitDriver();
        System.out.println("===> Test teardown completed <===");
    }
}