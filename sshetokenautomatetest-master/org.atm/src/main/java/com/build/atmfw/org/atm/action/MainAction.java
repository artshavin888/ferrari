package com.build.atmfw.org.atm.action;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import okio.Timeout;

import com.build.atmfw.org.atm.utilities.TimeUtil;

public class MainAction {

	public MainAction() {
		// TODO Auto-generated constructor stub
	}

	private WebDriver Driver;
	int i = 0;
	private Boolean boolStatus = true;
	@SuppressWarnings("unused")
	private String tmpstep, tmpdescription, tmpaction, tmpobjecttype, tmpobject, tmpdata, tmpexpect = null;

	public void CallAction(WebDriver dv, String step, String description, String taction, String objecttype,
			String object, String data, String expect) {
		Driver = dv;
		// CheckIteration(data,expect);
		// System.out.println(step+" :"+description+" :"+taction+" :"+objecttype+"
		// :"+object+" :"+data +" :"+expect);
		SelectAction(step, description, taction, objecttype, object, data, expect);
		// if (boolStatus.equals(false)) {
		// CaptureElementImg(objecttype, object);
		// }

	}

	private void SelectAction(String step, String description, String taction, String objecttype, String object,
			String data, String expect) {
		switch (taction.toLowerCase()) {
		case "start web":
			Driver.get(data);
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			boolStatus = true;
			break;
		case "click":
			boolStatus = ClickButton(Driver, objecttype, object);
			break;
		case "sent text":
			boolStatus = Senttext(Driver, objecttype, object, data);
			break;
		case "check text":
			boolStatus = checktext(Driver, objecttype, object, expect);
			break;
		case "check value text":
			boolStatus = checkvaluetext(Driver, objecttype, object, expect);
			break;
		case "sent read only text":
			boolStatus = senttextRO(Driver, objecttype, object, data);
			break;
		case "sent enter key":
			SendkeyEnter();
			boolStatus = true;

			break;
		case "wait":
			Wait(Integer.parseInt(data));
			boolStatus = true;
			break;

		case "wait for element":
			waitforElement(Driver, objecttype, object, data);
			boolStatus = true;
			break;

		case "comment":
			Commenttest(TimeUtil.GetDateTime() + "   " + data);
			boolStatus = true;
			break;
		case "sent up arrow key":
			SendkeyArrow(data, "up");
			boolStatus = true;
			break;
		case "sent down arrow key":
			SendkeyArrow(data, "down");
			boolStatus = true;
			break;
		case "sent right arrow key":
			SendkeyArrow(data, "right");
			boolStatus = true;
			break;
		case "sent left arrow key":
			SendkeyArrow(data, "down");
			boolStatus = true;
			break;
		case "sent tab key":
			SendkeyTab(data);
			boolStatus = true;
			break;
		case "forceclick":

			boolStatus = Forceclick(Driver, objecttype, object);

			break;
		case "assignvalue":
			AssignValue(data);
			break;
		case "gettextvalue":
			break;
		case "capture":
			CaptureElementImg(Driver, objecttype, object);
			break;
		case "capturescreen":
			CaptureScreenImg(Driver);
			break;
		case "getpos":
			GetPostion(Driver, objecttype, object);
			break;
		case "select dropdown":
			SelectDropdown(Driver, objecttype, object, data);
			break;
		case "close test":
			Driver.close();
			break;
		case "scroll to":
			Scrollto(Driver, objecttype, object, data);
			break;
		default:
			ThaiAction tAction = new ThaiAction(Driver, step, description, taction, objecttype, object, data, expect);
			boolStatus = tAction.SetBoolStatus();
			System.out.println(boolStatus);
			break;
		}
	}

	public String GetResult() {
		String result = "";
		if (boolStatus.equals(true)) {
			result = "Passed";
		} else if (boolStatus.equals(false)) {
			result = "Failed";
		} else {
			result = "Done";
		}
		return result;
	}

