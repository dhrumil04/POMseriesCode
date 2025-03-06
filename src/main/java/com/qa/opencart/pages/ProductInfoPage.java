package com.qa.opencart.pages;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By productHeader = By.tagName("h1");
	private By productImages = By.cssSelector("ul.thumbnails img");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=1]/li");
	private By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=2]/li");
	private By quantity = By.id("input-quantity");
	private By addToCartBtn = By.id("button-cart");
	private By cartSuccessMsg = By.cssSelector("div.alert.alert-success");
	
	
	private Map<String, String> productInfoMap;
	

	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	public String getProductHeaderValue() {
		String productHeaderVal = eleUtil.doElementGetText(productHeader);
		System.out.println("Product Header Value :: "+productHeaderVal);
		return productHeaderVal;
	}
	
	public int getProductImagesCount() {
		int imagesCount = eleUtil.waitForElementsVisible(productImages, AppConstants.DEFAULT_MEDIUM_TIMEOUT).size();
		System.out.println("product images count :: "+imagesCount);
		return imagesCount;
	}
	
	public Map<String, String> getProductInfo() {		
		
//		productInfoMap = new HashMap<String, String>();
		
		//to maintain the ORDER we can use LinkedHashMap
		productInfoMap = new LinkedHashMap<String, String>();
		
		//to get it in SORTED ORDER on the basis of keys we can use TreeMap
//		productInfoMap = new TreeMap<String, String>();
		
		//header
		productInfoMap.put("productName", getProductHeaderValue());
		
		getProductMetaData();
		getProductPricingData();
		
		System.out.println(productInfoMap);
		return productInfoMap;
		
	}
	
	//fetching the metadata
	private void getProductMetaData() {

		// metaData:
		// Brand: Apple
		// Product Code: Product 18
		// Rewared Point: 800
		// Availability: In Stock
		List<WebElement> metaList = eleUtil.getElements(productMetaData);
		for (WebElement e : metaList) {
			String meta = e.getText(); // Brand: Apple
			String metaInfo[] = meta.split(":");
			String key = metaInfo[0].trim();
			String value = metaInfo[1].trim();
			productInfoMap.put(key, value);
		}

	}
	
	//fetching pricing data
	private void getProductPricingData() {
		// price:
		// $2,000.00 --> 0th location
		// Ex Tax: $2,000.00 --> 1st location
		List<WebElement> priceList = eleUtil.getElements(productPriceData);
		String price = priceList.get(0).getText();
		String exTax = priceList.get(1).getText();
		String exTaxVal = exTax.split(":")[1].trim();

		productInfoMap.put("productPrice", price);
		productInfoMap.put("productPrice", exTaxVal);
	}
	
	
	public void enterQuantity(int quantity) {
		System.out.println("Product Quantity is : "+quantity);
		//convert int to String
		eleUtil.doSendKeys(this.quantity, String.valueOf(quantity));
	}
	
	public String addProductToCart() {
		eleUtil.doClick(addToCartBtn);
		String successMsg = eleUtil.waitForElementVisible(cartSuccessMsg, AppConstants.DEFAULT_MEDIUM_TIMEOUT).getText();
		
		StringBuilder sb = new StringBuilder(successMsg);		
		String mesg = sb.substring(0, successMsg.length()-1).replace("/n", "").toString();
		System.out.println("Cart success msg : "+mesg);
		return mesg;
	}

}
