package pages;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static utils.ActionUtil.hoverOverElement;

@Getter
public class SearchResultsPage extends BasePage {

    private String URLAdvanceSearch = "https://www.amazon.com/advanced-search/books";

    @FindBy(xpath = "//div[@data-cy=\"title-recipe\"]/a")
    private List<WebElement> bookTitles;

    @FindBy(xpath = "//span[contains(text(),'Advanced Search')]")
    private WebElement advanceSearch;

    @FindBy(xpath = "//div[contains(@class, 'puis-price')]/div[contains(@class,'a-color-base')]/a")
    private List<WebElement> formatType;

    @FindBy(xpath = "//span[contains(text(),'Clear')]")
    private WebElement clearFilters;


    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public AdvancedSearch goToAdvanceSearch(){
        driver.get(URLAdvanceSearch);
        return new AdvancedSearch(driver);
    }

    public int countResultBooks(){
        return bookTitles.size();
    }

    public boolean allElementsContainFormat(String format){
        for (WebElement element : formatType) {
            String elementText = element.getText().toLowerCase();

            if (!elementText.contains(format.toLowerCase())) {
                return false;
            }
        }
        return true;
    }

    public ProductPage selectBook(int bookIndex){
        formatType.get(bookIndex).click();
        return new ProductPage(driver);
    }

    public void clearFilters(){
        hoverOverElement(driver,clearFilters);
        clearFilters.click();
    }
}
