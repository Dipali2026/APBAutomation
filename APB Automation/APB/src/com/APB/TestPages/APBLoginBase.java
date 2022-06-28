package com.APB.TestPages;

import static org.testng.Assert.assertNotEquals;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import com.APB.TestBase.MasterTestBase;
import com.APB.Utility.Read_XLS;
import com.APB.Utility.SuiteUtility;

//import org.apache.log4j.Logger;

//NotificationPagesBase Class Inherits From MasterTestBase Class.
public class APBLoginBase extends MasterTestBase {

	// /*
	// Bellow given syntax will Insert log In applog.log file.
	// Add_Log.info("Execution started for LoginPage.");

	Read_XLS FilePath = null;
	String SheetName = null;
	String SuiteName = null;
	String ToRunColumnName = null;

	// This function will be executed before SuiteOne's test cases to check
	// SuiteToRun flag.
	@BeforeSuite
	public void checkSuiteToRun() throws IOException {
		// Called init() function from SuiteBase class to Initialize .xls Files
		init();
		// To set ICSTestSuiteList.xls file's path In FilePath Variable.
		FilePath = APBTestSuiteListExcel;
		SheetName = "APBSuitesList";
		SuiteName = "APBLoginPage";
		ToRunColumnName = "SuiteToRun";

		// Bellow given syntax will Insert log In ICS_applog.log file.
		Add_Log.info("Execution started for " + SuiteName);

		// If SuiteToRun !== "y" then LoginPage will be skipped from execution.
		if (!SuiteUtility.checkToRunUtility(FilePath, SheetName, ToRunColumnName, SuiteName)) {
			// To report LoginPage as 'Skipped' In SuitesList sheet of
			// ICSTestSuiteList.xls If SuiteToRun = N.
			SuiteUtility.WriteResultUtility(FilePath, SheetName, "Skipped/Executed", SuiteName, "Skipped");
			// It will throw SkipException to skip test suite's execution and
			// suite will be marked as skipped In testng report.
			throw new SkipException(
					SuiteName + "'s SuiteToRun Flag Is 'N' Or Blank. So Skipping Execution Of " + SuiteName);
		}
		// To report LoginPage as 'Executed' In SuitesList sheet of
		// ICSTestSuiteList.xls If SuiteToRun = Y.
		SuiteUtility.WriteResultUtility(FilePath, SheetName, "Skipped/Executed", SuiteName, "Executed");
	}

	public void verifyMessageForRoundCheck(String verificationString) {
		// TODO Auto-generated method stub
		SoftAssert ss = new SoftAssert();
		driver.getPageSource().contains(verificationString);
		ss.assertTrue(driver.getPageSource().contains(verificationString), "Success message not found!");
	}

	public void BasicLogin(String loginType) throws Exception {
		boolean proceed = false;
		driverWait(3);
		String expected = null;
		String userid = StringUtils.EMPTY;
		String pwd = StringUtils.EMPTY;

		switch (loginType) {
		case "maker":
			userid = Repository.getProperty("INDUSIND_LoginMaker_Username");
			pwd = Repository.getProperty("INDUSIND_LoginMaker_Password");
			break;
		case "checker":
			break;
		case "admin":
			userid = Repository.getProperty("INDUSIND_LoginAdmin_Username");
			pwd = Repository.getProperty("INDUSIND_LoginAdmin_Password");
			break;
		}

		while (!proceed) {
			WebElement username = getWebElement("icollect.login.username");
			username.clear();
			username.sendKeys(userid);
			Add_Log.info("Enter the " + loginType + " Username");

			WebElement password = getWebElement("icollect.login.password");
			password.clear();
			password.sendKeys(pwd);
			Add_Log.info("Enter the " + loginType + " Password");

			JavascriptExecutor js = (JavascriptExecutor) driver;
			String captchaStr = "";
			while (captchaStr.equals("")) {
				try {
					js.executeScript("window.promptResponse=prompt('Please enter the captcha within 5 secs.')");

					Alert alert = driver.switchTo().alert();

					driverWait(10);
					alert.accept();
					captchaStr = (String) js.executeScript("return window.promptResponse");
					System.out.println("Captcha is: " + captchaStr);
				} catch (NoAlertPresentException e) {

				}
			}
			assertNotEquals("", captchaStr);

			WebElement captcha = getWebElement("icollect.login.captcha.entry");
			captcha.clear();
			captcha.sendKeys(captchaStr);

			WebElement submitlogin = getWebElement("icollect.login.btnloginsubmit");
			submitlogin.click();

			driverWait(3);

			WebElement success = getWebElement("icollect.login.confirmation");
			expected = success.getText();

			WebElement error = null;
			if (!isTextPresent(expected)) {
				try {
					error = getWebElement("icollect.login.error");
				} catch (NoSuchElementException ex) {
					error = null;
				}
			} else {
				Add_Log.info(expected + " Login succeded");
				System.out.println(expected + " Login succeded");
				error = null;
			}

			proceed = error == null;
		}

		driverWait(3);

		if (driver.getPageSource().contains(expected)) {
			System.out.println(
					"*****************************************Logged In********************************************");
			Add_Log.info("Logged In as " + loginType + userid);

		} else {
			System.out.println("Failed Log In");
			Add_Log.info("Log In failed :" + userid);

		}
	}

