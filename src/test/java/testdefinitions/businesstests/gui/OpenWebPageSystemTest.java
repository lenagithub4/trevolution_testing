package testdefinitions.businesstests.gui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class OpenWebPageSystemTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Make sure chromedriver is in your system PATH or specify its location
        driver = new ChromeDriver();
    }

    @Test
    public void testTrevolutionPageTitle() {
        driver.get("https://dyninno.com/en/division/trevolution/");
        String title = driver.getTitle();
        System.out.println("Page title: " + title);
        assertTrue(title.toLowerCase().contains("trevolution"));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


}
