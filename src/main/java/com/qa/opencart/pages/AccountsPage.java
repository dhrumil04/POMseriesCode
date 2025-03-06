package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class AccountsPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	//1. by locators
	private By logoutLink = By.linkText("Logout");
	private By accsHeaders = By.cssSelector("div#content h2");
	private By search = By.name("search");
	private By searchIcon = By.cssSelector("#search button");
	
	
	//2. constructor with driver
	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	//3. actions/method
	public String getAccountsPageTitle() {
		String title = driver.getTitle();
		System.out.println("Acc page title : "+title);
		return title;
	}
	
	public String getAccountsPageURL() {
		String url = driver.getCurrentUrl();
		System.out.println("Acc page url : "+url);
		return url;
	} 
	
	public boolean isLogoutLinkExist() {
//		return driver.findElement(logoutLink).isDisplayed();
		return eleUtil.waitForElementVisible(logoutLink, AppConstants.DEFAULT_SHORT_TIMEOUT).isDisplayed();
	}
	
	public boolean isSearchExist() {
//		return driver.findElement(search).isDisplayed();
		return eleUtil.waitForElementVisible(search, AppConstants.DEFAULT_SHORT_TIMEOUT).isDisplayed();

	}
	
	public List<String> getAccountsPageHeaders() {
		
		List<WebElement> accHeadersList = driver.findElements(accsHeaders);
		List<String> accHeadersValList = new ArrayList<>();
		
		for(WebElement e : accHeadersList) {
			String text = e.getText();
			accHeadersValList.add(text);
		}
		
		return accHeadersValList;
	}
	
	public SearchPage performSearch(String searchKey) {
		if(isSearchExist()) {
			eleUtil.doSendKeys(search, searchKey);
			eleUtil.doClick(searchIcon);
			return new SearchPage(driver);
		}
		else {
			System.out.println("Search field is not present on the page ");
			return null;
		}
	}

}
