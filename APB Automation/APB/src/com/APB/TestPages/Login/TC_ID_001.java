package com.APB.TestPages.Login;




import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.APB.TestPages.APBLoginBase;
import com.APB.Utility.Read_XLS;
import com.APB.Utility.SuiteUtility;


//This Testcase Related to Maker/Checker for Company(merchent)
public class TC_ID_001 extends APBLoginBase {

	Read_XLS FilePath = null;
	String SheetName = null;
	String TestCaseName = null;
	String ToRunColumnNameTestCase = null;
	String ToRunColumnNameTestData = null;
	String TestDataToRun[] = null;
	static boolean TestCasePass = true;
	static int DataSet = -1;
	static boolean Testskip = false;
	static boolean Testfail = false;
	SoftAssert s_assert = null;
	String currentURL = null;
	String ExpectedURL = null;
	String APB_Home = System.getProperty("user.dir");
	
	@BeforeTest
	public void checkCaseToRun() throws Exception {
		// Called init() function from MasterTestBase class to Initialize .xls
		// Files
		init();
		// Called Driver_initialization() function from MasterTestBase class to
		// start browser
		// Driver_initialization();
		Driver_initialization();

		// OpenUrl();
		FilePath = APBLoginTestCaseListExcel;
		System.out.println("FilePath Is : " + FilePath);
		TestCaseName = this.getClass().getSimpleName();
		System.out.println("TestCaseName Is : " + TestCaseName);
		// SheetName to check CaseToRun flag against test case.
		SheetName = "APBLoginTCList";
		// Name of column In Excel sheet.
		ToRunColumnNameTestCase = "CaseToRun";
		// Name of column In Test Case Data sheets.
		ToRunColumnNameTestData = "DataToRun";

		// To check test case's CaseToRun = Y or N In related excel sheet.
		// If CaseToRun = N or blank, Test case will skip execution. Else It
		// will be executed.
		if (!SuiteUtility.checkToRunUtility(FilePath, SheetName,
				ToRunColumnNameTestCase, TestCaseName)) {
			// To report result as skip for test cases In ICSTestCasesList
			// sheet.
			SuiteUtility.WriteResultUtility(FilePath, SheetName,
					"Pass/Fail/Skip", TestCaseName, "SKIP");

			throw new SkipException(
					TestCaseName
							+ "'s CaseToRun Flag Is 'N' Or Blank. So Skipping Execution Of "
							+ TestCaseName);

		}
		// To retrieve DataToRun flags of all data set lines from related test
		// data sheet.
		TestDataToRun = SuiteUtility.checkToRunUtilityOfData(FilePath,
				TestCaseName, ToRunColumnNameTestData);
	}

