package com.qa.opencart.tests;

import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

public class RegisterPageTest extends BaseTest{
		
	@BeforeClass
	public void regPageSetup() {
		registerPage = loginPage.navigateToRegisterPage();
	}
	
	
	public String getRandomEmail() {
		Random random = new Random();
//		String email = "naveen" + random.nextInt(1000) + "@gmail.com";
		
		String email = "naveen" + System.currentTimeMillis() + "@gmail.com";
		return email;
	}
	
	@DataProvider
	public Object[][] getRegTestData() {
		Object regArray[][] = ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
		return regArray;
	}
	
	
	@Test(dataProvider = "getRegTestData")
	public void userRegTest(String firstName, String lastName, 
							String telephone, String password, String subscribe) {
		Assert.assertTrue(registerPage.registerUser(firstName, lastName, getRandomEmail(),
							telephone, password, subscribe));
	}
	
}
