package org.com.helper;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.com.project.utils.ReadData;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;

public class LaunchApp extends WebdriverRefrence {
	
	ReadData readData = new ReadData();
	
	@BeforeClass
	public void setUp() throws IOException {
		Properties prop = readData.readPropertiesFile("C:\\Users\\Dell\\eclipse-workspace\\Appium\\Resource\\configuration.properties");
		String platform = prop.getProperty("platformName");
		if(platform.equals("Android")) {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("platformVersion", prop.getProperty("version")); 
			capabilities.setCapability("deviceName", prop.getProperty("deviceName"));
			capabilities.setCapability("platformName", prop.getProperty("platformName"));
			capabilities.setCapability("appPackage", prop.getProperty("appPackage"));
			capabilities.setCapability("appActivity", prop.getProperty("appActivity"));
			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, prop.getProperty("automationName"));
			URL url = new URL(prop.getProperty("hostName"));
			driver = new AppiumDriver<MobileElement>(url, capabilities);
		} else if(platform.equals("ios")) {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("browserName", prop.getProperty("iosBrowsername"));
			capabilities.setCapability("platformVersion", prop.getProperty("iosVersion")); 
			capabilities.setCapability("deviceName", "iosDeviceName");
			capabilities.setCapability("platformName", "iosPlatformName");
			capabilities.setCapability("appPackage", "appPackage");
			capabilities.setCapability("appActivity", "appActivity");
			URL url = new URL(prop.getProperty("hostName"));
			driver = new AppiumDriver<MobileElement>(url, capabilities);
		}
		driver.rotate(ScreenOrientation.PORTRAIT);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}
	
	@AfterClass
	 public void tearDown(){
		driver.quit();
	 }

}
