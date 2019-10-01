package com.build.atmfw.org.atm.device;

import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import com.build.atmfw.org.atm.utilities.Utilites;


public class SetDevice {

	public SetDevice() {
		
	}
	private WebDriver driver;
	private Utilites ut;
	public void setchromeDv() {
	ut =new Utilites();
	ut.killProcess("-");
	System.setProperty("webdriver.chrome.driver", "D:\\ATMdriver\\chrome74\\chromedriver.exe");
	Utilites.writelog("====== Start Test =======");
		ChromeOptions chromeOption = new ChromeOptions();
//		chromeOption.addArguments("--headless","--disable-gpu","--ignore-certificate-errors", "--silent");

		
		//Setting Download PDF files instead of automatically opening them in Chrome
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromeOption.setExperimentalOption("prefs", chromePrefs);
		//disable flash and the PDF viewer
		chromePrefs.put("plugins.always_open_pdf_externally", true);
		
		driver = new ChromeDriver(chromeOption);
		driver.manage().window().maximize();
//		System.out.println(TimeUtil.GetDateTime()+ " ======Start Chromedriver=======");
		Utilites.writelog("======= Start Chromedriver =======");
		
		
	}
	public WebDriver getChromeDiver() {
		return driver;
	}
	protected void finalize ()  {
		Utilites.writelog("======= Closed test =======");
		driver.close();
		driver.quit();
		ut.killProcess("-");
		
		
    }



}
