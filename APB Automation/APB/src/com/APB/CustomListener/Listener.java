package com.APB.CustomListener;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.APB.Utility.SuiteUtility;

public class Listener extends SuiteUtility implements ITestListener{

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		if (result.isSuccess()) {
			   String userDirector = System.getProperty("user.dir");
			   String customeLocation = userDirector+"/APBSucessScreenshots/";
			   String SucessImageFileName =customeLocation+result.getMethod().getMethodName()+"-"+new SimpleDateFormat("MM-dd-yyyy_HH-ss").format(new GregorianCalendar().getTime())+".png";
			   File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			   try {
			    FileUtils.copyFile(scrFile, new File(SucessImageFileName));
			   } catch (IOException e) {
			    e.printStackTrace();
			   }
			   
			   //String userDirector = System.getProperty("user.dir") + "/";
			   Reporter.log("<a href=\"" + SucessImageFileName + "\"><img src=\"file:///" + SucessImageFileName +"\" alt=\"\"" + "height='1000' width='700'/> " + "<br />");
			   Reporter.setCurrentTestResult(null);

			  }
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		if (!result.isSuccess()) {
			   String userDirector = System.getProperty("user.dir");
			   String customeLocation = userDirector+"/APBFailureScreenshots/";
			   String failureImageFileName =customeLocation+result.getMethod().getMethodName()+"-"+new SimpleDateFormat("MM-dd-yyyy_HH-ss").format(new GregorianCalendar().getTime())+".png";
			   File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			   try {
			    FileUtils.copyFile(scrFile, new File(failureImageFileName));
			   } catch (IOException e) {
			    e.printStackTrace();
			   }
			   
			   //String userDirector = System.getProperty("user.dir") + "/";
			   Reporter.log("<a href=\"" + failureImageFileName + "\"><img src=\"file:///" + failureImageFileName +"\" alt=\"\"" + "height='1000' width='700'/> " + "<br />");
			   Reporter.setCurrentTestResult(null);

			  }
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}
	
}
