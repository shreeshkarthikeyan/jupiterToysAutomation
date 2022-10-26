
package framework.execution;

import java.lang.reflect.Method;

import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import framework.Report;

@Listeners({CustomMethodListener.class})
public class GlobalBaseTest {
    
    public static ThreadLocal<SoftAssert> softAsserts = new ThreadLocal<SoftAssert>();

    /**
     * performs a global setup before any test is executed
     * Initializes reporting
     * Initializes the test settings required to perform execution
     */
    @BeforeSuite(alwaysRun = true)
    public void globalSetup() {
        try
        {
            //Settings.readSettings();
            Report.initializeReporting();
        }catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
        
    }

    @BeforeClass(alwaysRun = true)
    public void suiteSetup(ITestContext testContext) {
        String className = this.getClass().getName();
        System.out.println("Creating suite: " + className);
        
        Report.createSuite(this.getClass().getName().replaceAll("tests.", ""));
        
    }

    /**
     * performs a test setup before each test is executed
     * Initializes the driver required for each test
     * Creates a test entry in the reports
     * Initializes the soft asserts
     * @param testContext the test context
     * @param method the test method under execution
     */
    @BeforeMethod(alwaysRun = true)
    public void testSetup(ITestContext testContext, Method method) {       
        softAsserts.set(new SoftAssert());
        Report.createTest(this.getClass().getName().replaceAll("tests.", ""), method.getName());
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