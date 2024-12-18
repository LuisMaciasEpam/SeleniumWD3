package pages;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
public class HomePage extends BasePage {

    @FindBy(id = "twotabsearchtextbox")
    private WebElement searchBox;

    @FindBy(id = "nav-search-submit-button")
    private WebElement searchButton;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public SearchResultsPage searchForBook(String bookName) {
        searchBox.sendKeys(bookName);
        searchButton.click();
        return new SearchResultsPage(driver);
    }

    public WebElement getSearchBox() {
        return searchBox;
    }

}