	public void BasicLogin1(String loginType) throws Exception {
		boolean proceed = false;
		driverWait(3);
		String expected = null;
		String userid = StringUtils.EMPTY;
		String pwd = StringUtils.EMPTY;

		switch (loginType) {
		case "maker":
			userid = Repository.getProperty("INDUSIND_LoginMaker_Username");
			pwd = Repository.getProperty("INDUSIND_LoginMaker_Password");
			break;
		case "checker":
			break;
		case "admin":
			userid = Repository.getProperty("INDUSIND_LoginAdmin_Username");
			pwd = Repository.getProperty("INDUSIND_LoginAdmin_Password");
			break;
		}

		while (!proceed) {
			WebElement username = getWebElement("icollect.login.username");
			username.clear();
			username.sendKeys(userid);
			Add_Log.info("Enter the " + loginType + " Username");

			WebElement password = getWebElement("icollect.login.password");
			password.clear();
			password.sendKeys(pwd);
			Add_Log.info("Enter the " + loginType + " Password");

			JavascriptExecutor js = (JavascriptExecutor) driver;
			String captchaStr = "";
			while (captchaStr.equals("")) {
				try {
					js.executeScript("window.promptResponse=prompt('Please enter the captcha within 5 secs.')");

					Alert alert = driver.switchTo().alert();

					driverWait(10);
					alert.accept();
					captchaStr = (String) js.executeScript("return window.promptResponse");
					System.out.println("Captcha is: " + captchaStr);
				} catch (NoAlertPresentException e) {

				}
			}
			assertNotEquals("", captchaStr);

			WebElement captcha = getWebElement("icollect.login.captcha.entry");
			captcha.clear();
			captcha.sendKeys(captchaStr);

			WebElement submitlogin = getWebElement("icollect.login.btnloginsubmit");
			submitlogin.click();

			driverWait(3);

			WebElement success = getWebElement("icollect.admin.confirmation");
			expected = success.getText();

			WebElement error = null;
			if (!isTextPresent(expected)) {
				try {
					error = getWebElement("icollect.login.error");
				} catch (NoSuchElementException ex) {
					error = null;
				}
			} else {
				Add_Log.info(expected + " Login succeded");
				System.out.println(expected + " Login succeded");
				error = null;
			}

			proceed = error == null;
		}

		driverWait(3);

		if (driver.getPageSource().contains(expected)) {
			System.out.println(
					"*****************************************Logged In********************************************");
			Add_Log.info("Logged In as " + loginType + userid);

		} else {
			System.out.println("Failed Log In");
			Add_Log.info("Log In failed :" + userid);

		}
	}

	public void MakerLogin() throws Exception {
		BasicLogin("maker");
	}

