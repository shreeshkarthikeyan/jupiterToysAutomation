package framework.execution;

import org.testng.asserts.IAssert;

import framework.Report;

/**
 * Extension of the TestNG soft assert
 * Records the results of an assert at the time of execution to the reports
 */
public class SoftAssert extends org.testng.asserts.SoftAssert {
    
    public void onAssertFailure(IAssert<?> assertCommand, AssertionError ex) {
        Report.logFail(assertCommand.getMessage() + ": Actual [" + assertCommand.getActual() + "], Expected [" + assertCommand.getExpected() + "]");
        super.onAssertFailure(assertCommand, ex);
    }

    public void onAssertSuccess(IAssert<?> assertCommand) {
        Report.logPass("Comparing: Actual [" + assertCommand.getActual() + "], Expected [" + assertCommand.getExpected() + "]");
        super.onAssertSuccess(assertCommand);
    }
}