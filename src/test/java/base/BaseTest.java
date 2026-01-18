package base;

import drivers.DriverManager;
import io.appium.java_client.AppiumDriver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ScreenshotTestWatcher.class)
public class BaseTest {

    protected static AppiumDriver driver;

    @BeforeAll
    public static void setUp() {
        System.out.println("===> Starting test setup <===");
        DriverManager.initializeDriver();
        driver = DriverManager.getDriver();
        System.out.println("===> Test setup completed <===");
    }

    @AfterAll
    public static void tearDown() {
        System.out.println("===> Starting test teardown <===");
        DriverManager.quitDriver();
        System.out.println("===> Test teardown completed <===");
    }
}