	public void payUserLogin() throws Exception {
		WebElement username = getWebElement("icollect.login.username");
		username.sendKeys(Repository.getProperty("INDUSIND_LoginPay_Username"));
		Add_Log.info("Enter the payUser Username");

		WebElement password = getWebElement("icollect.login.password");
		password.sendKeys(Repository.getProperty("INDUSIND_LoginPay_Password"));
		Add_Log.info("Enter the payUser Password");

		// saurabh
		WebElement captcha = getWebElement("icollect.login.captcha.entry");
		captcha.clear();
		captcha.sendKeys("777");
		// saurabh

		WebElement submitlogin = getWebElement("icollect.login.btnloginsubmit");
		submitlogin.click();

		driverWait(5);
		WebElement success = getWebElement("ipay.login.confirmation");
		String expected = success.getText();
		isTextPresent(expected);
		Add_Log.info(expected + " Login succeded");
		System.out.println(expected + " Login succeded");

		if (driver.getPageSource().contains(expected)) {
			System.out.println(
					"*****************************************Logged In********************************************");
			Add_Log.info(
					"Logged In as payUser:" + Repository.getProperty("INDUSIND_LoginPay_Username") + " My Portfolio");

		} else {
			System.out.println("Failed Log In");
			Add_Log.info("Log In failed :" + Repository.getProperty("INDUSIND_LoginPay_Username") + " My Portfolio");

		}
		driverWait(5);
	}

	public void MakerLogOut() throws Exception {
		getWebElement("icollect.login.confirmation").click();
	}

	public void CheckerLogin() throws Exception {
		BasicLogin("checker");
	}

	public void CheckerLogOut() throws Exception {
		getWebElement("icollect.login.confirmation").click();
	}

	public void verifyMessage() {
		driver.getPageSource().contains("Notification created successfully!");

		Assert.assertTrue(driver.getPageSource().contains("Notification created successfully!"),
				"Success message not found!");
	}

	public void VerifyExpectedValue(WebElement element, String expectedvalue) {
		if (element.getText().equals(expectedvalue)) {
			Add_Log.info("Expected value  " + element.getText() + " has been displayed");

		} else {
			Add_Log.info("Error:Expected value  " + expectedvalue
					+ " has not displayed, where as the displayed value is  " + element.getText());
		}
		return;
	}

	public void VerifyIcon(WebElement element, String value) {

		String iconpath = element.getAttribute("src");
		System.out.println("Actual Icon path is ----" + iconpath);

		String ApplicationPath = Repository.getProperty("AXIS_ADMIN_URL");
		String ValuePath = value;
		String ExpectedPath = ApplicationPath + ValuePath;
		System.out.println("Expected Icon path is ----" + ExpectedPath);
		if (iconpath.equals(ExpectedPath)) {
			Add_Log.info("Expected icon path  " + iconpath + " has been displayed");

		} else {
			Add_Log.info("Error:Expected icon path   " + ExpectedPath
					+ " has not displayed, where as the displayed value is  " + element.getText());
		}
		return;
	}

	public void nav_MTonetab(WebElement element) throws Exception {
		driverWait(3);
		Add_Log.info("Navigate to  " + element.getText() + " page");
		element.click();
		driverWait(3);

	}

	public void nav_MTtwotabs(WebElement element1, WebElement element2) throws Exception {
		driverWait(3);
		Add_Log.info("Navigate to  " + element1.getText() + " page");
		element1.click();
		driverWait(3);
		Add_Log.info("Navigate to  " + element2.getText() + " page");
		element2.click();

	}

	public void nav_MTthreetabs(WebElement element1, WebElement element2, WebElement element3) throws Exception {
		driverWait(3);
		element1.click();
		Add_Log.info("Navigate to  " + element1.getText() + " page");
		driverWait(3);
		Add_Log.info("Navigate to  " + element2.getText() + " page");
		element2.click();
		driverWait(3);
		Add_Log.info("Navigate to  " + element3.getText() + " page");
		element3.click();
	}

