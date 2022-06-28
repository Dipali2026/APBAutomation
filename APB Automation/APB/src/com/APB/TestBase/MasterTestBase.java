package com.APB.TestBase;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.APB.Utility.Read_XLS;
import com.APB.Utility.ReportsUtility;
import com.APB.Utility.SuiteUtility;

public class MasterTestBase extends SuiteUtility {

	public static Properties Repository = new Properties();
	public File file;
	public FileInputStream FI;

	public static Read_XLS APBTestSuiteListExcel = null;
	//public static Read_XLS APBTestCaseListExcel = null;
	public static Read_XLS APBLoginTestCaseListExcel=null;
	//public static Read_XLS INDPayTransactionIdExcel=null;
	//public static Read_XLS INDClientTestCaseListExcel=null;
	
	public static Logger Add_Log = null;

	public void Driver_initialization() throws IOException {
		selectBrowser(Repository.getProperty("browserType"));

		System.out.println(Repository.getProperty("browserType"));
		System.out.println(Repository.getProperty("APB_URL"));

		impliciteWait(30);
		driver.get(Repository.getProperty("APB_URL"));
		driver.navigate().refresh();
	}
	
	
	public void PayDriver_initialization() throws IOException {
		selectBrowser(Repository.getProperty("browserType"));

		System.out.println(Repository.getProperty("browserType"));
		System.out.println(Repository.getProperty("INDUSIND_PAY_URL"));

		impliciteWait(30);
		driver.get(Repository.getProperty("INDUSIND_PAY_URL"));
		driver.navigate().refresh();
	}
	public void ClentDriver_initialization() throws IOException {
		selectBrowser(Repository.getProperty("browserType"));

		System.out.println(Repository.getProperty("browserType"));
		System.out.println(Repository.getProperty("INDUSIND_CLIENT_URL"));

		impliciteWait(30);
		driver.get(Repository.getProperty("INDUSIND_CLIENT_URL"));
		driver.navigate().refresh();
	}
	
	public void admin_Logout() throws Exception
	{
	WebElement Logout = getWebElement("icollect.admin.Logout");
	Logout.click();
	driverWait(5);
	}
	
	public void client_Logout() throws Exception
	{
	WebElement Logout = getWebElement("icollect.client.Logout");
	Logout.click();
	driverWait(5);
	}
	public void PageRefresh() {
		driver.navigate().refresh();
	}

	public void mailReports() throws Exception {
		ReportsUtility.copyFiles(new File(Repository.getProperty("CRsource1")),
				new File(Repository.getProperty("CRdestination1")));
		System.out.println("Source1 copied");
		ReportsUtility.copyFiles(new File(Repository.getProperty("CRsource2")),
				new File(Repository.getProperty("CRdestination2")));
		System.out.println("Source2 copied");
		ReportsUtility.copyFiles(new File(Repository.getProperty("CRsource3")),
				new File(Repository.getProperty("CRdestination3")));
		System.out.println("Source3 copied");
		ReportsUtility.zipFolder((Repository.getProperty("ZRsource")),
				(Repository.getProperty("ZRdestination")));
		System.out.println("ZipFile created");

		String[] attacheFiles = new String[1];
		attacheFiles[0] = Repository.getProperty("attacheFiles");
		System.out.println("ZipFile path passed");
		ReportsUtility.sendEmailWithAttachments(Repository.getProperty("host"),
				Repository.getProperty("port"),
				Repository.getProperty("mailFrom"),
				Repository.getProperty("password"),
				Repository.getProperty("mailTo"),
				Repository.getProperty("subject"),
				Repository.getProperty("message"), attacheFiles);

	}

	public void init() throws IOException {
		loadXlsFile();
		loadPropertiesFile();

	}

	public void OpenUrl() throws IOException {
		driver.get(Repository.getProperty("url"));
	}

	public void Project_Home() {
		String Project_Home = System.getProperty("user.dir");
		System.out.println(Project_Home);
	}

