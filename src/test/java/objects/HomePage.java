package objects;

import org.openqa.selenium.WebDriver;

public class HomePage {
    private final WebDriver myDriver;

    public HomePage(WebDriver driver) {
        this.myDriver = driver;
//        PageFactory.initElements(driver, this);
    }

    public String getPageTitle() {
        return myDriver.getTitle();
    }
}
