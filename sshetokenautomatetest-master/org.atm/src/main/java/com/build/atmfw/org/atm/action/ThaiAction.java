package com.build.atmfw.org.atm.action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ThaiAction {

	private WebDriver Driver;
	public ThaiAction(WebDriver dv,String step,String description,String taction,String objecttype,String object,String data,String expect) {
		Driver=dv;
		SelectAction(step, description, taction, objecttype, object, data, expect);
	}
	private Boolean boolStatus=true;
	public Boolean SetBoolStatus() {
		return boolStatus;
	}
	private void SelectAction(String step,String description,String taction,String objecttype,String object,String data,String expect) {
		switch ((taction.toLowerCase())) {
		case "เปิดเวป":
			Driver.get(data);
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			boolStatus=true;
			break;
		case "ป้อนข้อมูล":
			Senttext(Driver,objecttype,object,data);
			boolStatus=true;
			break;
		default:
			break;
		}
	}

	private Boolean Senttext(WebDriver driver, String objecttype, String object, String data) {
		try {
			switch (objecttype) {
			case "id":
				driver.findElement(By.id(object)).sendKeys(data);
				break;
			case "name":
				driver.findElement(By.name(object)).sendKeys(data);
				break;
			case "xpath":
				WebElement we =	driver.findElement(By.xpath(object));
				we.sendKeys(data);
			break;

			default:
				break;
			}
			return true;
		} catch (Exception e) {
			
			System.out.println("error");
			return false;
		}
		
	}
}
