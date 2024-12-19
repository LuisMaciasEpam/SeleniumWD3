package tests;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static java.lang.Integer.parseInt;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Random;


public class SelenideTest {

    private int items;

    @Test
    public void searchABookWithMoreThan5Results() {
        Configuration.reportsFolder = "target/screenshots";

        open("https://www.bookdepository.com/");
        screenshot("searchPage");
        $(By.id("twotabsearchtextbox")).setValue("java");
        screenshot("termSearched");
        $(By.id("nav-search-submit-button")).click();
        $(By.xpath("//h2[contains(text(),'Results')]")).shouldHave(text("Results"));
        screenshot("searchResults");
        int totalElements = $$(By.xpath("//div[@data-cy=\"title-recipe\"]/a")).size();
        screenshot("searchResults");
        Assert.assertTrue(totalElements > 5, "Exist less than 5 results for this search term");
    }

    @Test
    public void AddBooksToCartAndCheckout() {

        Configuration.reportsFolder = "target/screenshots";

        open("https://www.amazon.com/advanced-search/books");
        $(By.name("field-title")).setValue("Java");
        $(By.name("p_n_feature_browse-bin")).selectOption("Kindle Edition");
        screenshot("filtersKindle");
        $(By.name("Adv-Srch-Books-Submit")).click();

        ElementsCollection resultElements = $$(By.xpath("//div[contains(@class, 'puis-price')]/div[contains(@class,'a-color-base')]/a"));
        boolean containsText = resultElements.stream()
                .allMatch(element -> element.getText().contains("Kindle"));
        screenshot("KindleResults");
        Assert.assertTrue(containsText);

        $(By.xpath("//span[contains(text(),'Clear')]")).click();
        open("https://www.amazon.com/advanced-search/books");
        $(By.name("field-title")).setValue("Java");
        $(By.name("p_n_feature_browse-bin")).selectOption("Paperback");
        screenshot("filtersPaperback");
        $(By.name("Adv-Srch-Books-Submit")).click();

        ElementsCollection resultElementsPaperback = $$(By.xpath("//div[contains(@class, 'puis-price')]/div[contains(@class,'a-color-base')]/a"));
        boolean containsPaperback = resultElementsPaperback.stream()
                .allMatch(element -> element.getText().contains("Paperback"));
        screenshot("PaperbackResults");
        Assert.assertTrue(containsPaperback);

        resultElementsPaperback.get(0).click();
        $(By.id("add-to-cart-button")).click();
        screenshot("addToCartItem1");
        back();
        $(By.xpath("//a[contains(text(),'Back to results')]")).click();
        back();
        resultElementsPaperback.get(1).click();
        $(By.id("add-to-cart-button")).click();
        screenshot("addToCartItem2");

        $(By.xpath("//a[contains(text(),'Go to Cart')]")).click();
        setItems(2);
        screenshot("shoppingCart");

        incrementQuantity($$(By.xpath("//div[@name='sc-quantity']//button[@data-a-selector='increment']")).get(0));
        validateSubtotal($(By.id("sc-subtotal-label-activecart")));
        screenshot("randomIncreaseItem1");

        incrementQuantity($$(By.xpath("//div[@name='sc-quantity']//button[@data-a-selector='increment']")).get(1));
        validateSubtotal($(By.id("sc-subtotal-label-activecart")));
        screenshot("randomIncreaseItem2");

        int subtotalItems = parseInt($$(By.xpath("//div[@data-a-selector='spinbutton']")).get(1).getText());
        setItems(getItems() - subtotalItems);
        $$(By.xpath("//input[@data-action='delete']")).get(1).click();
        validateSubtotal($(By.id("sc-subtotal-label-activecart")));
        screenshot("deleteItem2");

        $(By.name("proceedToRetailCheckout")).click();
        screenshot("checkout");
        waitFor2Seconds();
        Assert.assertTrue($(By.id("ap_email")).exists());
        screenshot("loginPage");
    }

    private void incrementQuantity(SelenideElement element){
        int random = randomIncrease();
        for (int i = 0; i < random; i++) {
            element.click();
            waitFor2Seconds();
            setItems(getItems() + 1);
        }
    }

    private void validateSubtotal(SelenideElement element){
        String subtotalMsg = "Subtotal ("+ (getItems()) +" items):";
        String currentMsg = element.getText();
        Assert.assertEquals(currentMsg,subtotalMsg);
    }

    private void waitFor2Seconds() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setItems(int items) {
        this.items = items;
    }

    public int getItems() {
        return items;
    }

    public int randomIncrease(){
        Random random = new Random();
        return random.nextInt(3) + 1;
    }
}
