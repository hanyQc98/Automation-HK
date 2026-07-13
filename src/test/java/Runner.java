import org.openqa.selenium.WebDriver;

public class Runner {
    public static void main(String[] args) {
        System.out.println("Starting automation test sequence...");

        WebDriver driver = Login.loginToDashboard();

        if (driver != null) {
            try {
                System.out.println("Login successful, proceeding to send notification...");
//               SendNotification.sendNotification(driver);
                System.out.println("Automation test sequence completed successfully!");
            } catch (Exception e) {
                System.err.println("Error during notification sending: " + e.getMessage());
                e.printStackTrace();
            } finally {
                driver.quit();
                System.out.println("Browser closed.");
            }
        } else {
            System.err.println("Login failed. Automation sequence aborted.");
        }
    }
}
