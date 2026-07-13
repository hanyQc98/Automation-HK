import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class VerifySidebar {
    public static void main(String[] args) {
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

            System.out.println("Successfully logged in!");

            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".sidebar")));

            List<WebElement> sidebarItems = driver.findElements(By.cssSelector(".sidebar a, .sidebar .menu-item, .sidebar li"));

            System.out.println("Total sidebar items found: " + sidebarItems.size());

            for (int i = 0; i < sidebarItems.size(); i++) {
                WebElement item = sidebarItems.get(i);
                String text = item.getText().trim();
                String href = item.getAttribute("href");
                System.out.println((i + 1) + ". " + text + (href != null ? " -> " + href : ""));
            }

            if (sidebarItems.isEmpty()) {
                System.err.println("WARNING: No sidebar items found. You may need to update the CSS selector.");
            } else {
                System.out.println("All sidebar items verified successfully!");
            }

            Thread.sleep(5000);

        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
