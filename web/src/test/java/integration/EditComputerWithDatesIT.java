package integration;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class EditComputerWithDatesIT {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private static final int SLEEP = 3000;

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "http://localhost:8181";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testBcd() throws Exception {
        driver.get(baseUrl + "/dashboard");
        Thread.sleep(SLEEP);

        driver.findElement(By.linkText("Apple IIe")).click();
        Thread.sleep(SLEEP);
        assertEquals(driver.findElement(By.xpath("//h1")).getText(), "Edit Computer");

        driver.findElement(By.id("introduced")).click();
        driver.findElement(By.linkText("2")).click();
        Thread.sleep(SLEEP);
        assertTrue(!driver.findElement(By.id("introduced")).getAttribute("value").isEmpty());

        driver.findElement(By.id("discontinued")).click();
        driver.findElement(By.linkText("1")).click();
        Thread.sleep(SLEEP);
        assertTrue(!driver.findElement(By.id("discontinued")).getAttribute("value").isEmpty());

        driver.findElement(By.cssSelector("input.btn.btn-primary")).click();
        Thread.sleep(SLEEP);
        assertEquals(driver.findElement(By.id("discontinued.errors")).getText(), "Discontinuation date must be after introduction date.");

        driver.findElement(By.id("introduced")).click();
        driver.findElement(By.id("introduced")).clear();
        driver.findElement(By.id("introduced")).sendKeys("01-02-2");
        Thread.sleep(SLEEP);
        assertTrue(!driver.findElement(By.id("introduced")).getAttribute("value").isEmpty());

        driver.findElement(By.cssSelector("div.row")).click();
        Thread.sleep(SLEEP);

        driver.findElement(By.cssSelector("input.btn.btn-primary")).click();
        Thread.sleep(SLEEP);
        assertEquals(driver.findElement(By.id("introduced.errors")).getText(), "Enter the date in the mm-dd-yyyy format.");

        driver.findElement(By.id("discontinued")).click();
        driver.findElement(By.id("discontinued")).clear();
        driver.findElement(By.id("discontinued")).sendKeys("01-01-2");
        Thread.sleep(SLEEP);
        assertTrue(!driver.findElement(By.id("discontinued")).getAttribute("value").isEmpty());

        driver.findElement(By.cssSelector("input.btn.btn-primary")).click();
        Thread.sleep(SLEEP);
        assertEquals(driver.findElement(By.id("introduced.errors")).getText(), "Enter the date in the mm-dd-yyyy format.");
        assertEquals(driver.findElement(By.id("discontinued.errors")).getText(), "Enter the date in the mm-dd-yyyy format.");

        driver.findElement(By.id("introduced")).click();
        driver.findElement(By.linkText("3")).click();
        Thread.sleep(SLEEP);
        assertTrue(!driver.findElement(By.id("introduced")).getAttribute("value").isEmpty());

        driver.findElement(By.id("discontinued")).click();
        driver.findElement(By.linkText("19")).click();
        Thread.sleep(SLEEP);
        assertTrue(!driver.findElement(By.id("discontinued")).getAttribute("value").isEmpty());

        new Select(driver.findElement(By.id("companyId"))).selectByVisibleText("Sony");
        Thread.sleep(SLEEP);
        assertEquals(driver.findElement(By.id("companyId")).getAttribute("value"),"17");

        driver.findElement(By.cssSelector("input.btn.btn-primary")).click();
        Thread.sleep(SLEEP);
        assertTrue(driver.findElement(By.xpath("//h1")).getText().endsWith("Computers found"));
        assertEquals(driver.findElement(By.xpath("//h1/following-sibling::p")).getText(), "Operation success");
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
