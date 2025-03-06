package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.aspectj.util.FileUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;


import pom.qa.opencart.exception.FrameworkException;

public class DriverFactory {
	
	
	public WebDriver driver;
	public Properties prop;
	public OptionsManager optionsManager;
	
	public static String highlight;
	
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	
	/**
	 * this method is initializing the driver on basis of given browserName
	 * this returns the driver.
	 * 
	 * @param prop
	 * @return
	 */
	public WebDriver initDriver(Properties prop) {
		
		optionsManager = new OptionsManager(prop);
		
		highlight = prop.getProperty("highlight").trim();
		
		System.out.println("Browser name is "+ prop);
			
		String browserName = prop.getProperty("browser").toLowerCase().trim();
		
		if(browserName.trim().equalsIgnoreCase("chrome")) {
//			driver = new ChromeDriver(optionsManager.getChromeOptions());
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
		}
		else if(browserName.trim().equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver(optionsManager.getFireFoxOptions());
			tlDriver.set(new FirefoxDriver(optionsManager.getFireFoxOptions()));
		}
		else if(browserName.trim().equalsIgnoreCase("safari")) {
			driver = new SafariDriver();
		}
		else if(browserName.trim().equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
		}
		else {
			System.out.println("Please pass the right browser.. ");
		}
		
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		
		getDriver().get(prop.getProperty("url"));
		
		return getDriver();
		
	}
	
	
	//get local thread copy of driver
	public synchronized static WebDriver getDriver() {
		return tlDriver.get();
	}
	
	
	
	/**
	 * this method is reading the properties from the .properties file
	 * @return
	 */
	public Properties initProp() {
			
		//mvn clean install -Denv="stage"
		//mvn clean install
		prop = new Properties();
		FileInputStream ip = null;
		
		String envName = System.getProperty("env");
		System.out.println("Running Test cases on env : "+envName);
		
		
		try {
			
		if(envName == null) {
			System.out.println("No environment is passed .. Running tests on QA environment...");
			ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
		}
		
		else {
			switch (envName.toLowerCase().trim()) {
			case "qa":
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
				break;
			case "prod":
				ip = new FileInputStream("./src/test/resources/config/prod.config.properties");
				break;
			case "stag":
				ip = new FileInputStream("./src/test/resources/config/stag.config.properties");
				break;
			case "dev":
				ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
				break;
				
			default:
				System.out.println("..wrong env passed ..no need to run the test cases..");
				throw new FrameworkException("Wrong env passed .. this is custom exception..");
				//break;
			}
		}
		}catch(FileNotFoundException e) {
			
		}
		
		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return prop;
	}
	
	
	/**
	 * take screenshot
	 */
	public static String getScreenshot() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		try {
			FileUtil.copyFile(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
}

