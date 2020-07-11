package org.com.project.testcase;

import java.util.List;

import org.com.helper.LaunchApp;
import org.com.project.controls.UiContols;
import org.com.project.utils.Constant;
import org.com.project.utils.Locator;
import org.testng.Reporter;
import org.testng.annotations.Test;

import io.appium.java_client.MobileElement;

public class AmazonProductOrder extends LaunchApp {

	UiContols control = new UiContols();
    
	@Test
	public void amzonOrder() {
		String productDetail = null;
		String descptnStock = null;
		String shipping = null;
		
		selectLanguageEnglish();
		skipLoginAndClickOnRemindMeButton();
		
		//searching for item, after search it takes time to load the content, so added static wait
		control.waitForElementToDisplay(Locator.search_box);
		control.searchAndEnter(Locator.search_box, Constant.ITEM_TO_SEARCH);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//clicking on product except first and last
		List<MobileElement> list = control.listOfElementUsingXpath(Locator.all_products);
		for(int product = 0; product < list.size(); product++) {
			if(product != 1 || product != list.size()) {
				if(list.get(product).getText().contains(Constant.PRODUCT_TO_SHOP)) {
					productDetail = list.get(product).getText();
					System.out.println(productDetail);
					control.swipe();
					list.get(product).click();
					break;
				}
			}
		}
		
		Reporter.log("*************Detail Page Verification********************");
		//product name
		control.waitForElementToDisplay(Locator.product_name.replace("$", productDetail));
		control.verifyResult(Locator.product_name.replace("$", productDetail), "Product name verified on detail page: "+productDetail);
		control.swipe();
		
		//getting price
		String price = control.getTextOfElement(Locator.price_detail);
		String priceDetail[] = price.split("\\s");
		
		//adding product to cart		
		for(int addCartButton = 0; addCartButton < 4; addCartButton++) {
			control.swipe();
			if(addCartButton == 2) {
				//item eligibility
				descptnStock = control.getTextOfElement(Locator.product_availablity);
				descptnStock.replace(".", "").trim();
				System.out.println(descptnStock);
			}
		} 
		
		//delivery type
		shipping = control.getTextOfElement(Locator.delivery_type);
		String freeShipping[] = shipping.split("\\+");
		String shippingDetail = Constant.PRODUCT_ELIGIBILITY+freeShipping[1];
		
		control.click(Locator.add_to_cart_button);
		List<MobileElement> done = control.listOfElementUsingXpath(Locator.popup_done_button);
		if(done.size() != 0) {
			done.get(0).click();
		}
		
		//clicking on cart
		control.click(Locator.cart_button);
		
		Reporter.log("*************Cart Page Verification********************");
		
		control.waitForElementToDisplay(Locator.cart_product_price);
		control.swipe();
		
		//product name
		control.verifyResultsIfContains(Locator.cart_product_name, productDetail);
		
		//Description
		control.verifyResultsIfContains(Locator.cart_product_stock, descptnStock);
		control.verifyResultsIfContains(Locator.cart_product_shipping_detail, shippingDetail);
			
		//price
		//control.verifyResultsIfContains(Locator.product_price, priceDetail[1]);
	}
	
	private void selectLanguageEnglish() {
		control.click(Locator.main_page);
		control.waitForElementToDisplay(Locator.english_image);
		control.click(Locator.english_image);
		if(control.isListEmpnty(Locator.sign_in_text) == true); {
			control.focusOnElement(Locator.arrow_image);
			control.click(Locator.arrow_image);
			control.waitForElementToDisplay(Locator.english_image);
			control.click(Locator.english_image);
		}
		control.verifyResults(Locator.sign_in_page_text, Constant.SIGN_IN_PAGE_TEXT);
	}
	
	private void skipLoginAndClickOnRemindMeButton() {
		//Login page screenshot not coming giving an error, so i just skipped
		control.click(Locator.skip_button);
				
		//It will click if remind me pop up will appear
		if((control.listSize(Locator.remind_me_popup_button) != 0)) {
			control.click(Locator.remind_me_popup_button);
		}
	}
}

