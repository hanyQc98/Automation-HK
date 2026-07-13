import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ElementActions {
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

            System.out.println("Successfully logged in and navigated to dashboard!");
            System.out.println("Current URL: " + driver.getCurrentUrl());

            Thread.sleep(3000);

            System.out.println("Dashboard loaded, ready for filtering and search...");

            performFilterAndSearch(driver, wait);

            return driver;

        } catch (Exception e) {
            System.err.println("Error occurred during login: " + e.getMessage());
            e.printStackTrace();
            driver.quit();
            return null;
        }
    }

    private static <WebElement> void performFilterAndSearch(WebDriver driver, WebDriverWait wait) {
        try {
            WebElement searchInput = (WebElement) driver.findElement(By.cssSelector("input[type='search'], input[placeholder*='بحث'], input[placeholder*='search'], .search-input"));
            ((org.openqa.selenium.WebElement) searchInput).sendKeys("test");
            System.out.println("Search query entered: 'test'");

            Thread.sleep(1000);

            WebElement filterDropdown = (WebElement) driver.findElement(By.cssSelector("select[name='filter'], .filter-select, .dropdown-filter"));
            filterDropdown.clone();
            System.out.println("Filter dropdown clicked");

            Thread.sleep(500);

            WebElement applyFilterButton = (WebElement) driver.findElement(By.cssSelector(".apply-filter, .filter-btn, button[type='submit']"));
            applyFilterButton.clone();
            System.out.println("Filter applied");

            Thread.sleep(2000);

            System.out.println("Filter and search operations completed successfully!");

        } catch (Exception e) {
            System.out.println("Filter and search skipped or not available: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        WebDriver driver = loginToDashboard();
        if (driver != null) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                driver.quit();
            }
        }
    }
}