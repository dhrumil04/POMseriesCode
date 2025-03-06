package com.qa.opencart.tests;

import static org.testng.Assert.assertTrue;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("EPIC - 100 : design login page test for opencart ")
@Story("story number : 465465 - Login page title test")
public class LoginPageTest extends BaseTest{
	
	@Severity(SeverityLevel.NORMAL)
	@Description("...getting the title of the page.. - dhrumil")
	@Test(priority = 1)
	public void loginPageTitleTest() {
		
		String actTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle, "Account Login");
		
	}
	
	
	@Test(priority = 2)
	public void loginPageURLTest() {
		
		String actURL = loginPage.getLoginPageURL();
		Assert.assertTrue(actURL.contains("route=account/login"));
		
	}
	
	
	@Test(priority = 3)
	public void forgotPwdLinkExistTest() {

		Assert.assertTrue(loginPage.isForgotPasswordLinkExist());
		
	}
	
	
	@Test(priority = 4)
	public void loginTest() {
		
		accPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
		assertTrue(accPage.isLogoutLinkExist());
		
	}

}
