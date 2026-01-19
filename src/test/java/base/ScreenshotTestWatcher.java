package base;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import utils.ConfigReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class ScreenshotTestWatcher implements TestWatcher {

    private final String APP_ID = ConfigReader.getAppPackage();

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        AndroidDriver driver = BaseTest.driver;

        if (driver == null) {
            System.out.println("Driver is null - cannot take screenshot");
            return;
        }

        String testName = context.getDisplayName();

        try {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            String filename = "screenshots/FAILED_" + testName + "_" + timestamp + ".png";

            Files.createDirectories(Paths.get("screenshots"));

            File screenshotFile = driver.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshotFile, new File(filename));

            try (FileInputStream fis = new FileInputStream(screenshotFile)) {
                Allure.addAttachment("FAILED - " + testName, "image/png", fis, ".png");
            }

            System.out.println("Screenshot saved: " + filename);
        } catch (IOException e) {
            System.out.println("Failed to take screenshot: " + e.getMessage());
        }
        resetApp(driver);
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        AndroidDriver driver = BaseTest.driver;
        if (driver != null) {
            resetApp(driver);
        }
    }

    private void resetApp(AndroidDriver driver) {
        System.out.println("===> Resetting app <===");
        driver.executeScript("mobile: clearApp", Map.of("appId", APP_ID));
        driver.executeScript("mobile: activateApp", Map.of("appId", APP_ID));
        System.out.println("===> App reset completed <===");
    }
}
