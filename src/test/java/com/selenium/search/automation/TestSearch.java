package com.selenium.search.automation;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestSearch {

	private WebDriver webDriver;
	private String url = "https://hq.iv.navvis.com/";
	String driverPath = "src/main/resources/geckodriver.exe";

	private static FirefoxProfile getFirefoxProfile(String profileName) {
		if (profileName == null || profileName.trim().isEmpty()) {
			return new FirefoxProfile();
		}
		return new ProfilesIni().getProfile(profileName);
	}

	@BeforeTest
	public void invokeBrowser() {

		try {
			System.setProperty("webdriver.gecko.driver", driverPath);

			FirefoxOptions firefoxOptions = new FirefoxOptions();
			firefoxOptions.setProfile(getFirefoxProfile("default"));
			firefoxOptions.setCapability("marionette", true);
			firefoxOptions.setAcceptInsecureCerts(true);

			// Capabilities capabilities = DesiredCapabilities.firefox();
			webDriver = new FirefoxDriver(firefoxOptions);

			webDriver.manage().deleteAllCookies();
			webDriver.manage().window().maximize();
			// driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			webDriver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

			// launch Firefox and redirect it to the URL
			webDriver.get(url);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void verifySearch() throws InterruptedException {

		WebDriverWait webDriverWait = new WebDriverWait(webDriver, 10);

		webDriver.findElement(By.cssSelector("input[type=search]")).sendKeys("IV");
		Thread.sleep(3000);

		webDriver.findElement(By.cssSelector("img[alt='Search']")).click();

		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.className("result-info-wrapper")));
		webDriver.findElement(By.className("result-info-wrapper")).click();

		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(text(),'5')]")));

	}

	@AfterTest(alwaysRun = true)
	public void closeBrowser() {

		webDriver.close();
		System.out.println("closed the browser");
	}
}
