import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class SendNotification {

    public static void verifyNotificationTable(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        System.out.println("This current URL: "+driver.getCurrentUrl());
        WebElement table = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table")));
        Assert.assertNotNull(table, "Notification table not found");
        
        List<WebElement> headers = table.findElements(By.cssSelector("thead th"));
        
        String[] expectedColumns = {"title", "content", "notification date", "Actions"};
        for (String column : expectedColumns) {
            boolean found = headers.stream().anyMatch(h -> h.getText().toLowerCase().contains(column.toLowerCase()));
            Assert.assertTrue(found, "Column '" + column + "' not found in table");
        }
        
        WebElement actionsColumn = headers.stream()
            .filter(h -> h.getText().toLowerCase().contains("actions"))
            .findFirst()
            .orElse(null);
        Assert.assertNotNull(actionsColumn, "Actions column not found");
        
        System.out.println("Notification table verified with all required columns");
    }

    public static void clickAddNotificationButton(WebDriver driver) {

        driver.navigate().to("https://mosa3ed.moltaqadev.com/ar/notifications");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("body > div.min-h-screen.bg-muted/30 > main > div > div > div.flex.justify-end > button")
        ));
        addButton.click();
        
        wait.until(ExpectedConditions.urlContains("notifications/create"));
        System.out.println("Navigated to add notification page");
    }

    public static void fillTitleFields(WebDriver driver, String titleAr, String titleEn) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        WebElement titleArField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("body > div.min-h-screen.bg-muted/30 > main > div > div > form > div:nth-child(1) > div > div > div:nth-child(1) > input")
        ));
        titleArField.sendKeys(titleAr);
        
        WebElement titleEnField = driver.findElement(
            By.cssSelector("body > div.min-h-screen.bg-muted/30 > main > div > div > form > div:nth-child(1) > div > div > div:nth-child(2) > input")
        );
        titleEnField.sendKeys(titleEn);
        
        System.out.println("Title fields filled");
    }

    public static void fillMessageFields(WebDriver driver, String messageAr, String messageEn) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        WebElement messageArField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("body > div.min-h-screen.bg-muted/30 > main > div > div > form > div:nth-child(2) > div > div > div:nth-child(1) > textarea")
        ));
        messageArField.sendKeys(messageAr);
        
        WebElement messageEnField = driver.findElement(
            By.cssSelector("body > div.min-h-screen.bg-muted/30 > main > div > div > form > div:nth-child(2) > div > div > div:nth-child(2) > textarea")
        );
        messageEnField.sendKeys(messageEn);
        
        System.out.println("Message fields filled");
    }

    public static void selectAllRecipients(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        WebElement recipientDropdown = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("body > div.min-h-screen.bg-muted/30 > main > div > div > form > div:nth-child(3) > div > div > button")
        ));
        recipientDropdown.click();
        
        WebElement selectAllOption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#select-all-dropdown")));
        selectAllOption.click();
        
        System.out.println("All recipients selected");
    }

    public static void clickSaveButton(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("body > div.min-h-screen.bg-muted/30 > main > div > div > form > div.flex.justify-end.gap-3 > button.inline-flex.items-center.justify-center.gap-2.whitespace-nowrap.rounded-md.text-sm.font-medium.transition-all.cursor-pointer.disabled\\:pointer-events-none.disabled\\:opacity-50.disabled\\:cursor-not-allowed.\\[\\&_svg\\]\\:pointer-events-none.\\[\\&_svg\\:not\\(\\[class\\*=\\'size-\\'\\]\\)\\]\\:size-4.shrink-0.\\[\\&_svg\\]\\:shrink-0.outline-none.focus-visible\\:border-ring.focus-visible\\:ring-ring\\/50.focus-visible\\:ring-\\[3px\\].aria-invalid\\:ring-destructive\\/20.dark\\:aria-invalid\\:ring-destructive\\/40.aria-invalid\\:border-destructive.h-9.px-4.py-2.has-\\[\\>svg\\]\\:px-3.bg-app-primary.hover\\:bg-app-primary\\/90.text-white")
        ));
        saveButton.click();
        
        System.out.println("Save button clicked");
    }

    public static void sendNotification(WebDriver driver) {
        createNotification(driver, "اختبار من الأتمتة", "Test from automation", "رسالة تجريبية من الأتمتة", "Test message from automation");
    }

    public static void createNotification(WebDriver driver, String titleAr, String titleEn, String messageAr, String messageEn) {
        driver.get("https://mosa3ed.moltaqadev.com/en/notifications/create");
        
        fillTitleFields(driver, titleAr, titleEn);
        fillMessageFields(driver, messageAr, messageEn);
        selectAllRecipients(driver);
        clickSaveButton(driver);
    }

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        try {
            driver.get("https://mosa3ed.moltaqadev.com/ar/login?callbackUrl=%2Far%2Fdashboard");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("email")));
            emailField.sendKeys("admin@mosa3ed.sa");

            WebElement passwordField = driver.findElement(By.name("password"));
            passwordField.sendKeys("password123");

            WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
            submitButton.click();

            wait.until(ExpectedConditions.urlContains("dashboard"));
            //verifyNotificationTable(driver);
            clickAddNotificationButton(driver);
            createNotification(driver, "اختبار من الأتمتة", "Test from automation", "رسالة تجريبية من الأتمتة", "Test message from automation");

            Thread.sleep(5000);

        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