	public boolean checkInternetPayValid() throws Exception {
		boolean flag = false;

		WebElement baseamount = getWebElement("ipay.menu.internetbanking.baseamount");
		String baseAmt = baseamount.getText();
		baseAmt.replaceAll("\\s", "");
		String baseAmt1 = baseAmt.replace(",", "");
		double bAmt = Double.parseDouble(amtSplit(baseAmt1));
		System.out.println(bAmt);

		WebElement commission_amt = getWebElement("ipay.menu.internetbanking.commissionamt");
		String commissionAmt = commission_amt.getText();
		commissionAmt.replaceAll("\\s", "");
		String commissionAmt1 = commissionAmt.replace(",", "");
		double comsAmt = Double.parseDouble(amtSplit(commissionAmt1));
		System.out.println(comsAmt);

		WebElement loot_amt = getWebElement("ipay.menu.internetbanking.loot");
		String lootAmt = loot_amt.getText();
		lootAmt.replaceAll("\\s", "");
		String lootAmt1 = lootAmt.replace(",", "");
		double loAmt = Double.parseDouble(amtSplit(lootAmt1));
		System.out.println(loAmt);

		WebElement total_amt = getWebElement("ipay.menu.internetbanking.totalamount");
		String tatalAmt = total_amt.getText();
		tatalAmt.replaceAll("\\s", "");
		String tatalAmt1 = tatalAmt.replace(",", "");
		double tAmt = Double.parseDouble(amtSplit(tatalAmt1));
		System.out.println(tAmt);

		if ((bAmt + comsAmt + loAmt) == tAmt) {
			flag = true;
			return flag;
		}
		return flag;
	}

	public boolean checkCreditPayValid() throws Exception {
		boolean flag = false;

		WebElement baseamount = getWebElement("ipay.menu.creditcard.baseamount");
		String baseAmt = baseamount.getText();
		System.out.println(baseAmt);
		baseAmt.replaceAll("\\s", "");
		String baseAmt1 = baseAmt.replace(",", "");
		double bAmt = Double.parseDouble(amtSplit(baseAmt1));
		System.out.println(baseAmt1);

		WebElement commission_amt = getWebElement("ipay.menu.creditcard.commissionamt");
		String commissionAmt = commission_amt.getText();
		commissionAmt.replaceAll("\\s", "");
		String commissionAmt1 = commissionAmt.replace(",", "");
		double comsAmt = Double.parseDouble(amtSplit(commissionAmt1));
		System.out.println(comsAmt);
		WebElement loot_amt = getWebElement("ipay.menu.creditcard.loot");
		String lootAmt = loot_amt.getText();
		lootAmt.replaceAll("\\s", "");
		String lootAmt1 = lootAmt.replace(",", "");
		double loAmt = Double.parseDouble(amtSplit(lootAmt1));
		System.out.println(loAmt);

		WebElement total_amt = getWebElement("ipay.menu.creditcard.totalamt");
		String tatalAmt = total_amt.getText();
		tatalAmt.replaceAll("\\s", "");
		String tatalAmt1 = tatalAmt.replace(",", "");
		double tAmt = Double.parseDouble(amtSplit(tatalAmt1));
		System.out.println(tAmt);
		if ((bAmt + comsAmt + loAmt) == tAmt) {
			flag = true;
			return flag;
		}

		return flag;
	}

	public boolean checkDebitPayValid() throws Exception {
		boolean flag = false;

		WebElement baseamount = getWebElement("ipay.menu.debitcard.baseamount");
		String baseAmt = baseamount.getText();
		System.out.println(baseAmt);
		baseAmt.replaceAll("\\s", "");
		String baseAmt1 = baseAmt.replace(",", "");
		double bAmt = Double.parseDouble(amtSplit(baseAmt1));
		System.out.println(baseAmt1);

		WebElement commission_amt = getWebElement("ipay.menu.debitcard.commissionamt");
		String commissionAmt = commission_amt.getText();
		commissionAmt.replaceAll("\\s", "");
		String commissionAmt1 = commissionAmt.replace(",", "");
		double comsAmt = Double.parseDouble(amtSplit(commissionAmt1));
		System.out.println(comsAmt);

		WebElement loot_amt = getWebElement("ipay.menu.debitcard.loot");
		String lootAmt = loot_amt.getText();
		lootAmt.replaceAll("\\s", "");
		String lootAmt1 = lootAmt.replace(",", "");
		double loAmt = Double.parseDouble(amtSplit(lootAmt1));
		System.out.println(loAmt);

		WebElement total_amt = getWebElement("ipay.menu.debitcard.totalamt");
		String tatalAmt = total_amt.getText();
		tatalAmt.replaceAll("\\s", "");
		String tatalAmt1 = tatalAmt.replace(",", "");
		double tAmt = Double.parseDouble(amtSplit(tatalAmt1));
		System.out.println(tAmt);

		if ((bAmt + comsAmt + loAmt) == tAmt) {
			flag = true;
			return flag;
		}

		return flag;
	}

