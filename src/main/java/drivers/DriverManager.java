package drivers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import utils.ConfigReader;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;

public class DriverManager {

    private static final ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();

    public static AppiumDriver getDriver() {
        return driver.get();
    }

    public static void setDriver(AppiumDriver driverInstance) {
        driver.set(driverInstance);
    }

    public static void initializeDriver() {
        String platform = ConfigReader.getPlatform().toLowerCase();
        System.out.println("Initializing driver for platform: " + platform);

        try {
            AppiumDriver appiumDriver;
            URL serverUrl = new URI(ConfigReader.getAppiumServerUrl()).toURL();
            if (platform.equals("android")) {
                appiumDriver = createAndroidDriver(serverUrl);
            } else {
                throw new IllegalArgumentException("Invalid platform: " + platform);
            }

            appiumDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getImplicitWait()));

            setDriver(appiumDriver);
            System.out.println("Driver initialized successfully");

        } catch (MalformedURLException e) {
            System.err.println("Invalid Appium server URL: " + e.getMessage());
            throw new RuntimeException("Failed to initialize driver", e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static AndroidDriver createAndroidDriver(URL serverUrl) {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName(ConfigReader.getDeviceName());
        options.setPlatformVersion(ConfigReader.getPlatformVersion());
        options.setApp(System.getProperty("user.dir") + "/" + ConfigReader.getAppPath());
        options.setAppPackage(ConfigReader.getAppPackage());
        options.setAppActivity(ConfigReader.getAppActivity());
        options.setAutomationName("UiAutomator2");
        options.setNoReset(false);

        return new AndroidDriver(serverUrl, options);
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            System.out.println("Quitting driver");
            driver.get().quit();
            driver.remove();
        }
    }
}
