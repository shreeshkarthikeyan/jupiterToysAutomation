package framework;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

/**
 * Provides the reporting capability of the automation framework
 */
public class Report {
    private static ThreadLocal<ExtentTest> testReports = new ThreadLocal<ExtentTest>();

    private static ExtentReports extentReports;

    private static List<ExtentTest> suites = new ArrayList<ExtentTest>();

    /**
     * Initializes the reporting to a html report style with location in the reports folder
     */
    public static void initializeReporting() {
        extentReports = new ExtentReports();
        ExtentSparkReporter reporter = new ExtentSparkReporter("reports/Report.html");
        extentReports.attachReporter(reporter);
        extentReports.setAnalysisStrategy(AnalysisStrategy.SUITE);
        // extentReports.setSystemInfo("URL", Settings.getUrlSalesforceCaseWorker());
        // extentReports.setSystemInfo("Browser", Settings.getBrowser());
        // extentReports.setSystemInfo("Device", Settings.getDevice());
    }

    public static synchronized void createSuite(String suiteName) {
        Optional<ExtentTest> suite = suites.stream().filter(s -> s.getModel().getName().equals(suiteName)).findAny();
        if(!suite.isPresent())
        {
            suites.add(extentReports.createTest(suiteName));
            System.out.println("suite created");
        }else {
            System.out.println("suite already exists");
        }
    }
    /**
     * Creates a new test case entry in the reports
     * @param testName the test name
     */
    public static synchronized void createTest(String suiteName, String testName) {
        Optional<ExtentTest> suite = suites.stream().filter(s -> s.getModel().getName().equals(suiteName)).findAny();
        
        System.out.println("Creating Test");
        testReports.set(suite.get().createNode(testName));
        System.out.println("Test Created");
    }

    /**
     * retrieves the report for the currently execution test case
     */
    public static ExtentTest getTestReport() {
        return testReports.get();
    }

    /**
     * publishes the report
     */
    public static void publishReports() {
        extentReports.flush();
    }

    /**
     * logs a step in the current test case
     * @param step the step information to log to report
     */
    public static void logStep(String step) {
        getTestReport().info(step);
        Reporter.log(step, true);
    }

    /**
     * logs a step in the current test case
     * @param step the step information to log to report
     */
    public static void logDebugInfo(String debugInfo) {
        // if(Settings.isDebugMode())
        // {
        //     getTestReport().debug(debugInfo);
        // }
        // Reporter.log(debugInfo, true);
    }

    /**
     * logs a pass status for a step in the current test case
     * @param step the step information to log to report
     */
    public static void logPass(String step) {
        getTestReport().pass(step);
        Reporter.log(step, true);
    }

    /**
     * logs a fail status for a step in the current test case
     * @param failureMessage the step information to log to report
     */
    public static void logFail(String failureMessage) {
        getTestReport().fail(failureMessage);
        Reporter.log("ERROR: " + failureMessage, true);
    }

    /**
     * logs an exception fail in the current test case
     * @param error the error information to log to report
     */
    public static void logException(Throwable error) {
        error.printStackTrace();
        getTestReport().fail(error);
        
    }

    /**
     * captures a screenshot of the current open page
     * stores the screenshot in the report and a local file
     * @param driver the driver object to take screenshot from
     */
    public static void captureScreenshot(WebDriver driver) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String base64screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);

        getTestReport().addScreenCaptureFromBase64String(base64screenshot, "Screenshot");
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
            String filename = "failure_screenshots/" + dateFormatter.format(calendar.getTime()) + ".png";
            FileUtils.copyFile(srcFile, new File(filename));
            logStep("Screenshot stored at: " + filename);
        }catch(Exception e) {
            logFail("Unable to take screenshot");
            logException(e);
        }

    }
}
