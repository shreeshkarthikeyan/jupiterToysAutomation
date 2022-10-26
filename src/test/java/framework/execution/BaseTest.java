package framework.execution;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import framework.Report;
import framework.Settings;


/**
 * Base Test performs the setup and teardown for all test cases created for Salesforce
 */
@Listeners({CustomMethodListener.class, CustomSuiteListener.class})
public class BaseTest extends GlobalBaseTest {

    /**
     * performs a test setup before each test is executed
     * Initializes the driver required for each test
     * Creates a test entry in the reports
     * Initializes the soft asserts
     * @param testContext the test context
     * @param method the test method under execution
     */
    @BeforeMethod(alwaysRun = true)
    public void testSetup() {
        try {
            Browser.launchBrowser(Settings.getUrl());
        }catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * performs a test teardown after each test is executed
     * reports the result of the test capturing a screenshot on failure
     * Closes all open tabs and Logs the user out of salesforce
     * @param result
     */
    @AfterMethod(alwaysRun = true)
    public void testTeardown(ITestResult result) {
        if (!result.isSuccess()) {
            Report.logException(result.getThrowable());
            result.getThrowable().printStackTrace();
            Report.captureScreenshot(Browser.getInstance());
        }
        // if(!Settings.isDebugMode())
        // {
        //     try {
        //         // HomePage homePage = new HomePage(Browser.getInstance());
        //         // homePage.closeAllTabs();
        //         // homePage.logout();
        //     }catch(Exception e) {
        //         Report.captureScreenshot(Browser.getInstance());
        //         Report.logException(e);
        //     }
        
        //     Browser.quit();
        // }
        Browser.quit();
    }

    /**
     * performs a teardown after all tests are executed
     * publishes reports
     */
    @AfterSuite(alwaysRun = true)
    public void globalTeardown() {
        Report.publishReports();
    }
}