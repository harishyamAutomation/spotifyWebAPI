package base;

import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import core.OutputLog;
import helper.BaseTestHelper;
import util.ExtentReport;

public class BaseTest {
	
	private static ThreadLocal<ExtentTest> extentTest = null;
	
	public static ThreadLocal<ExtentTest> getTest(){
		if(extentTest==null) {
			extentTest = new ThreadLocal<ExtentTest>(); 
		}
		return extentTest;
	}
	
	@BeforeSuite(alwaysRun = true)
	public void config(@Optional("Optional Name Spotify WebAPI") String reportName, @Optional("Regression Report") String flow) {
		String folderPath = System.getProperty("user.dir")+"/reports/"+BaseTestHelper.currentDate();
		BaseTestHelper.createFolder(folderPath);
		
		ExtentReport.getInstance().initialize(folderPath+"/"+BaseTestHelper.timeStamp()+"Spotify_WebAPI_Regression.html");
		OutputLog.info("Before suite executed.");
	}
	
	@BeforeTest
	public void setup(){
		OutputLog.info(">>> Execution Started");
	}
	
	@BeforeMethod(alwaysRun = true)
	public void logBeforeMethod(Method method) {
		Test annotation = method.getAnnotation(Test.class);
		String desc = annotation.description()==null?"":annotation.description();
		ExtentReport.startTest(method.getName(), "<b>Description</b> : "+desc+"<hr style='margin:0.1%;'/> ");
		OutputLog.info(">>> "+method.getName()+"execution has been started.");
	}
	
	@AfterMethod(alwaysRun = true)
	public void getResult(ITestResult result) {
		
		if(result.getStatus()==ITestResult.SUCCESS) {
			ExtentReport.getTest().generateLog(Status.PASS, result.getName()+" : "+result.getMethod().getDescription()+" has been <b style='color:green'>PASSED</b>");
		}else if(result.getStatus()==ITestResult.FAILURE) {
			ExtentReport.getTest().generateLog(Status.FAIL, result.getName()+" : "+result.getMethod().getDescription()+" has been <b style='color:red'>FAILED</b></br>due to<br/>"+result.getThrowable());
		}else if(result.getStatus()==ITestResult.SKIP) {
			ExtentReport.getTest().generateLog(Status.SKIP, result.getName()+" : "+result.getMethod().getDescription()+" has been <b style='color:blue'>SKIPPED</b>");
		}
	}
	
	@AfterSuite
	public void endReport() {
		ExtentReport.getInstance().flush();
		OutputLog.info(">>> Test Suite executed successfully.");
	}
	
}
