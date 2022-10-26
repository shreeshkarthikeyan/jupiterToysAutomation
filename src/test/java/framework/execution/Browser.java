package framework.execution;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;

import framework.Report;
import framework.Settings;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Browser {

    private static ThreadLocal<WebDriver> drivers = new ThreadLocal<WebDriver>();

    public static WebDriver getInstance() {
        return drivers.get();
    }

    public static void setInstance(WebDriver driver) {
        drivers.set(driver);
    }

    /**
     * launches the browser based on the user provided settings
     * 
     * @return @WebDriver the driver object representing the browser
     */
    public static WebDriver launchBrowser(String url) {
        Capabilities capabilities = null;
        WebDriver driver = null;

        switch (Settings.getBrowser().toLowerCase()) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--ignore-ssl-errors=yes");
                chromeOptions.addArguments("--ignore-certificate-errors");
                if (Settings.isHeadless()) {
                    chromeOptions.addArguments("--headless");
                    chromeOptions.addArguments("window-size=1200x600");
                    chromeOptions.addArguments("w3c=false");
                }

                if (!Settings.getDevice().equalsIgnoreCase("desktop")) {
                    Map<String, String> mobileEmulation = new HashMap<>();

                    mobileEmulation.put("deviceName", Settings.getDevice());
                    chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
                }

                //if (Settings.getChrome_profile() != null) {
                //    chromeOptions.addArguments("--user-data-dir=" + Settings.getChrome_profile());
                //}
                capabilities = chromeOptions;

                // if (!Settings.isRemote_execution()) {
                //     WebDriverManager.chromedriver().setup();
                //     driver = new ChromeDriver((ChromeOptions) capabilities);
                // }
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver((ChromeOptions) capabilities);
                break;
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                capabilities = firefoxOptions;
                // if (!Settings.isRemote_execution()) {
                //     WebDriverManager.firefoxdriver().setup();
                //     driver = new FirefoxDriver((FirefoxOptions) capabilities);
                // }
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver((FirefoxOptions) capabilities);
                break;
            case "ie":
                InternetExplorerOptions ieOptions = new InternetExplorerOptions();
                ieOptions.introduceFlakinessByIgnoringSecurityDomains();
                // disabled for now while ie scripts stabilised
                // ieOptions.requireWindowFocus();
                // ieOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
                // ieOptions.disableNativeEvents();
                capabilities = ieOptions;
                // if (!Settings.isRemote_execution()) {
                //     WebDriverManager.iedriver().arch32().setup();
                //     driver = new InternetExplorerDriver((InternetExplorerOptions) capabilities);
                // }
                WebDriverManager.iedriver().arch32().setup();
                driver = new InternetExplorerDriver((InternetExplorerOptions) capabilities);
                break;
            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                capabilities = edgeOptions;
                // if (!Settings.isRemote_execution()) {
                //     WebDriverManager.edgedriver().setup();
                //     driver = new EdgeDriver((EdgeOptions) capabilities);
                // }
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver((EdgeOptions) capabilities);
                break;
            case "safari":
                OperaOptions operaOptions = new OperaOptions();
                capabilities = operaOptions;
                // if (!Settings.isRemote_execution()) {
                //     WebDriverManager.operadriver().setup();
                //     driver = new OperaDriver((OperaOptions) capabilities);
                // }
                WebDriverManager.operadriver().setup();
                driver = new OperaDriver((OperaOptions) capabilities);
                break;
            default:
                throw new RuntimeException("Unknown browser: " + Settings.getBrowser());
        }

        // if (Settings.isRemote_execution()) {
        //     try {
        //         driver = new RemoteWebDriver(new URL(Settings.getGrid_url()), capabilities);
        //     } catch (MalformedURLException e) {
        //         throw new RuntimeException("Unable to connect to grid", e);
        //     }
        // }
        driver.manage().timeouts().implicitlyWait(Settings.getTimeout_seconds(), TimeUnit.SECONDS);
        driver.manage().window().maximize();
        Report.logStep("Launching Browser to: " + url);
        driver.get(url);
        drivers.set(driver);
        System.out.println("Browser launched");
        return driver;
    }

    public static void quit() {
        drivers.get().quit();
        drivers.remove();
    }
}