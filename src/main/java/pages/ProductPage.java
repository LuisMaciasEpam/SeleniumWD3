package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import static utils.ActionUtil.hoverOverElement;

public class ProductPage extends BasePage{

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "add-to-cart-button")
    private WebElement addToCartButton;

    @FindBy(xpath = "//span[@class='a-list-item']/a[@id='breadcrumb-back-link']")
    private WebElement backToResults;

    @FindBy(name = "proceedToRetailCheckout")
    private WebElement proceedToCheckoutButton;

    @FindBy(xpath = "//a[contains(text(),'Go to Cart')]")
    private WebElement goToCartButton;

    public CartPage goToCart(){
        goToCartButton.click();
        return new CartPage(driver);
    }

    public void addToCart(){
        hoverOverElement(driver,addToCartButton);
        addToCartButton.click();
    }

    public void backToResults(){

        hoverOverElement(driver,backToResults);
        backToResults.click();
    }

}
