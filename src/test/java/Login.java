import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class Login {
    
    @Test
    public void testLoginToDashboard() {
        WebDriver driver = loginToDashboard();
        Assert.assertNotNull(driver, "Login failed - driver is null");
        
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
    
    public static WebDriver loginToDashboard() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        try {
            driver.get("https://mosa3ed.moltaqadev.com/ar/login?callbackUrl=%2Far%2Fdashboard");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            wait.until(ExpectedConditions.presenceOfElementLocated(By.name("email")))
                    .sendKeys("admin@mosa3ed.sa");

            driver.findElement(By.name("password")).sendKeys("password123");

            driver.findElement(By.cssSelector("button[type='submit']")).click();

            wait.until(ExpectedConditions.urlContains("dashboard"));

            Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"), "Login failed - not on dashboard page");
            
            Thread.sleep(2000);

        } catch (Exception e) {
            Assert.fail("Login failed: " + e.getMessage());
        }
        SendNotification.sendNotification(driver);
        return driver;
    }
}