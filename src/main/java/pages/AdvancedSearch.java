package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class AdvancedSearch extends BasePage{

    public AdvancedSearch(WebDriver driver) {
        super(driver);
    }

    @FindBy(name = "field-keywords")
    private WebElement keywords;

    @FindBy(name = "p_n_feature_browse-bin")
    private WebElement formatList;

    @FindBy(name = "field-title")
    private WebElement titleSearch;

    @FindBy(name = "Adv-Srch-Books-Submit")
    private WebElement searchButton;

    public SearchResultsPage searchByFormatAndTitle(String title, String format){
        titleSearch.sendKeys(title);
        Select select = new Select(formatList);
        select.selectByVisibleText(format);
        searchButton.click();
        return new SearchResultsPage(driver);
    }
}
