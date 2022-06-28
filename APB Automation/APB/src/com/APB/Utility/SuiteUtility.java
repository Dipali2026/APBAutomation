package com.APB.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.google.common.base.Function;

public class SuiteUtility {

	public static WebDriver driver;

	/**
	 * This method initialize browser object
	 * 
	 * @param browser
	 * @return browser driver
	 */

	public static WebDriver openBrowser(String browserTypes) {
		switch (browserTypes) {
		case "firefox":
			driver = new FirefoxDriver();
			break;
		case "chrome":
			driver = new ChromeDriver();
			break;
		case "IE":
			driver = new InternetExplorerDriver();
			break;
		default:
			System.out.println("browser : " + browserTypes + " is invalid, Launching Firefox as browser of choice..");
			driver = new FirefoxDriver();
		}
		return driver;
	}

	public static WebDriver selectBrowser(String browserType) {
		if (browserType.equals("firefox") || browserType.equals("FIREFOX")) {
			System.out.println("firefox browser");

			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("network.proxy.type", 1);
			profile.setPreference("network.proxy.http", "idccfm.axisb.com");
			profile.setPreference("network.proxy.http_port", 1050);
			profile.setPreference("network.proxy.ssl", "idccfm.axisb.com");
			profile.setPreference("network.proxy.ssl_port", 1050);
			profile.setPreference("network.proxy.ftp", "idccfm.axisb.com");
			profile.setPreference("network.proxy.ftp_port", 1050);
			profile.setPreference("network.proxy.host", "idccfm.axisb.com");
			profile.setPreference("network.proxy.host_port", 1050);
			driver = new FirefoxDriver(profile);
			driver.manage().window().maximize();
			return driver;

		} else if (browserType.equals("chrome") || browserType.equals("CHROME")) {
			System.out.println("chrome browser");
			// ChromeOptions options = new ChromeOptions();
			// options.setExperimentalOption("excludeSwitches",
			// Arrays.asList("enable-automation"));
			// options.addArguments("chrome.switches","--disable-extensions");
			// options.addArguments("disable-popup-blocking");
			// options.addArguments("disable-infobars");
			// options.addArguments("start-maximized");
			// options.addArguments("--disable-notifications");

			/*
			 * ChromeProfile profile = new ChromeProfile();
			 * profile.setPreference("network.proxy.type", 1);
			 * profile.setPreference("network.proxy.http", "idccfm.axisb.com");
			 * profile.setPreference("network.proxy.http_port", 1050);
			 * profile.setPreference("network.proxy.ssl", "idccfm.axisb.com");
			 * profile.setPreference("network.proxy.ssl_port", 1050);
			 * profile.setPreference("network.proxy.ftp", "idccfm.axisb.com");
			 * profile.setPreference("network.proxy.ftp_port", 1050);
			 * profile.setPreference("network.proxy.host", "idccfm.axisb.com");
			 * profile.setPreference("network.proxy.host_port", 1050);
			 */
			// For Unix Operating system
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "/src/com/APB/BrowserDrivers/chromedriver.exe");
			driver = new ChromeDriver();
			// For Windows Operating system
			// System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+
			// "\\src\\com\\AXIS\\BrowserDrivers\\chromedriver.exe");
			// ChromeDriver driver = new ChromeDriver(options);
			driver.manage().window().maximize();
			try {
				BrowserF5();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return driver;

		} else if (browserType.equals("ie") || browserType.equals("IE")) {
			System.out.println("IE browser");
			driver = new InternetExplorerDriver();
			driver.manage().window().maximize();
			return driver;
		}
	
		return null;
	}

	/**
	 *
	 * @param timeInsec
	 */
	public void impliciteWait(int timeInsec) {
		Reporter.log("waiting for page to load...");
		try {
			driver.manage().timeouts().implicitlyWait(timeInsec, TimeUnit.SECONDS);
			Reporter.log("Page is loaded");
		} catch (Throwable error) {
			Reporter.log("Timeout for Page Load Request to complete after " + timeInsec + " seconds");
			Assert.assertTrue(false, "Timeout for page load request after " + timeInsec + " second");
		}
	}