	public void loadPropertiesFile() throws IOException {

		file = new File(System.getProperty("user.dir")
				+ "/src/com/APB/BrowserDrivers/config.properties");
		FI = new FileInputStream(file);
		Repository.load(FI);

		File propertyFolder = new File(System.getProperty("user.dir")
				+ "/src/com/APB/Property");
		File[] propertyFiles = propertyFolder.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				return (pathname.isFile() && FilenameUtils.getExtension(
						pathname.getName()).equals("properties"));
			}
		});
		for (File propFile : propertyFiles) {
			file = propFile;
			FI = new FileInputStream(file);
			Repository.load(FI);

			Add_Log.info("Properties File:" + propFile.getName()
					+ " Initialized successfully.");
		}

		Add_Log.info("All Properties Files Initialized successfully.");
	}

	public static WebElement getLocator(String locator) throws Exception {
		String[] split = locator.split(":");
		String locatorType = split[0];
		String locatorValue = split[1];

		if (locatorType.toLowerCase().equals("id"))
			return driver.findElement(By.id(locatorValue));
		else if (locatorType.toLowerCase().equals("name"))
			return driver.findElement(By.name(locatorValue));
		else if ((locatorType.toLowerCase().equals("classname"))
				|| (locatorType.toLowerCase().equals("class")))
			return driver.findElement(By.className(locatorValue));
		else if ((locatorType.toLowerCase().equals("tagname"))
				|| (locatorType.toLowerCase().equals("tag")))
			return driver.findElement(By.className(locatorValue));
		else if ((locatorType.toLowerCase().equals("linktext"))
				|| (locatorType.toLowerCase().equals("link")))
			return driver.findElement(By.linkText(locatorValue));
		else if (locatorType.toLowerCase().equals("partiallinktext"))
			return driver.findElement(By.partialLinkText(locatorValue));
		else if ((locatorType.toLowerCase().equals("cssselector"))
				|| (locatorType.toLowerCase().equals("css")))
			return driver.findElement(By.cssSelector(locatorValue));
		else if (locatorType.toLowerCase().equals("xpath"))
			return driver.findElement(By.xpath(locatorValue));
		else
			throw new Exception("Unknown locator type '" + locatorType + "'");
	}

	public static List<WebElement> getLocators(String locator) throws Exception {
		String[] split = locator.split(":");
		String locatorType = split[0];
		String locatorValue = split[1];

		if (locatorType.toLowerCase().equals("id"))
			return driver.findElements(By.id(locatorValue));
		else if (locatorType.toLowerCase().equals("name"))
			return driver.findElements(By.name(locatorValue));
		else if ((locatorType.toLowerCase().equals("classname"))
				|| (locatorType.toLowerCase().equals("class")))
			return driver.findElements(By.className(locatorValue));
		else if ((locatorType.toLowerCase().equals("tagname"))
				|| (locatorType.toLowerCase().equals("tag")))
			return driver.findElements(By.className(locatorValue));
		else if ((locatorType.toLowerCase().equals("linktext"))
				|| (locatorType.toLowerCase().equals("link")))
			return driver.findElements(By.linkText(locatorValue));
		else if (locatorType.toLowerCase().equals("partiallinktext"))
			return driver.findElements(By.partialLinkText(locatorValue));
		else if ((locatorType.toLowerCase().equals("cssselector"))
				|| (locatorType.toLowerCase().equals("css")))
			return driver.findElements(By.cssSelector(locatorValue));
		else if (locatorType.toLowerCase().equals("xpath"))
			return driver.findElements(By.xpath(locatorValue));
		else
			throw new Exception("Unknown locator type '" + locatorType + "'");
	}

	public WebElement getWebElement(String locator) throws Exception {
		return getLocator(Repository.getProperty(locator));
	}

	public List<WebElement> getWebElements(String locator) throws Exception {
		return getLocators(Repository.getProperty(locator));
	}

	public String getXPath(String locator) {
		String[] split = Repository.getProperty(locator).split(":");
		String locatorValue = split[1];

		return locatorValue;
	}

	public void closeBrowser() {
		driver.quit();
		System.out.println("Browser window closed");
		Add_Log.info("Browser window closed");
	}

	public void loadXlsFile() throws IOException {
		// To Initialize logger service.
		Add_Log = Logger.getLogger("rootLogger");

		// Please change file's path strings bellow If you have stored them at
		// location other than bellow.
		//Please change file's path strings bellow If you have stored them at location other than bellow.
		APBTestSuiteListExcel = new Read_XLS(System.getProperty("user.dir")+"/src/com/APB/ExcelFiles/APBTestSuiteList.xls");
		
		//Initializing Test Suite (Notification.xls) File Path Using Constructor Of Read_XLS Utility Class.
		//APBTestCaseListExcel = new Read_XLS(System.getProperty("user.dir")+"/src/com/APB/ExcelFiles/APBLogin.xls");
		
		//Initializing Test Suite (Notification.xls) File Path Using Constructor Of Read_XLS Utility Class.
		APBLoginTestCaseListExcel = new Read_XLS(System.getProperty("user.dir")+"/src/com/APB/ExcelFiles/APBLoginPage.xls");
		///INDPayTransactionIdExcel= new Read_XLS(System.getProperty("user.dir")+"/src/com/INDUSIND/ExcelFiles/INDPayTransactionId.xls");
		//INDClientTestCaseListExcel = new Read_XLS(System.getProperty("user.dir")+"/src/com/INDUSIND/ExcelFiles/INDClientModule.xls");
		
		Add_Log.info("All Excel (Test Data) Files Initialized successfully.");
	}

	public void clickActionLink(WebElement actionColumn, String action) {
		WebElement actionLink = actionColumn.findElement(By.xpath("a[@title = '"+StringUtils.capitalize(action)+"']"));
		actionLink.click();
		
//		List<WebElement> links = actionColumn.findElements(By.xpath("a[@title = 'Selenium']"));
//		for (WebElement actionLink : links) {
//			String title = actionLink.getAttribute("title").toLowerCase();
//			Add_Log.info("Click on " + actionLink.getTagName() + " with title "
//					+ title);
//			if (title.equalsIgnoreCase(action)) {
//				actionLink.click();
//
//				WebElement editLink = driver
//						.findElement(By
//								.xpath("//*[@id='cmpnymstr-grid']/table/tbody/tr/td[7]/a[2]"));
//				editLink.click();
//			}
//		}
	}

	public void waitForProcessing(int defaultSleep) throws Exception {
		WebElement processing = getWebElement("apb.processing");
		WebDriverWait wait = new WebDriverWait(driver, defaultSleep);
		wait.until(ExpectedConditions.not(ExpectedConditions
				.visibilityOf(processing)));
	}
	

	
	public void PayDriver_initialization_DP() throws IOException {
		selectBrowser(Repository.getProperty("browserType"));

		System.out.println(Repository.getProperty("browserType"));
		impliciteWait(30);
		
	}
	//
}
