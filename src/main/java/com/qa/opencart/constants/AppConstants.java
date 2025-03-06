package com.qa.opencart.constants;

import java.util.Arrays;
import java.util.List;

public class AppConstants {
	
	public static final int DEFAULT_MEDIUM_TIMEOUT = 10;
	public static final int DEFAULT_SHORT_TIMEOUT = 5;
	public static final int DEFAULT_LONG_TIMEOUT = 15;
	
	public static final String LOGIN_PAGE_TITLE_VALUE = "Account Login";
	
	public static final int ACCOUNTS_PAGE_HEADERS_COUNT = 4;
	public static final List<String> EXPECTED_ACCOUNTS_PAGE_HEADERS_LIST = Arrays.asList("My Account", "My Orders",
																					"My Affiliate Account", "Newsletter");
	
	public static final String USER_REGISTRATION_SUCC_MSG = "Your Account Has Been Created";
	
	//excel sheet name
	public static final String REGISTER_SHEET_NAME = "register";
	
	
	//test data : excel
	//constants : final static in java
	//env data : properties (browser, headless mode, )
}
