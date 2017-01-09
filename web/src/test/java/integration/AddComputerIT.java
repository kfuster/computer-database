package integration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

public class AddComputerIT {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "http://localhost:8181";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testAddComputerIT() throws Exception {
        driver.get(baseUrl + "/addComputer");
        driver.findElement(By.cssSelector("input.btn.btn-primary")).click();
        driver.findElement(By.id("main")).click();
        driver.findElement(By.id("introduced")).click();
        driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/table/tbody/tr[2]/td[5]")).click();
        driver.findElement(By.id("introduced")).clear();
        driver.findElement(By.id("introduced")).sendKeys("1968-11-10");
        driver.findElement(By.id("discontinued")).clear();
        driver.findElement(By.id("discontinued")).sendKeys("24-05-2012");
        driver.findElement(By.cssSelector("input.btn.btn-primary")).click();
        driver.findElement(By.id("computerName")).clear();
        driver.findElement(By.id("computerName")).sendKeys("Test");
        driver.findElement(By.cssSelector("input.btn.btn-primary")).click();
        driver.findElement(By.cssSelector("div.row")).click();
        driver.findElement(By.id("introduced")).click();
        driver.findElement(By.id("discontinued")).click();
        driver.findElement(By.linkText("23")).click();
        driver.findElement(By.cssSelector("input.btn.btn-primary")).click();
        driver.findElement(By.id("introduced")).clear();
        driver.findElement(By.id("introduced")).sendKeys("2068-11-10");
        driver.findElement(By.cssSelector("body")).click();
        driver.findElement(By.cssSelector("input.btn.btn-primary")).click();
        driver.findElement(By.id("discontinued")).click();
        driver.findElement(By.id("discontinued")).clear();
        driver.findElement(By.id("discontinued")).sendKeys("2076-11-23");
        driver.findElement(By.cssSelector("div.row")).click();
        driver.findElement(By.id("computerName")).clear();
        driver.findElement(By.id("computerName")).sendKeys("Te");
        driver.findElement(By.id("computerName")).click();
        driver.findElement(By.id("computerName")).clear();
        driver.findElement(By.id("computerName")).sendKeys("Test");
        driver.findElement(By.cssSelector("input.btn.btn-primary")).click();
        driver.findElement(By.cssSelector("a.navbar-brand")).click();
        driver.findElement(By.linkText("Cancel")).click();
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