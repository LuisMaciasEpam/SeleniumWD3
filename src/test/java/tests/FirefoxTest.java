package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchResultsPage;

import static utils.WaitUtil.waitForElementVisible;

public class FirefoxTest  extends BaseTest{
    @Test
    public void testSearchABookWithMoreThan5Results() {
        driver.get("https://www.bookdepository.com/");
        System.out.println("Running Firefox test on thread: " + Thread.currentThread().getId());
        HomePage homePage = new HomePage(driver);
        waitForElementVisible(driver, homePage.getSearchBox(), 10);
        SearchResultsPage searchResultsPage = homePage.searchForBook("Java");
        int resultTitles = searchResultsPage.countResultBooks();
        Assert.assertTrue(resultTitles > 5, "There are less than 5 Books");
    }
}
