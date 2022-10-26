package framework.execution;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

/**
 * Extension that performs the soft assert validation checks
 */
public class CustomMethodListener implements IInvokedMethodListener {

    /**
     * calls the soft assert and updates the results based on success/failure
     */
    public synchronized void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            try {
                GlobalBaseTest.softAsserts.get().assertAll();
            } catch (Throwable t) {
                testResult.setStatus(ITestResult.FAILURE);
                if(testResult.getThrowable()!=null)
                {
                    testResult.getThrowable().addSuppressed(t);
                }else{
                    testResult.setThrowable(t);
                }
            }
        }
    }
}