package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import utils.DriverFactory;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;

    @Parameters("browser")
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser) {
        driver = DriverFactory.getDriver(browser);
        driver.manage().window().maximize();
        //driver.get("https://example.com"); // URL base
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
