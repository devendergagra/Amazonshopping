package org.com.project.controls;

import java.util.List;

import org.com.helper.WebdriverRefrence;
import org.com.project.utils.Log;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import junit.framework.Assert;

public class UiContols extends WebdriverRefrence {
	
	/***
	 * focusOnElement() - this method will move on element
	 * @param id - xpath path of the element
	 */
	public void focusOnElement(String xpath) {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(driver.findElementByXPath(xpath));
		} catch(Exception e) {
			Log.info("Element not found "+ xpath);
			Assert.fail();
		}
	}
	
	/******
	 * click() - this method will click on element
	 * @param xpath - xpath of the element 
	 */
	public void click(String xpath) {
		try {
			driver.findElementByXPath(xpath).click();
		} catch(Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	
	/*****
	 * enterInputDataUsingIdXpath() - this method will input the data
	 * @param xpath - xpath of the element
	 * @param data - data to enter
	 */
	public void enterInputDataUsingIdXpath(String xpath, String data) {
		try {
			MobileElement  element = (MobileElement) driver.findElementByXPath(xpath);
			element.clear();
			element.sendKeys(data);
		} catch(Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	/*****
	 * enterInputDataUsingIdXpath() - this method will input the data and press go button
	 * @param xpath - xpath of the element
	 * @param data - data to enter
	 */
	public void searchAndEnter(String xpath, String data) {
		try {
			MobileElement  element = (MobileElement) driver.findElementByXPath(xpath);
			element.clear();
			element.sendKeys(data);
			driver.executeScript("mobile:performEditorAction", ImmutableMap.of("action", "Go"));
		} catch(Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	
	/******
	 * waitForElementToDisplay()- this method will wait for element 30 seconds
	 * @param element - locator 
	 */
	public void waitForElementToDisplay(String xpath) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath(xpath)));
		} catch(Exception e) {
			e.printStackTrace();
			Assert.fail();
		 }
	}
	
	/*****
	 * verifyResults() - this method will assert text
	 * @param xpath - xpath of the element
	 * @param actual - actual string to verify
	 */
	public void verifyResults(String xpath, String actual) {
		try {
			MobileElement  element = (MobileElement) driver.findElementByXPath(xpath);
			String expected = element.getText();
			Assert.assertEquals(expected, actual);
			Reporter.log("Verification pass actual : "+actual +" and Expected : "+expected);
		} catch(Exception e) {
			e.printStackTrace();
			Assert.fail();
		 }
	}
	
	/***********
	 * verifyResultsIfContains - This method will verify partial records
	 * @param xpath - xpath of the element
	 * @param actual - actual string to verify
	 */
	public void verifyResultsIfContains(String xpath, String actual) {
		try {
			MobileElement  element = (MobileElement) driver.findElementByXPath(xpath);
			String value = element.getText();
			if(value.contains("LED...")) {
				value = value.replaceAll("LED...", "LED");
			}
			System.out.println(value);
			if(actual.contains(value)) {
				Assert.assertEquals(true, true);
				Reporter.log("verification passed and records are present : "+value);
			} else {
				Assert.assertEquals(true, false);
			}
		} catch(Exception e) {
			e.printStackTrace();
			Assert.fail();
		 }
	}
	
	/*****
	 * listSize() - This method will return number of elements present inside the list
	 * @param xpath - xpath of the element
	 * @return - size of the list
	 */
	public int listSize(String xpath) {
		try {
			List<MobileElement> element = driver.findElementsByXPath(xpath);
			return element.size();
		} catch(Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		return 0;
	}
	
	/**********
	 * verifyResult() - This method will verify text 
	 * @param xpath - xpath of the element 
	 * @param message - message to prinet
	 */
	public void verifyResult(String xpath, String message) {
		try {
			int size = listSize(xpath);
			if(size != 0) {
				Assert.assertEquals(true, true);
				Reporter.log(message+ " and present");
			}else {
				Reporter.log(message + " and not present");
				Assert.assertEquals(true, false);
			}
		} catch(Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	/***********
	 * listOfElementUsingXpath() - This method will return list of elements
	 * @param xpath - xpath of element
	 * @return - list of elements
	 */
	public List<MobileElement> listOfElementUsingXpath(String xpath) {
		try {
			return driver.findElementsByXPath(xpath);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/********
	 * click() - This method will click on element based on index 
	 * @param xpath - xpath of the element
	 * @param index - index number of the element
	 */
	public void click(String xpath, int index) {
		try {
			driver.findElementsByXPath(xpath).get(index).click();
		} catch(Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	/**********
	 * getTextOfElement() - This method will return element text
	 * @param xpath - xpath of the element
	 * @return - text/null
	 */
	public String getTextOfElement(String xpath) {
		try {
			return driver.findElementByXPath(xpath).getText();
		} catch(Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		return null;
	}
	
	/**********
	 * swipe()- This method will swipe page
	 */
	public void swipe() {
		try {
			int pressX = driver.manage().window().getSize().width/2;
			int bottomY = driver.manage().window().getSize().height/2;
			int topY = driver.manage().window().getSize().height/8;
			TouchAction touch = new TouchAction(driver);
			touch.longPress(PointOption.point(pressX, bottomY)).moveTo(PointOption.point(pressX, topY)).release().perform();
		} catch(Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	
	/************
	 * isListEmpnty() - This method will check for list of elements
	 * @param xpath - xpath of element
	 * @return - true/false
	 */
	public boolean isListEmpnty(String xpath) {
		boolean isElement = false;
		try{
			List<MobileElement> language = listOfElementUsingXpath(xpath);
			if(language.size() == 0) {
				isElement = true;
			}
		} catch(Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		return isElement;
	}
}