	// Accepts 2 column's String data In every Iteration.
	@Test(dataProvider = "TC_ID_001Data")
	public void TC_ID_001Test(String MobileNumber) throws Exception {

		DataSet++;
		// Created object of testng SoftAssert class.
		s_assert = new SoftAssert();

		// If found DataToRun = "N" for data set then execution will be skipped
		// for that data set.
		if (!TestDataToRun[DataSet].equalsIgnoreCase("Y")) {

			// If DataToRun = "N", Set Testskip=true.
			Testskip = true;

			throw new SkipException("DataToRun for row number " + DataSet
					+ " Is No Or Blank. So Skipping Its Execution.");
		}
		try {

			//MerchentAdminLogin();
			//To Create Maker		
			WebElement login = getWebElement("apb.login.loginid");
			login.click();
			driverWait(3);
			
			WebElement login1 = getWebElement("apb.login.loginid");
			login1.sendKeys(MobileNumber);
			/*
			WebElement option = getWebElement("ipay.maker.makerchecker.option");
			option.click();
			driverWait(3);
			
			WebElement add = getWebElement("ipay.maker.add");
			add.click();
			
			WebElement search = getWebElement("ipay.maker.userid");
			search.sendKeys(UserId);
			driverWait(5);
			
			WebElement email = getWebElement("ipay.maker.email");
			email.sendKeys(EmailId);
			driverWait(5);
			
			WebElement mob = getWebElement("ipay.maker.mobilenum");
			mob.sendKeys(MobileNumber);
		/*	
			WebElement drop = getWebElement("ipay.maker.dropdwn");
			Select down= new Select(drop);
			down.selectByVisibleText(Role);
			driverWait(3);
			
			
			WebElement createCollectionTemplate = getWebElement("ipay.maker.createCollectionTemplate");
			if (CreateCollectionTemplate.equalsIgnoreCase("yes"))
				createCollectionTemplate.click();
			
			WebElement invoices = getWebElement("ipay.maker.invoices");
			if (Invoices.equalsIgnoreCase("yes"))
				invoices.click();
			
			WebElement editViewCollectionTemplate = getWebElement("ipay.maker.editViewCollectionTemplate");
			if (EditViewCollectionTemplate.equalsIgnoreCase("yes"))
				editViewCollectionTemplate.click();
			
			WebElement withEnrichment = getWebElement("ipay.maker.withEnrichment");
			if (WithEnrichment.equalsIgnoreCase("yes"))
				withEnrichment.click();
			
			WebElement withoutEnrichment = getWebElement("ipay.maker.withoutEnrichment");
			if (WithOutEnrichment.equalsIgnoreCase("yes"))
				withoutEnrichment.click();
			
			WebElement againstInvoice = getWebElement("ipay.maker.againstInvoice");
			if (AgainstInvoice.equalsIgnoreCase("yes"))
				againstInvoice.click();
			
			WebElement viewTransaction = getWebElement("ipay.maker.viewTransaction");
			if (ViewTransaction.equalsIgnoreCase("yes"))
				viewTransaction.click();
			
			WebElement setEndDateForCollection = getWebElement("ipay.maker.setEndDateForCollection");
			if (SetEndDateForCollection.equalsIgnoreCase("yes"))
				setEndDateForCollection.click();
			
			WebElement transferSetting = getWebElement("ipay.maker.transferSetting");
			if (Transfersetting.equalsIgnoreCase("yes"))
				transferSetting.click();
			
			WebElement pendingEnddatesetup = getWebElement("ipay.maker.pendingEndDateSetup");
			if (PendingEnddatesetup.equalsIgnoreCase("yes"))
				pendingEnddatesetup.click();
			
			WebElement create = getWebElement("ipay.maker.create");
			create.click();
			driverWait(15);
			
			// verify maker creation
			/* Testfail = verifyMerchantMakerCheckerCreation(UserId);
			if (Testfail) {
				Testfail = false;
				Add_Log.info("Merchant maker verified successfully.");
			} else {
				Testfail = true;
				Add_Log.info("Merchant maker verified successfully.");
				throw new Exception("Error in maker Creation.");
			}
			
			// End verify maker creation

			// To Create Checker
			
			WebElement addc = getWebElement("ipay.maker.add");
			addc.click();
			
			WebElement searchc = getWebElement("ipay.maker.userid");
			searchc.sendKeys(UserIdC);
			driverWait(5);
			
			WebElement emailc = getWebElement("ipay.maker.email");
			emailc.sendKeys(EmailIdC);
			driverWait(5);
			
			WebElement mobc = getWebElement("ipay.maker.mobilenum");
			mobc.sendKeys(MobileNumber);
			
			WebElement dropc = getWebElement("ipay.maker.dropdwn");
			Select downc= new Select(dropc);
			downc.selectByVisibleText(Rolechecker);
			driverWait(5);
			
			WebElement invoices_c = getWebElement("ipay.checker.invoices");
			if (Invoices_CK.equalsIgnoreCase("yes")){
				invoices_c.click();
			}
			WebElement editviewcollectiontemp_c = getWebElement("ipay.checker.editviewcollectiontemp");
			if (EditViewCollection_CK.equalsIgnoreCase("yes"))
				editviewcollectiontemp_c.click();
			WebElement withenrichmnetc = getWebElement("ipay.checker.withenrichmnet");
			if (WithEnrichment_CK.equalsIgnoreCase("yes"))
				withenrichmnetc.click();
			WebElement withoutenrichmentc = getWebElement("ipay.checker.withoutenrichment");
			if (WithOutEnrichment_CK.equalsIgnoreCase("yes")){
				withoutenrichmentc.click();
			}
			WebElement againstinvoicesc= getWebElement("ipay.checker.againstinvoices");
			if (AgainstInvoices_CK.equalsIgnoreCase("yes"))
				againstinvoicesc.click();
			WebElement viewtransactionc = getWebElement("ipay.checker.viewtransaction");
			if (ViewTransaction_CK.equalsIgnoreCase("yes"))
				viewtransactionc.click();
			WebElement setenddateforcollectionc = getWebElement("ipay.checker.setenddateforcollection");
			if (SetEndDateForCollection_CK.equalsIgnoreCase("yes"))
				setenddateforcollectionc.click();
			WebElement transfersettingc = getWebElement("ipay.checker.transfersetting");
			if (TransferSetting_CK.equalsIgnoreCase("yes"))
				transfersettingc.click();
			WebElement create1 = getWebElement("ipay.maker.create");
			create1.click();
			driverWait(10);
*/
			// verify checker creation
			/*Testfail = verifyMerchantMakerCheckerCreation(UserIdC);
			if (Testfail) {
				Testfail = false;
				Add_Log.info("Merchant checker verified successfully.");
			} else {
				Testfail = true;
				Add_Log.info("Merchant checker verified successfully.");
				throw new Exception("Error in Checker Creation.");
			}
			*/
			// End verify checker creation

			//client_Logout();
					
		} catch (Exception e) {

			Testfail = true;
			throw (e);
		}

	}