	public boolean checkCashPayValid() throws Exception {
		boolean flag = false;

		WebElement baseamount = getWebElement("ipay.menu.cash.baseamount");
		String baseAmt = baseamount.getText();
		System.out.println(baseAmt);
		baseAmt.replaceAll("\\s", "");
		String baseAmt1 = baseAmt.replace(",", "");
		double bAmt = Double.parseDouble(amtSplit(baseAmt1));
		System.out.println(baseAmt1);

		WebElement commission_amt = getWebElement("ipay.menu.cash.commissionamt");
		String commissionAmt = commission_amt.getText();
		commissionAmt.replaceAll("\\s", "");
		String commissionAmt1 = commissionAmt.replace(",", "");
		double comsAmt = Double.parseDouble(amtSplit(commissionAmt1));

		WebElement loot_amt = getWebElement("ipay.menu.cash.loot");
		String lootAmt = loot_amt.getText();
		lootAmt.replaceAll("\\s", "");
		String lootAmt1 = lootAmt.replace(",", "");
		double loAmt = Double.parseDouble(amtSplit(lootAmt1));

		WebElement total_amt = getWebElement("ipay.menu.cash.totalamt");
		String tatalAmt = total_amt.getText();
		tatalAmt.replaceAll("\\s", "");
		String tatalAmt1 = tatalAmt.replace(",", "");
		double tAmt = Double.parseDouble(amtSplit(tatalAmt1));

		if ((bAmt + comsAmt + loAmt) == tAmt) {
			flag = true;
			return flag;
		}

		return flag;
	}

	public boolean checkUPIPayValid() throws Exception {
		boolean flag = false;

		WebElement baseamount = getWebElement("ipay.menu.upi.baseamount");
		String baseAmt = baseamount.getText();
		System.out.println(baseAmt);
		baseAmt.replaceAll("\\s", "");
		String baseAmt1 = baseAmt.replace(",", "");
		double bAmt = Double.parseDouble(amtSplit(baseAmt1));
		System.out.println(baseAmt1);

		WebElement commission_amt = getWebElement("ipay.menu.upi.commissionamt");
		String commissionAmt = commission_amt.getText();
		commissionAmt.replaceAll("\\s", "");
		String commissionAmt1 = commissionAmt.replace(",", "");
		double comsAmt = Double.parseDouble(amtSplit(commissionAmt1));

		WebElement loot_amt = getWebElement("ipay.menu.upi.loot");
		String lootAmt = loot_amt.getText();
		lootAmt.replaceAll("\\s", "");
		String lootAmt1 = lootAmt.replace(",", "");
		double loAmt = Double.parseDouble(amtSplit(lootAmt1));

		WebElement total_amt = getWebElement("ipay.menu.upi.totalamt");
		String tatalAmt = total_amt.getText();
		tatalAmt.replaceAll("\\s", "");
		String tatalAmt1 = tatalAmt.replace(",", "");
		double tAmt = Double.parseDouble(amtSplit(tatalAmt1));

		if ((bAmt + comsAmt + loAmt) == tAmt) {
			flag = true;
			return flag;
		}

		return flag;
	}

	public String amtSplit(String amt) {
		String string = amt;
		String[] parts = string.split("\\.", 2);
		String part1 = parts[0];
		String part2 = parts[1];

		return part2;
	}

