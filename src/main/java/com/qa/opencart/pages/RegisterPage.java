package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class RegisterPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;

	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirmpassword = By.id("input-confirm");

	private By agreeCheckBox = By.name("agree");
	private By continueButton = By.xpath("//input[@type='submit' and @value='Continue']");

	private By subscribeYes = By.xpath("//label[normalize-space()='Yes']/input[@type='radio']");
	private By subscribeNo = By.xpath("//label[normalize-space()='No']/input[@type='radio']");

	private By registerSuccessMesg = By.cssSelector("div#content h1");

	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");
	
	
	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	
	public boolean registerUser(String firstName, String lastName, String email, 
							String telephone, String password, String subscribe) {
		
		eleUtil.waitForElementVisible(this.firstName, AppConstants.DEFAULT_SHORT_TIMEOUT).sendKeys(firstName);
		eleUtil.doSendKeys(this.lastName, lastName);
		eleUtil.doSendKeys(this.email, email);
		eleUtil.doSendKeys(this.telephone, telephone);
		eleUtil.doSendKeys(this.password, password);
		eleUtil.doSendKeys(this.confirmpassword, password);
		
		
		if(subscribe.equalsIgnoreCase("yes")) {
			eleUtil.doClick(subscribeYes);
		}
		else {
			eleUtil.doClick(subscribeNo);
		}
		
//		eleUtil.doClick(agreeCheckBox);
		eleUtil.doActionsClick(agreeCheckBox);
		eleUtil.doClick(continueButton);
		
		String succMsg = eleUtil.waitForElementVisible(registerSuccessMesg, AppConstants.DEFAULT_MEDIUM_TIMEOUT).getText();
		System.out.println("User registration success msg : "+succMsg);
		
		if(succMsg.contains(AppConstants.USER_REGISTRATION_SUCC_MSG)) {
			
			eleUtil.doClick(logoutLink);
			eleUtil.doClick(registerLink);
			
			return true;
		}
		return false;
		
	}
}