	// @AfterMethod method will be executed after execution of @Test method
	// every time.
	@AfterMethod
	public void reporterDataResults() {
		if (Testskip)
			// If found Testskip = true, Result will be reported as SKIP against
			// data set line In excel sheet.
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName,
					"Pass/Fail/Skip", DataSet + 1, "SKIP");
		else if (Testfail) {
			// To make object reference null after reporting In report.
			s_assert = null;
			// Set TestCasePass = false to report test case as fail In excel
			// sheet.
			TestCasePass = false;
			// If found Testfail = true, Result will be reported as FAIL against
			// data set line In excel sheet.
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName,
					"Pass/Fail/Skip", DataSet + 1, "FAIL");
		} else
			// If found Testskip = false and Testfail = false, Result will be
			// reported as PASS against data set line In excel sheet.
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName,
					"Pass/Fail/Skip", DataSet + 1, "PASS");
		// At last make both flags as false for next data set.
		Testskip = false;
		Testfail = false;
	}

	@DataProvider
	public Object[][] TC_ID_001Data() {
		// To retrieve data from Username Column,Password Column and Expected
		// Result column of LoginPageTCOne data Sheet.
		// Last two columns (DataToRun and Pass/Fail/Skip) are Ignored
		// programatically when reading test data.
		return SuiteUtility.GetTestDataUtility(FilePath, TestCaseName);
	}

	@AfterTest
	public void teardown() throws Exception {
		// To report result as pass or fail for test cases In ICSTestCasesList
		// sheet.
		if (TestCasePass) {
			SuiteUtility.WriteResultUtility(FilePath, SheetName,
					"Pass/Fail/Skip", TestCaseName, "PASS");
			// closeBrowser();
		} else {
			SuiteUtility.WriteResultUtility(FilePath, SheetName,
					"Pass/Fail/Skip", TestCaseName, "FAIL");
			// closeBrowser();
		}

	}
}


