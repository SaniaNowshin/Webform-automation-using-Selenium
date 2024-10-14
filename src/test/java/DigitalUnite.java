import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DigitalUnite {
    WebDriver driver;

    @BeforeAll
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().minimize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @DisplayName("Fill the webform information according to the field and submit the form")
    @Test

    public void formFillup() throws InterruptedException {
           driver.get("https://www.digitalunite.com/practice-webform-learners");

           List<WebElement> formElm = driver.findElements(By.className("form-control"));

           //click add-button
           WebElement bannerCloseButton = driver.findElement(By.className("banner-close-button"));
           bannerCloseButton.click();

            // Name field
            formElm.get(0).sendKeys("Sania");
            Assertions.assertEquals("Sania", formElm.get(0).getAttribute("value"));

            // Phone number field
            formElm.get(1).sendKeys("01888888888");
            Assertions.assertEquals("01888888888", formElm.get(1).getAttribute("value"));
            Utils.scroll(driver, 500);

            // Date picker
            WebElement dateElement = driver.findElement(By.id("edit-date"));
            dateElement.sendKeys(Keys.CONTROL + "A" + Keys.BACK_SPACE);  // Clear the field
            DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
            Date date = new Date();
            String currentDate = dateFormat.format(date);
            dateElement.sendKeys(currentDate, Keys.ENTER);

            String expectedResult = "10-14-2024";
            Assertions.assertEquals(currentDate, expectedResult);

            // Email
            formElm.get(3).sendKeys("sania@test.com");
            Assertions.assertEquals("sania@test.com", formElm.get(3).getAttribute("value"));
            Utils.scroll(driver, 500);

            // About yourself
            formElm.get(4).sendKeys("I am Sania Islam Nowshin, an undergraduate student currently learning Software Quality Assurance.");
            String expectedTextarea = "I am Sania Islam Nowshin, an undergraduate student currently learning Software Quality Assurance.";
            Assertions.assertTrue(expectedTextarea.contains(formElm.get(4).getAttribute("value")));
            Utils.scroll(driver, 500);

            // Upload a document
            WebElement uploadElement = driver.findElement(By.id("edit-uploadocument-upload"));
            uploadElement.sendKeys("C:\\Users\\DELL\\IdeaProjects\\AutomateWebForm-1\\src\\test\\resources\\tree.jpg");

            // Click on Check Box
            Utils.scroll(driver, 1000);
            driver.findElement(By.id("edit-age")).click();

            // Submit
            Thread.sleep(1000);  // Use only minimal sleep for stabilization
            driver.findElement(By.id("edit-submit")).click();

            // Handle Alert
            driver.switchTo().alert().accept();

            // Assert Success Message
            String actualMessage = driver.findElement(By.tagName("h1")).getText();
            String expectedMessage = "Thank you for your submission!";
            Assertions.assertEquals(actualMessage, expectedMessage);

    }
    @AfterAll
    @Test
    public void close(){
        driver.quit();
    }
}
