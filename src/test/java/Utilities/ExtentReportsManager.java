package Utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportsManager  implements ITestListener {

    String reportName;
    public ExtentSparkReporter extentSparkReporter; // UI of the report
    public ExtentReports extentReports; // populate common info in the report
    public ExtentTest extentTest; // updating status of test cases in the report

    @Override
    public void onStart(ITestContext context) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.hh.mm.ss").format(new Date());
        reportName = "Test-report-" + timeStamp+".html";
        extentSparkReporter = new ExtentSparkReporter("./reports/"+ reportName);
       extentSparkReporter.config().setDocumentTitle("Orange HRM Automation Project");
       extentSparkReporter.config().setReportName("Functional Testing");
       extentSparkReporter.config().setTheme(Theme.DARK);

       extentReports = new ExtentReports();
       extentReports.attachReporter(extentSparkReporter);
       extentReports.setSystemInfo("OS","Mac OS");
       extentReports.setSystemInfo("QA Person","Rohan");
    }

    public void onTestSuccess(ITestResult result){
        // Get the test class name
        String className = result.getMethod().getRealClass().getName();
        // Get the test method name
        String methodName = result.getMethod().getMethodName();
        extentTest = extentReports.createTest(className + " : " + methodName);
        extentTest.assignCategory(result.getMethod().getGroups());
        extentTest.createNode(result.getName());
        extentTest.log(Status.PASS, "Test Passed");
    }

    public void onTestFailure(ITestResult result){
        // Get the test class name
        String className = result.getMethod().getRealClass().getName();
        // Get the test method name
        String methodName = result.getMethod().getMethodName();
        extentTest = extentReports.createTest(className + " : " + methodName);
        extentTest.assignCategory(result.getMethod().getGroups());
        extentTest.createNode(result.getName());
        extentTest.log(Status.FAIL, "Test Failed");
        extentTest.log(Status.FAIL, result.getThrowable().getMessage());
    }

    public void onTestSkip(ITestResult result){
        // Get the test class name
        String className = result.getMethod().getRealClass().getName();
        // Get the test method name
        String methodName = result.getMethod().getMethodName();
        extentTest = extentReports.createTest(className + " : " + methodName);
        extentTest.assignCategory(result.getMethod().getGroups());
        extentTest.createNode(result.getName());
        extentTest.log(Status.SKIP, "Test Skipped");
        extentTest.log(Status.SKIP, result.getThrowable().getMessage());
    }

    public void onFinish(ITestContext testContext){
        extentReports.flush();
    }
}
