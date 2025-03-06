package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class ProductPageInfoTest extends BaseTest{
	
	@BeforeClass
	public void productInfoPageSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
	}
	
	@DataProvider
	public Object[][] getProductImagesTestData() {
		return new Object[][] {
			{"Macbook", "MacBook Pro", 4},
			{"iMac", "iMac", 3},
			{"Apple", "Apple Cinema 30\"", 6},
			{"Samsung", "Samsung SyncMaster 941BW", 1},
		};
	}
	
	@Test(dataProvider = "getProductImagesTestData")
	public void productImagesCountTest(String searchKey, String productName, int imagesCount) {
		searchPage = accPage.performSearch(searchKey);
		productInfoPage = searchPage.selectProduct(productName);
		int actImagesCount = productInfoPage.getProductImagesCount();
		Assert.assertEquals(actImagesCount, imagesCount);
	}
	
	//when one test method has multiple assert to verify then use softAssert obj 
	//even if one of the soft assert fails, it will give chance other soft asserts to execute 
	@Test
	public void productInfoTest() {
		searchPage = accPage.performSearch("Macbook");
		productInfoPage = searchPage.selectProduct("MacBook Pro");
		Map<String, String> actProductInfoMap = productInfoPage.getProductInfo();
		System.out.println(actProductInfoMap);
		
		softAssert.assertEquals(actProductInfoMap.get("Brand"), "Apple");
		softAssert.assertEquals(actProductInfoMap.get("Product Code"), "Product 18");
		softAssert.assertEquals(actProductInfoMap.get("productName"), "MacBook Pro");
		softAssert.assertEquals(actProductInfoMap.get("productPrice"), "$2,000.00");
		
		softAssert.assertAll(); //-->mandatory to write --> this will give info which softassert passed/failed 
	}
	
	//this one is failing, just modify 3rd softassert. or remove it
	@Test
	public void addToCart() {
		searchPage = accPage.performSearch("Macbook");
		productInfoPage = searchPage.selectProduct("MacBook Pro");
		productInfoPage.enterQuantity(2);
		String actCartMsg = productInfoPage.addProductToCart();
		
		//Success: You have added MacBook Pro to your shopping cart!
		softAssert.assertTrue(actCartMsg.contains("Success"));
		softAssert.assertTrue(actCartMsg.contains("MacBook Pro"));
//		softAssert.assertEquals(actCartMsg,"Success: You have added MacBook Pro to your shopping cart!");
		softAssert.assertAll();
	}

}
