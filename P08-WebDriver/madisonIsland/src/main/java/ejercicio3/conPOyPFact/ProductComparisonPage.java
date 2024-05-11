package ejercicio3.conPOyPFact;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductComparisonPage {
    WebDriver driver;
    String myHandleId;
    String myHandleIdFrom;
    @FindBy(xpath = "//*[@id=\"top\"]/body/div/div[3]/button") WebElement close;

    public ProductComparisonPage(WebDriver driver) {
        this.driver = driver;
    }

    public ShoesPage close() {
        close.click();
        driver.switchTo().frame(myHandleIdFrom);
        return PageFactory.initElements(driver, ShoesPage.class);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }
}
