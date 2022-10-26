package framework.execution;

import java.util.Map;
import java.util.Set;

import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ISuiteResult;
import org.testng.ITestResult;

public class CustomSuiteListener implements ISuiteListener {
    
    public void onFinish(ISuite suite) {
        // not implemented
        Map<String, ISuiteResult> results = suite.getResults();

        for (Map.Entry<String,ISuiteResult> entry : results.entrySet())  
        {
            IResultMap resultsMap = entry.getValue().getTestContext().getPassedConfigurations();

            Set<ITestResult> testResults = resultsMap.getAllResults();
            testResults.forEach(t -> {
                testResults.remove(t);
    
            });
        } 

    }
}