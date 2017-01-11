package integration;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class EditComputerWithoutDatesIT {
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
    public void testEditComputerWithoutDates() throws Exception {
        driver.get(baseUrl + "/dashboard");
        Thread.sleep(SLEEP);

        driver.findElement(By.linkText("Apple IIGS")).click();
        Thread.sleep(SLEEP);
        assertEquals(driver.findElement(By.xpath("//h1")).getText(), "Edit Computer");

        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys("M");
        Thread.sleep(SLEEP);
        assertEquals(driver.findElement(By.id("name")).getAttribute("value"), "M");

        driver.findElement(By.cssSelector("input.btn.btn-primary")).click();
        Thread.sleep(SLEEP);
        assertEquals(driver.findElement(By.id("name.errors")).getText(), "size must be between 2 and 40");

        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys("Mac");
        Thread.sleep(SLEEP);
        assertEquals(driver.findElement(By.id("name")).getAttribute("value"), "Mac");

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