	public boolean SelectCategory(String CATEGORY) throws Exception {
		boolean flag = false;

		WebElement mytable = getWebElement("icollect.checker.search.table");
		// To locate rows of table.
		List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
		// To calculate no of rows In table.
		int rows_count = rows_table.size();
		// Loop will execute till the last row of table.
		for (int row = 0; row < rows_count; row++) {
			// To locate columns(cells) of that specific row.
			List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
			// To calculate no of columns (cells). In that specific row.
			int columns_count = Columns_row.size();
			System.out.println("Number of cells In Row " + row + " are " + columns_count);
			// Loop will execute till the last cell of that specific row.
			for (int column = 0; column < columns_count; column++) {
				// To retrieve text from that specific cell.
				String categoryName = Columns_row.get(0).getText();
				String companyName = Columns_row.get(1).getText();
				String templateName = Columns_row.get(2).getText();
				String statusName = Columns_row.get(3).getText();

				System.out.println("CategoryName " + categoryName);
				System.out.println("CompanyName " + companyName);
				System.out.println("TemplateName " + templateName);
				System.out.println("StatusName " + statusName);
				if (categoryName != null && categoryName.equals(CATEGORY)) {

					WebElement approve = driver
							.findElement(By.xpath("html/body/div[1]/div/div/div/div[2]/div/div[1]/div[2]/ul/li/a"));
					approve.click();
					flag = true;
					return flag;
				}
			}
		}

		return flag;
	}

	public boolean SelectCompany(String Company) throws Exception {
		boolean flag = false;

		WebElement mytable = getWebElement("ipay.select.company.all");
		// To locate rows of table.
		List<WebElement> rows_table = mytable.findElements(By.tagName("li"));
		// To calculate no of rows In table.
		int rows_count = rows_table.size();
		// Loop will execute till the last row of table.
		for (int row = 0; row < rows_count; row++) {
			// To locate columns(cells) of that specific row.
			List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("a"));
			// To calculate no of columns (cells). In that specific row.
			int columns_count = Columns_row.size();
			System.out.println("Number of cells In Row " + row + " are " + columns_count);
			// Loop will execute till the last cell of that specific row.
			for (int column = 0; column < columns_count; column++) {
				// To retrieve text from that specific cell.
				String companyName = Columns_row.get(column).getText();

				System.out.println("CompanyName " + companyName);

				if (companyName != null && companyName.equalsIgnoreCase(Company)) {
					WebElement approve = driver.findElement(By.xpath("html/body/div[1]/div[1]/div[3]/div/div[3]/div[2]/ul/li["
							+ (row + 1) + "]/div/a[" + (column + 1) + "]/div"));
					approve.click();
					flag = true;
					return flag;
				}
			}
		}

		return flag;
	}
	// End

	// For Verify SignUp User Creation
	public boolean verifySignUpUser() throws Exception {
		boolean flag = false;

		if (!(driver.getPageSource().contains("has already been taken."))) {
			if (!(driver.getPageSource().contains("Field can contain only alphabetic characters"))) {
				flag = true;
				return flag;
			}
		}

		return flag;
	}
	// End Verify SignUp User Creation

	// saurabh for category select
	public boolean SelectCategoryforMakePayment(String PaymentCategory) throws Exception {
		boolean flag = false;

		WebElement mytable = getWebElement("ipay.select.all.category");
		// To locate rows of table.
		List<WebElement> rows_table = mytable.findElements(By.tagName("li"));
		// To calculate no of rows In table.
		int rows_count = rows_table.size();
		// Loop will execute till the last row of table.
		for (int row = 0; row < rows_count; row++) {
			// To locate columns(cells) of that specific row.
			List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("a"));
			// To calculate no of columns (cells). In that specific row.
			int columns_count = Columns_row.size();
			System.out.println("Number of cells In Row " + row + " are " + columns_count);
			// Loop will execute till the last cell of that specific row.
			for (int column = 0; column < columns_count; column++) {
				// To retrieve text from that specific cell.
				String paymentCategory = Columns_row.get(column).getText();

				System.out.println("CompanyName " + paymentCategory);

				if (paymentCategory != null && paymentCategory.equalsIgnoreCase(PaymentCategory)) {
					WebElement approve = driver.findElement(By.xpath("html/body/div[1]/div/div[3]/div/div/div[4]/div[2]/ul/li["
							+ (row + 1) + "]/div/a[" + (column + 1) + "]/div"));
					approve.click();
					flag = true;
					return flag;
				}
			}
		}

		return flag;
	}


}
