package drivers;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import utils.ConfigReader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;

public class DriverManager {

    private static final ThreadLocal<AndroidDriver> driver = new ThreadLocal<>();

    public static AndroidDriver getDriver() {
        return driver.get();
    }

    public static void setDriver(AndroidDriver driverInstance) {
        driver.set(driverInstance);
    }

    public static void startEmulator(String emulatorName) {
        System.out.println("===> Starting emulator: " + emulatorName + " <===");
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    System.getenv("ANDROID_HOME") + "/emulator/emulator",
                    "-avd", emulatorName,
                    "-no-snapshot-load"
            );
            pb.start();

            waitForEmulatorReady();
            System.out.println("===> Emulator started <===");
        } catch (Exception e) {
            throw new RuntimeException("Failed to start emulator: " + emulatorName, e);
        }
    }

    private static void waitForEmulatorReady() throws IOException, InterruptedException {
        System.out.println("Waiting for emulator to be ready...");
        int maxAttempts = 60;

        for (int i = 0; i < maxAttempts; i++) {
            Process process = new ProcessBuilder(
                    System.getenv("ANDROID_HOME") + "/platform-tools/adb",
                    "shell", "getprop", "sys.boot_completed"
            ).start();

            String output = new String(process.getInputStream().readAllBytes()).trim();
            process.waitFor();

            if ("1".equals(output)) {
                System.out.println("Emulator is ready!");
                return;
            }

            Thread.sleep(1000);
        }

        throw new RuntimeException("Emulator did not start within timeout");
    }

    public static void initializeDriver() {
        System.out.println("===> Initializing Android driver <===");

        try {
            URL serverUrl = new URI(ConfigReader.getAppiumServerUrl()).toURL();
            AndroidDriver androidDriver = createAndroidDriver(serverUrl);

            androidDriver.
                    manage().
                    timeouts().
                    implicitlyWait(Duration.ofSeconds(ConfigReader.getImplicitWait()));

            setDriver(androidDriver);
            System.out.println("===> Driver initialized successfully <===");

        } catch (MalformedURLException e) {
            System.err.println("Invalid Appium server URL: " + e.getMessage());
            throw new RuntimeException("Failed to initialize driver", e);
        } catch (URISyntaxException e) {
            System.err.println("Invalid URI syntax: " + e.getMessage());
            throw new RuntimeException("Failed to initialize driver", e);
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
            System.out.println("Quitting driver...");
            driver.get().quit();
            driver.remove();
        }
    }
}
