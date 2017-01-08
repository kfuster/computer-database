package integration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

public class DashboardIT {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        //System.setProperty("webdriver.gecko.driver","C:\\Program Files\\Selenium\\geckodriver.exe");
        driver = new FirefoxDriver();
        baseUrl = "http://localhost:8181";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testDashboard() throws Exception {
        driver.get(baseUrl + "/dashboard");
        driver.findElement(By.linkText("»")).click();
        driver.findElement(By.linkText("4")).click();
        driver.findElement(By.xpath("(//a[contains(text(),'...')])[2]")).click();
        driver.findElement(By.linkText("59")).click();
        driver.findElement(By.linkText("«")).click();
        driver.findElement(By.linkText("1")).click();
        driver.findElement(By.linkText("59")).click();
        driver.findElement(By.xpath("(//button[@type='button'])[2]")).click();
        driver.findElement(By.xpath("(//button[@type='button'])[3]")).click();
        driver.findElement(By.cssSelector("button.btn.btn-default")).click();
        driver.findElement(By.id("searchbox")).clear();
        driver.findElement(By.id("searchbox")).sendKeys("mac");
        driver.findElement(By.id("searchsubmit")).click();
        driver.findElement(By.linkText("2")).click();
        driver.findElement(By.linkText("10")).click();
        driver.findElement(By.xpath("(//button[@type='button'])[2]")).click();
        driver.findElement(By.xpath("(//button[@type='button'])[3]")).click();
        driver.findElement(By.cssSelector("button.btn.btn-default")).click();
        driver.findElement(By.id("addComputer")).click();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}