	/**
	 *
	 * @param element
	 * @param waitForPageToLoad
	 */
	public static void waitForPageToLoad(long timeOutInSeconds) {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		try {
			System.out.println("Waiting for page to load...");
			WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
			wait.until(expectation);
		} catch (Throwable error) {
			System.out.println(
					"Timeout waiting for Page Load Request to complete after " + timeOutInSeconds + " seconds");
			Assert.assertFalse(true, " Timeout waiting for Page Load Request to complete.");
		}
	}

	/**
	 *
	 * @param element
	 * @param dropDownValue
	 */
	public void selectDropDownValue(WebElement element, String dropDownValue) {
		Select select = new Select(element);
		element.isSelected();
		Reporter.log("selecting " + dropDownValue + " value in dropdown");
		select.selectByVisibleText(dropDownValue);

		// WebElement like = getWebElement("axis.btn_like");
		// like.click();
		// Add_Log.info("Click to Like ");

	}

	public void selectDropDownValueByValue(WebElement element, String dropDownIndex) {
		Select select = new Select(element);
		element.isSelected();
		Reporter.log("selecting " + dropDownIndex + " value in dropdown");
		// select.selectByVisibleText(dropDownValue);
		select.selectByValue(dropDownIndex);
	}

	public void selectRadioByValue(List<WebElement> radioLabels, String labelValue) {
		for (WebElement cmsFlagLabel : radioLabels) {
			if (cmsFlagLabel.getText().equals(labelValue)) {
				WebElement radio = driver.findElement(By.id(cmsFlagLabel.getAttribute("for")));
				radio.click();
				break;
			}
		}
	}

