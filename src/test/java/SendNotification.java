import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SendNotification {
    public static void sendNotification(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            System.out.println("Navigating to notifications create page...");

            driver.get("https://mosa3ed.moltaqadev.com/ar/notifications/create");

            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("form")));

            WebElement titleArField = driver.findElement(By.cssSelector("input[name*='title'][lang*='ar'], input[name='title_ar'], input[id*='title'][id*='ar'], input[placeholder*='العنوان']"));
            titleArField.sendKeys("test user from automation");

            WebElement titleEnField = driver.findElement(By.cssSelector("input[name*='title'][lang*='en'], input[name='title_en'], input[id*='title'][id*='en'], input[placeholder*='Title']"));
            titleEnField.sendKeys("test user from automation");

            WebElement subjectArField = driver.findElement(By.cssSelector("textarea[name*='subject'][lang*='ar'], textarea[name='subject_ar'], textarea[id*='subject'][id*='ar'], textarea[placeholder*='الموضوع']"));
            subjectArField.sendKeys("test user title subject with files all docs autooo");

            WebElement subjectEnField = driver.findElement(By.cssSelector("textarea[name*='subject'][lang*='en'], textarea[name='subject_en'], textarea[id*='subject'][id*='en'], textarea[placeholder*='Subject']"));
            subjectEnField.sendKeys("test user title subject with files all docs autooo");

            WebElement recipientSelect = driver.findElement(By.cssSelector("select[name*='user'], select[name*='recipient'], select[name*='to'], .user-select, .recipient-select"));
            recipientSelect.sendKeys("all");

            WebElement sendButton = driver.findElement(By.cssSelector("button[type='submit'], .btn-primary, .submit-btn"));
            sendButton.click();

            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".success-message, .alert-success, .notification-sent")));

            System.out.println("Notification sent successfully to all users!");

            Thread.sleep(5000);

        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        try {
            driver.get("https://mosa3ed.moltaqadev.com/ar/login?callbackUrl=%2Far%2Fdashboard");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            System.out.println("Page loaded: " + driver.getCurrentUrl());

            WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("email")));
            emailField.sendKeys("admin@mosa3ed.sa");

            WebElement passwordField = driver.findElement(By.name("password"));
            passwordField.sendKeys("password123");

            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit'], input[type='submit'], .btn-primary")));
            submitButton.click();

            System.out.println("Submit button clicked, waiting for redirect...");

            wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("dashboard"),
                ExpectedConditions.urlContains("notifications/create")
            ));

            System.out.println("Successfully logged in! Current URL: " + driver.getCurrentUrl());

            sendNotification(driver);

        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
