package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties;
    private static final String CONFIG_PATH = "src/test/resources/config.properties";

    static {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_PATH)) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config file: " + CONFIG_PATH, e);
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property '" + key + "' not found in config file");
        }
        return value;
    }

    public static String getDeviceName() {
        return System.getenv("DEVICE_NAME") != null
                ? System.getenv("DEVICE_NAME")
                : properties.getProperty("device.name");
    }

    public static String getPlatformVersion() {
        return System.getenv("PLATFORM_VERSION") != null
                ? System.getenv("PLATFORM_VERSION")
                : properties.getProperty("platform.version");
    }

    public static String getAppiumServerUrl() {
        return getProperty("appium.server.url");
    }

    public static String getAppPath() {
        return getProperty("app.path");
    }

    public static String getAppPackage() {
        return getProperty("app.package");
    }

    public static String getAppActivity() {
        return getProperty("app.activity");
    }

    public static int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait"));
    }

    public static int getIExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait"));
    }
}