	private Boolean checkvaluetext(WebDriver driver, String objecttype, String object, String expect) {
		String textget = null;
		try {
			switch (objecttype) {
			case "id":
				textget = driver.findElement(By.id(object)).getAttribute("value");
				break;
			case "name":
				textget = driver.findElement(By.name(object)).getAttribute("value");
				;
				break;
			case "xpath":
				WebElement we = driver.findElement(By.xpath(object));
				textget = we.getAttribute("value");
				break;
			default:
				break;
			}

			System.out.println("================= ()()()" + textget + "()()()================");
			if (textget.equals(expect)) {
				System.out.println("Passed");
				return true;
			} else {
				System.out.println("Failed");
				return false;
			}

		} catch (Exception e) {
			System.out.println("================= ()()()" + textget + "()()()================");
			System.out.println("error");
			return false;
		}

	}

	private void Commenttest(String data) {
		System.out.println(data);

	}

	public double roundAvoid(double value, int places) {
		double scale = Math.pow(10, places);
		return Math.round(value * scale) / scale;
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
				WebElement we = driver.findElement(By.xpath(object));
				we.sendKeys(data);
				break;

			default:
				break;
			}
			return true;
		} catch (Exception e) {

			System.out.println("error: " + e);
			return false;
		}

	}

	private Boolean Scrollto(WebDriver driver, String objecttype, String object, String data) {
		try {
			WebElement webelement = null;
			switch (objecttype) {
			case "id":
				webelement = driver.findElement(By.id(object));
				break;
			case "name":
				webelement = driver.findElement(By.name(object));
				break;
			case "xpath":
				webelement = driver.findElement(By.xpath(object));
				webelement.sendKeys(data);
				break;

			default:
				break;
			}
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", webelement);
			return true;
		} catch (Exception e) {

			System.out.println("error: " + e);
			return false;
		}

	}

	private Boolean ClickButton(WebDriver driver, String objecttype, String object) {
		try {
			switch (objecttype) {
			case "id":
				driver.findElement(By.id(object)).click();
				;
				break;
			case "name":
				driver.findElement(By.name(object)).click();
				;
				break;
			case "xpath":
				WebElement we = driver.findElement(By.xpath(object));
				we.click();
				break;

			default:
				break;
			}
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}

	}

	private Boolean senttextRO(WebDriver driver, String objecttype, String object, String stxt) {

		try {
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

			WebElement searchbox = null;
			switch (objecttype) {
			case "id":
				searchbox = driver.findElement(By.id(object));
				break;
			case "name":
				searchbox = driver.findElement(By.name(object));
				break;
			case "xpath":
				searchbox = driver.findElement(By.xpath(object));
				break;
			default:
				break;
			}

			JavascriptExecutor myExecutor = ((JavascriptExecutor) driver);
			myExecutor.executeScript("arguments[0].value='" + stxt + "';", searchbox);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			return true;
		} catch (Exception e) {

			System.out.println(e.toString());
			return false;
		}
	}

	private Boolean checktext(WebDriver driver, String objecttype, String object, String expect) {
		String textget = null;
		try {
			switch (objecttype) {
			case "id":
				textget = driver.findElement(By.id(object)).getText();
				break;
			case "name":
				textget = driver.findElement(By.name(object)).getText();
				break;
			case "xpath":
				WebElement we = driver.findElement(By.xpath(object));
				textget = we.getText();
				break;
			default:
				break;
			}

			System.out.println("================= ()()()" + textget + "()()()================");
			if (textget.equals(expect)) {
				System.out.println("Passed");
				return true;
			} else {
				System.out.println("Failed");
				return false;
			}

		} catch (Exception e) {
			System.out.println("================= ()()()" + textget + "()()()================");
			System.out.println("error");
			return false;
		}

	}

	private void SendkeyEnter() {
		Robot rb;
		try {
			rb = new Robot();
			rb.keyPress(KeyEvent.VK_ENTER);
			rb.keyRelease(KeyEvent.VK_ENTER);

		} catch (AWTException e) {

			e.printStackTrace();
		}

	}

	private void SendkeyTab(String data) {
		Robot rb;
		try {
			int i = Integer.parseInt(data);
			rb = new Robot();
			for (int j = 1; j <= i; j++) {
				rb.keyPress(KeyEvent.VK_TAB);
				rb.keyRelease(KeyEvent.VK_TAB);
			}

		} catch (AWTException e) {

			e.printStackTrace();
		}

	}

	private void SendkeyArrow(String data, String arrow) {
		Robot rb;
		try {
			rb = new Robot();
			int iCount = Integer.parseInt(data);
			switch (arrow) {
			case "up":
				for (int i = 1; i <= iCount; i++) {
					rb.keyPress(KeyEvent.VK_UP);
					rb.keyRelease(KeyEvent.VK_UP);
				}

				break;
			case "down":
				for (int i = 1; i <= iCount; i++) {
					rb.keyPress(KeyEvent.VK_DOWN);
					rb.keyRelease(KeyEvent.VK_DOWN);
				}

				break;
			case "right":
				for (int i = 1; i <= iCount; i++) {
					rb.keyPress(KeyEvent.VK_RIGHT);
					rb.keyRelease(KeyEvent.VK_RIGHT);
				}

				break;
			case "left":
				for (int i = 1; i <= iCount; i++) {
					rb.keyPress(KeyEvent.VK_LEFT);
					rb.keyRelease(KeyEvent.VK_LEFT);
				}

				break;
			default:
				break;
			}

		} catch (AWTException e) {

			e.printStackTrace();
		}
	}

	public int rndBetween(int min, int max) {
		Random rn = new Random();
		int range = max - min + 1;
		return (rn.nextInt(range) + 1);

	}

	public void waitforElement(WebDriver driver, String objecttype, String object, String data) {
		int timeout = Integer.parseInt(data);
			boolean chkstatus = false;
			boolean chk=true;
			int i=0;

			while (chkstatus==false) {
				if (objecttype=="id") {
					try {
						i++;
						Thread.sleep(10000);
						System.out.println(objecttype);
						String q = driver.findElement(By.id(object));
						System.out.println(q);
						chk=true;
						
					System.out.println("id_status:"+chkstatus+"  "+"loop:"+i);
					} catch (Exception e) {
						chkstatus =false;
						chk=false;
					}
				
				
				}else if (objecttype=="name") {
					try {
						i++;
						Thread.sleep(10000);
						System.out.println(objecttype);
						String r = driver.findElement(By.name(object));
						System.out.println(r);
						chk=true;
						
					System.out.println("name_status:"+chkstatus+"  "+"loop:"+i);
					} catch (Exception e) {
						chkstatus =false;
						chk=false;
					}
					
				}else {
					try {
						i++;
						Thread.sleep(10000);
						System.out.println(objecttype);
						String s =driver.findElement(By.xpath(object));
						System.out.println(s);
						chk=true;
						
					System.out.println("xpath_status:"+chkstatus+"  "+"loop:"+i);
					} catch (Exception e) {
						chkstatus =false;
						chk=false;
					}

				
				if (chk==true) {
					chkstatus=true;
					System.out.println("Elementfind:"+chkstatus+"  "+i);
				}	
				if (i==timeout) {
					chkstatus=true;
					System.out.println("Elementtimeout:"+chkstatus+"  "+i);
				}
				
			}
		}
			
	}

	public void Wait(int MS) {
		try {

			int txtsc = MS / 1000;

			System.out.println("Please wait!!!!!!!!!! " + txtsc + " Second");
			Thread.sleep(MS);
			// System.out.println("Finishing wait!!!!");
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	private Point GetPostion(WebDriver driver, String objecttype, String object) {
		Point textget = null;
		switch (objecttype) {
		case "id":
			textget = driver.findElement(By.id(object)).getRect().getPoint();
			break;
		case "name":
			textget = driver.findElement(By.name(object)).getRect().getPoint();
			break;
		case "xpath":
			WebElement we = driver.findElement(By.xpath(object));
			textget = we.getRect().getPoint();
			break;
		default:
			break;
		}
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@  " + textget);
		System.out.println("-0-0-0-0-0-0-0-0-0-0-0-0-0-" + textget);
		return textget;
	}

	private Boolean Forceclick(WebDriver driver, String objecttype, String object) {
		WebElement webElement = null;
		try {
			switch (objecttype) {
			case "id":
				webElement = driver.findElement(By.id(object));
				break;
			case "name":
				webElement = driver.findElement(By.name(object));
				break;
			case "xpath":
				webElement = driver.findElement(By.xpath(object));
				break;
			default:
				break;
			}

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", webElement);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", webElement);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	private void CaptureElementImg(WebDriver driver, String objecttype, String object) {

		WebElement ele = null;

		switch (objecttype) {
		case "id":
			ele = driver.findElement(By.id(object));
			break;
		case "xpaht":
			ele = driver.findElement(By.xpath(object));
			break;
		case "name":
			ele = driver.findElement(By.name(object));
			break;
		default:
			break;
		}
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		BufferedImage fullImg;
		try {
			fullImg = ImageIO.read(screenshot);
			// Get the location of element on the page
			Point point = ele.getLocation();

			// Get width and height of the element
			int eleWidth = ele.getSize().getWidth() + 200;
			int eleHeight = ele.getSize().getHeight() + 200;

			// Crop the entire page screenshot to get only element screenshot
			BufferedImage eleScreenshot = fullImg.getSubimage(point.getX() - 100, point.getY() - 50, eleWidth,
					eleHeight);
			ImageIO.write(eleScreenshot, "png", screenshot);

			// Copy the element screenshot to disk
			File screenshotLocation = new File("D:\\testImg\\" + TimeUtil.GetNameDateTime() + ".png");
			FileUtils.copyFile(screenshot, screenshotLocation);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void CaptureElementImg(String objecttype, String object) {

		WebElement ele = null;

		switch (objecttype) {
		case "id":
			ele = Driver.findElement(By.id(object));
			break;
		case "xpaht":
			ele = Driver.findElement(By.xpath(object));
			break;
		case "name":
			ele = Driver.findElement(By.name(object));
			break;
		default:
			break;
		}
		((JavascriptExecutor) Driver).executeScript("arguments[0].scrollIntoView(true);", ele);
		File screenshot = ((TakesScreenshot) Driver).getScreenshotAs(OutputType.FILE);
		BufferedImage fullImg;
		try {
			fullImg = ImageIO.read(screenshot);
			// Get the location of element on the page
			Point point = ele.getLocation();

			// Get width and height of the element
			int eleWidth = ele.getSize().getWidth() + 200;
			int eleHeight = ele.getSize().getHeight() + 200;

			// Crop the entire page screenshot to get only element screenshot
			BufferedImage eleScreenshot = fullImg.getSubimage(point.getX() - 100, point.getY() - 50, eleWidth,
					eleHeight);
			ImageIO.write(eleScreenshot, "png", screenshot);

			// Copy the element screenshot to disk
			File screenshotLocation = new File("D:\\testImg\\" + TimeUtil.GetNameDateTime() + ".png");
			FileUtils.copyFile(screenshot, screenshotLocation);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	private Boolean SelectDropdown(WebDriver driver, String objecttype, String object, String data) {
		WebElement ele = null;
		switch (objecttype) {
		case "id":
			ele = driver.findElement(By.id(object));
			break;
		case "name":
			ele = driver.findElement(By.name(object));
			break;
		case "xpath":

			ele = driver.findElement(By.xpath(object));
			break;
		default:
			break;
		}

		Select select = new Select(ele);
		select.selectByVisibleText(data);
		return true;
	}

	private void CaptureScreenImg(WebDriver driver) {

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File("D:\\testImg\\Scr_" + TimeUtil.GetNameDateTime() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void CaptureScreenImg() {

		File scrFile = ((TakesScreenshot) Driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File("D:\\testImg\\Scr_" + TimeUtil.GetNameDateTime() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private Hashtable<String, String> assgValue = new Hashtable<String, String>();

	private void AssignValue(String data) {
		String[] splTxt;
		String txtchange;
		txtchange = data.replace("|>", ",");
		splTxt = txtchange.split(",");
		assgValue.put(splTxt[0], splTxt[1]);
		System.out.println(assgValue.get(splTxt[0]));
	}

	public String GetValue(String data) {
		// System.out.println(data);

		return assgValue.get(data);
	}

	public String SetValue(String data) {
		String[] splTxt;
		String txtchange;
		txtchange = data.replace("|>", ",");
		splTxt = txtchange.split(",");
		assgValue.put(splTxt[0], splTxt[1]);
		return splTxt[1];
	}

}
