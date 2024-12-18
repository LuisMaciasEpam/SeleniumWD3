package tests;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

import static utils.WaitUtil.waitForElementVisible;

public class SearchAndCheckoutTest extends BaseTest {

    @Test
    public void testSearchABookWithMoreThan5Results() {
        driver.get("https://www.bookdepository.com/");

        HomePage homePage = new HomePage(driver);
        waitForElementVisible(driver, homePage.getSearchBox(), 10);
        SearchResultsPage searchResultsPage = homePage.searchForBook("Java");
        int resultTitles = searchResultsPage.countResultBooks();
        Assert.assertTrue(resultTitles > 5, "There are less than 5 Books");
    }

    @Test
    public void testFiltersByDifferentFormats() {
        driver.get("https://www.bookdepository.com/");
        HomePage homePage = new HomePage(driver);
        waitForElementVisible(driver, homePage.getSearchBox(), 10);
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
        AdvancedSearch advancedSearch = searchResultsPage.goToAdvanceSearch();
        SearchResultsPage searchFiltered = advancedSearch.searchByFormatAndTitle("java", "Kindle Edition");

        Assert.assertTrue(searchFiltered.allElementsContainFormat("Kindle"),"Not all books are available in Kindle format");

        searchFiltered.clearFilters();
        searchResultsPage.goToAdvanceSearch();
        advancedSearch.searchByFormatAndTitle("java", "Paperback");

        Assert.assertTrue(searchFiltered.allElementsContainFormat("Paperback"),"Not all books are available in Paperback format");

        ProductPage productPage = searchResultsPage.selectBook(0);
        productPage.addToCart();
        driver.navigate().back();
        productPage.backToResults();
        driver.navigate().back();
        productPage = searchResultsPage.selectBook(1);
        productPage.addToCart();

        CartPage cartPage = productPage.goToCart();
        cartPage.setItems(2);
        cartPage.increaseQuantity(cartPage.randomIncrease(), 0 );
        cartPage.increaseQuantity(cartPage.randomIncrease(), 1 );
        cartPage.deleteItemFromCart(0);

        Assert.assertTrue(cartPage.verifySubTotal());

        PaymentPage paymentPage = cartPage.goToPayment();
        Assert.assertTrue(paymentPage.signInPageIsLoaded());

    }
}
