package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PaymentPage extends BasePage{
    public PaymentPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "ap_email")
    private WebElement emailInput;

    public boolean signInPageIsLoaded(){
        return emailInput.isDisplayed();
    }
}
