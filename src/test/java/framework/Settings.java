package framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Represents the Test Settings required for Test Execution
 */
public class Settings {
    private static Properties properties;

    private static void loadSettingsFile() {
        if(properties==null) {
            Properties systemProperties = System.getProperties();
            File fileLoc1 = new File("src/test/resources/Settings.properties");
            
            if (systemProperties.containsKey("property.file")) {
                properties = readFile(systemProperties.getProperty("property.file"));
            } else if (fileLoc1.exists()) {
                properties = readFile(fileLoc1.getAbsolutePath());
            }else {
                properties = new Properties();
            }
        }
    }

    private static String getSetting(String setting) {
        loadSettingsFile();
        String value = null;
        String envVar = setting.replaceAll("\\.", "_").toUpperCase();

        if(System.getenv(envVar)!=null) {
            value = System.getenv(envVar);
        }

        if (properties.containsKey(setting)) {
            value = properties.getProperty(setting);
        }
        return value;
    }

    private static Properties readFile(String fileName) {
        Properties fileProperties = new Properties();
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(fileName);
            fileProperties.load(inputStream);
            return fileProperties;
        } catch (Exception e) {
            throw new RuntimeException("Unable to read properties file: " + fileName, e);
        }
    }

    public static String getUrl() {
        String url = getSetting("url");
        if(url !=null) {
            return url;
        }else{
            return null;
        }        
    }

    public static int getTimeout_seconds() {
        String timeout = getSetting("timeout.seconds");
        if(timeout!=null) {
            return Integer.parseInt(timeout);
        }else{
            return 5;
        }
    }

    public static String getBrowser() {
        String browser = getSetting("browser");
        if(browser!=null) {
            return browser;
        }else {
            return "chrome";
        }
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(getSetting("headless"));
    }

    public static String getUsername() {
        return getSetting("username");
    }

    public static String getPassword() {
        return getSetting("password");
    }

    public static String getDevice() {
        String device = getSetting("device");
        if(device!=null) {
            return getSetting("device");
        }else {
            return "desktop";
        }
    }
}