	public boolean verifyDropDownValue(WebElement element, String Value) throws Exception {
		Select select = new Select(element);
		List<WebElement> options = select.getOptions();
		boolean flag = false;
		System.out.println("Number of values is : " + options.size());
		System.out.println("------List of Type of policy dropdown values are :------");
		for (int j = 0; j < options.size(); j++) {

			System.out.println(j + "  " + options.get(j).getText());

			if (options.get(j).getText().equals(Value)) {
				flag = true;
				System.out.println("Value displayed in dropdown :" + options.get(j).getText() + " Matched - " + flag);
				// Assert.assertTrue(match);
				break;

			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean verifyText(WebElement element, String Value) throws Exception {

		boolean flag = false;
		System.out.println(element.getText());
		System.out.println(Value);
		if (element.getText().equals(Value)) {
			flag = true;
			System.out.println("Value displayed on page :" + element.getText() + " Matched - " + flag);
			// Assert.assertTrue(match);

		} else {
			flag = false;
		}
		return flag;
	}

	public void driverWait(int timeToWaitInSec) throws InterruptedException {
		Reporter.log("waiting for " + timeToWaitInSec + " seconds...");
		Thread.sleep(timeToWaitInSec * 1000);
	}

	public static void expliciteWait(WebElement element, int timeToWaitInSec) {
		WebDriverWait wait = new WebDriverWait(driver, timeToWaitInSec);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void clickWhenReady(By locator, int timeout) {
		WebElement element = null;
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		element.click();

	}

	public WebElement fluentWait(final WebElement webElement, int timeinsec) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(timeinsec, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
		WebElement element = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return webElement;
			}
		});

		return element;
	};

	public WebElement getWhenVisible(By locator, int timeout) {

		WebElement element = null;

		WebDriverWait wait = new WebDriverWait(driver, timeout);

		element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

		return element;

	}

	public static String dateFormat(String Date) {
		DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		Date date = new Date();
		// System.out.println(dateFormat.format(date));
		return dateFormat.format(date);
	}

	public static void acceptPopup() {
		try {
			// Wait 10 seconds till alert is present
			WebDriverWait wait = new WebDriverWait(driver, 5);
			Alert alert = wait.until(ExpectedConditions.alertIsPresent());

			// Accepting alert.
			alert.accept();
			System.out.println("Accepted the alert successfully.");
		} catch (Throwable e) {
			System.err.println("Error came while waiting for the alert popup. " + e.getMessage());
		}
	}

	public void navNewTab() throws Exception {
		// get window handlers as list
		List<String> browserTabs = new ArrayList<String>(driver.getWindowHandles());
		// switch to new tab
		driver.switchTo().window(browserTabs.get(1));
		// check is it correct page opened or not (e.g. check page's title)
		// driver.getTitle();
		// System.out.println(driver.getTitle());
		driver.manage().window().maximize();

	}

	public void CloseTab() throws Exception {
		List<String> browserTabs = new ArrayList<String>(driver.getWindowHandles());
		driver.close();
		driver.switchTo().window(browserTabs.get(0));
		// then close tab and get back
	}

	public static void BrowserF5() throws Exception {
		Actions actionObject = new Actions(driver);
		actionObject.keyDown(Keys.CONTROL).sendKeys(Keys.F5).keyUp(Keys.CONTROL).perform();
		System.out.println("Refresh the BrowserPage ");
	}

	public static void clearHistory() throws Exception {
		driver.manage().deleteAllCookies();
		System.out.println("Clean Browser History ");
	}

	public void arrowDown() throws Exception {
		for (int i = 0; i < 5; i++) {
			Actions actionObject = new Actions(driver);
			actionObject.sendKeys(Keys.DOWN).perform();
		}
	}

	// Select parameterized dates from date picker
	public void genericDatePicker(String inputDate) throws Exception {

		driver.findElement(By.id("ui-datepicker-div"));
		/* Here we are getting Month and Year */
		Thread.sleep(1000);
		String month = driver.findElement(By.xpath(".//*[@id='ui-datepicker-div']/div/div/span[1]")).getText();
		Thread.sleep(1000);
		String year = driver.findElement(By.xpath(".//*[@id='ui-datepicker-div']/div/div/span[2]")).getText();
		System.out.println("Application month : " + month + " Year :" + year);
		int monthNum = getMonthNum(month);
		System.out.println("Enum Num : " + monthNum);
		String[] parts = inputDate.split("-"); // Here I am Spliting Our Input
												// String Value
		// Here I am Implementing the Logic
		int noOfHits = ((Integer.parseInt(parts[2]) - Integer.parseInt(year)) * 12)
				+ (Integer.parseInt(parts[1]) - monthNum);
		System.out.println("No OF Hits " + noOfHits);

		if (noOfHits < 0) {
			int noOfHits2 = Math.abs(noOfHits);
			System.out.println("No OF Hits converted value " + noOfHits2);
			for (int i = 0; i < noOfHits2; i++) {

				driver.findElement(By.xpath(".//*[@id='ui-datepicker-div']/div/a[1]/span")).click();
			}
		} else if (noOfHits > 0) {
			for (int i = 0; i < noOfHits; i++) {

				driver.findElement(By.xpath(".//*[@id='ui-datepicker-div']/div/a[2]/span")).click();

			}
		}

		/* selecting the month div */
		List<WebElement> cals = driver.findElements(By.xpath(".//*[@id='ui-datepicker-div']/table/tbody/tr[1]"));
		System.out.println(cals.size());
		/* iterating the "tr" list */
		for (WebElement daterow : cals) {
			/* getting the all "td" s */
			List<WebElement> datenums = daterow.findElements(By.xpath("//td"));
			/* iterating the "td" list */
			for (WebElement date : datenums) {
				/*
				 * Checking The our input Date(if it match go inside and click
				 */

				if (date.getText().equalsIgnoreCase(parts[0])) {

					date.click();

					break;

				}

			}

		}

	}

	// This method will return Month Number
	public int getMonthNum(String month) {
		for (Month mName : Month.values()) {

			if (mName.name().equalsIgnoreCase(month))

				return mName.value;

		}

		return -1;

	}

	// Here Creating Enum Method(Assigning Number to every Month)
	public enum Month {

		January(1), February(2), March(3), April(4), May(5), June(6), July(7), August(8), September(9), October(
				10), November(11), December(12);

		private int value;

		private Month(int value) {

			this.value = value;
		}
	}

	public boolean verifyTableResult(String vInput) {

		boolean flag = false;

		// WebElement table_element =
		// driver.findElement(By.cssSelector(".table.table-striped.table-bordered"));
		WebElement table_element = driver
				.findElement(By.xpath("//*[starts-with(@class,'table table-striped table-bordered')]"));
		List<WebElement> tr_collection = table_element.findElements(By.tagName("tr"));
		System.out.println("NUMBER OF ROWS IN THIS TABLE = " + tr_collection.size());
		int row_num, col_num;
		row_num = 1;
		for (WebElement trElement : tr_collection) {
			List<WebElement> td_collection = trElement.findElements(By.xpath("td"));
			System.out.println("NUMBER OF COLUMNS=" + td_collection.size());
			col_num = 1;
			for (WebElement tdElement : td_collection) {
				System.out.println("row # " + row_num + ", col # " + col_num + "text=" + tdElement.getText());
				col_num++;

				if (tdElement.getText().equals(vInput)) {
					System.out.println("Expected result : " + vInput + " , Actual result :" + tdElement.getText()
							+ ", is matched");
					flag = true;
					break;
				} else {
					System.out.println("Expected result : " + vInput + " ,not found");
					flag = false;
				}
			}
			row_num++;

		}
		return flag;

	}

	public boolean ClickTableLink(String vInput, String vStatus) throws InterruptedException {

		boolean flag = false;
		int row_num, col_num;
		List<WebElement> allpages = driver.findElements(By.xpath("//div/ul/li[@class='page']//a"));
		System.out.println("Total pages :" + allpages.size());

		for (int i = 0; i <= (allpages.size()); i++)

		{

			System.out.println(driver.getPageSource().contains("Displaying 11-19 of 19 results."));

			// WebElement table_element =
			// driver.findElement(By.cssSelector(".table.table-striped.table-bordered"));
			WebElement table_element = driver
					.findElement(By.xpath("//*[starts-with(@class,'table table-striped table-bordered')]"));
			List<WebElement> tr_collection = table_element.findElements(By.tagName("tr"));
			System.out.println("NUMBER OF ROWS IN THIS TABLE = " + tr_collection.size());

			row_num = 1;
			for (WebElement trElement : tr_collection) {

				List<WebElement> td_collection = trElement.findElements(By.xpath("td"));
				System.out.println("NUMBER OF COLUMNS=" + td_collection.size());
				col_num = 1;
				for (WebElement tdElement1 : td_collection) {
					if (tdElement1.getText().equals(vInput)) {
						col_num++;
						System.out.println("row # " + row_num + ", col # " + col_num + "text=" + tdElement1.getText());

						for (WebElement tdElement2 : td_collection) {
							if (tdElement2.getText().equals(vStatus)) {
								col_num++;
								System.out.println(
										"row # " + row_num + ", col # " + col_num + "text=" + tdElement2.getText());
								tdElement1.click();
								flag = true;
								return flag;
							} else {
								System.out.println("Status : " + vStatus + " not found");
								// row_num++;
							}
							// row_num++;
							// allpages =
							// driver.findElements(By.xpath("//div/ul/li[@class='page']//a"));
						}
						// allpages =
						// driver.findElements(By.xpath("//div/ul/li[@class='page']//a"));
						// driver.manage().timeouts().pageLoadTimeout(5,
						// TimeUnit.SECONDS);
						// allpages.get(i).click();
					} else {
						System.out.println("Search value : " + vInput + " ,not found");

					}
				} // allpages =
					// driver.findElements(By.xpath("//div/ul/li[@class='page']//a"));

			}
			allpages = driver.findElements(By.xpath("//div/ul/li[@class='page']//a"));
			allpages.get(i).click();
			Thread.sleep(5000);

			// row_num++;
		}
		return flag;
	}

	public static void writeXLSXFile(String Filepath, int Tcsheet, int row, int col, String setValue)
			throws IOException {

		try {

			FileInputStream file = new FileInputStream(Filepath);

			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.getSheetAt(Tcsheet);
			HSSFCell cell = null;

			// Retrieve the row and check for null
			HSSFRow sheetrow = sheet.getRow(row);
			if (sheetrow == null) {
				sheetrow = sheet.createRow(row);
			}
			// Update the value of cell
			cell = sheetrow.getCell(col);
			if (cell == null) {
				cell = sheetrow.createCell(col);
			}
			cell.setCellValue(setValue);

			file.close();

			FileOutputStream outFile = new FileOutputStream(new File(Filepath));
			workbook.write(outFile);
			outFile.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isTextPresent(String text) {

		boolean flag = false;
		boolean b = driver.getPageSource().contains(text);
		System.out.println("Verified the Expected text  : " + b);

		if (b == true) {
			System.out.println("Verified the Expected text  : " + text);
			flag = true;
			return flag;
		} else {
			System.out.println("Error: The " + text + " Expected result is missing - Failed");
		}
		return flag;
	}

	public static boolean checkToRunUtility(Read_XLS xls, String sheetName, String ToRun, String testSuite) {

		boolean Flag = false;
		if (xls.retrieveToRunFlag(sheetName, ToRun, testSuite).equalsIgnoreCase("y")) {
			Flag = true;
		} else {
			Flag = false;
		}
		return Flag;
	}

	public static String[] checkToRunUtilityOfData(Read_XLS xls, String sheetName, String ColName) {
		return xls.retrieveToRunFlagTestData(sheetName, ColName);
	}

	public static Object[][] GetTestDataUtility(Read_XLS xls, String sheetName) {
		return xls.retrieveTestData(sheetName);
	}

	public static boolean WriteResultUtility(Read_XLS xls, String sheetName, String ColName, int rowNum,
			String Result) {
		return xls.writeResult(sheetName, ColName, rowNum, Result);
	}

	public static boolean WriteResultUtility(Read_XLS xls, String sheetName, String ColName, String rowName,
			String Result) {
		return xls.writeResult(sheetName, ColName, rowName, Result);
	}

	public static boolean WriteResultClientTransferUtility(Read_XLS xls, String sheetName, String ColName,
			String Result) {
		return xls.writeClientResult(sheetName, ColName, Result);
	}

	public static boolean WriteResultClientRefundUtility(Read_XLS xls, String sheetName, String ColName,
			String Result) {
		return xls.writeClientResult(sheetName, ColName, Result);
	}

	public static boolean WriteResultPayTransferUtility(Read_XLS xls, String sheetName, String ColName, int rowNum,
			String Result) {
		return xls.writePayTransferResult(sheetName, ColName, rowNum, Result);
	}

	public static boolean WriteResultPayRefundUtility(Read_XLS xls, String sheetName, String ColName, int rowNum,
			String Result) {
		return xls.writePayRefundResult(sheetName, ColName, rowNum, Result);
	}

	public List<WebElement> waitForDependentPopulation(WebDriver driver, int defaultSleep, String childXpath) {
		WebDriverWait wait = new WebDriverWait(driver, defaultSleep);
		List<WebElement> optionsToSelect = wait
				.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(childXpath), 1));
		return optionsToSelect;
	}

	public void setDatepickerFromString(String dateStr, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date dateCommencementDate = new Date();
		try {
			dateCommencementDate = dateFormat.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar dateCommentmentCal = Calendar.getInstance();
		dateCommentmentCal.setTime(dateCommencementDate);

		WebElement dcYear = driver.findElement(By.xpath("//*[@id='ui-datepicker-div']/div/div/select[2]"));
		Select dcYearSelect = new Select(dcYear);
		dcYearSelect.selectByVisibleText(String.valueOf(dateCommentmentCal.get(Calendar.YEAR)));

		WebElement dcMonth = driver.findElement(By.xpath("//*[@id='ui-datepicker-div']/div/div/select[1]"));
		Select dcMonthSelect = new Select(dcMonth);
		dcMonthSelect.selectByValue(String.valueOf(dateCommentmentCal.get(Calendar.MONTH)));

		WebElement dateBody = driver.findElement(By.xpath("//*[@id='ui-datepicker-div']/table/tbody"));
		List<WebElement> dateList = dateBody.findElements(By.tagName("td"));
		for (WebElement dates : dateList) {
			if (dates.getText().equals(String.valueOf(dateCommentmentCal.get(Calendar.DATE)))) {
				dates.click();
				break;
			}
		}
	}
}
