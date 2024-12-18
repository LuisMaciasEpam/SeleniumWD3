package pages;

import lombok.Setter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

import static java.lang.Integer.parseInt;
import static utils.WaitUtil.*;

public class CartPage extends BasePage{

    public CartPage(WebDriver driver) {
        super(driver);
    }

    @Setter
    private int items = 0;

    @FindBy(xpath = "//div[@name='sc-quantity']//button[@data-a-selector='increment']")
    private List<WebElement> quantityUpControl;

    @FindBy(xpath = "//div[@name='sc-quantity']//button[@data-a-selector='decrement']")
    private List<WebElement> quantityDownControl;

    @FindBy(xpath ="//div[@data-a-selector='spinbutton']" )
    private List<WebElement> quantity;

    @FindBy(xpath = "//input[@data-action='delete']")
    private List<WebElement> deleteButton;

    @FindBy(id = "sc-subtotal-label-activecart")
    private WebElement subtotal;

    @FindBy(name = "proceedToRetailCheckout")
    private WebElement proceedToPayButton;

    private static final Object lock = new Object();

    public int randomIncrease(){
        Random random = new Random();
        return random.nextInt(4) + 2;
    }

    public void increaseQuantity(int increaseAmount, int index){
        for (int i = 0; i < increaseAmount; i++) {
            waitForElementIsClickable(driver,quantityUpControl.get(index),3);
            quantityUpControl.get(index).click();
            waitForChanges(2000);
            setItems(items + 1);
            Assert.assertTrue(increaseSubTotal());
        }
    }

    public void decreaseQuantity(int increaseAmount, int index){
        for (int i = 0; i < increaseAmount; i++) {
            waitForElementIsClickable(driver,quantityDownControl.get(index),3);
            quantityDownControl.get(index).click();
        }
    }

    public void deleteItemFromCart(int index){
        waitForElementIsClickable(driver,deleteButton.get(index),3);
        setItems(items - parseInt(quantity.get(index).getText()));
        deleteButton.get(index).click();
    }

    public boolean increaseSubTotal(){
        String subtotalMsg = "Subtotal ("+ (items) +" items):";
        String currentMsg = subtotal.getText();
        Assert.assertEquals(currentMsg, subtotalMsg);
        return true;
    }

    public boolean verifySubTotal(){
        Assert.assertEquals(subtotal.getText(), "Subtotal ("+ (items) +" items):");
        return true;
    }

    public PaymentPage goToPayment(){
        proceedToPayButton.click();
        return new PaymentPage(driver);
    }

    public void setItems(int items) {
        this.items = items;
    }
}
