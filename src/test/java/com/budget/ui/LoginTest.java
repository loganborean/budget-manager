package com.budget.ui;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginTest {
    private WebDriver driver;
    private String baseUrl;
    
    
    @Before
    public void setUp() throws Exception {
        File file = new File("/home/logan/Desktop/chromedriver");
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        driver = new ChromeDriver();
        baseUrl = "localhost:8080";
    }

	@Test
	public void testLogin() {
	    driver.get(baseUrl);
	    driver.findElement(By.name("username")).sendKeys("loganborean");
	    driver.findElement(By.name("password")).sendKeys("Alskdjf5");
	    driver.findElement(By.name("submit")).click();
	    String titleText = driver.findElement(By.id("pagetitle")).getText();
	    assertTrue(titleText.toLowerCase().contains("monthly budget"));
	}


}
