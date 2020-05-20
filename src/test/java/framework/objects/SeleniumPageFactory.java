package framework.objects;

import framework.ContextOfTest;
import org.openqa.selenium.support.PageFactory;

/**
 * All pages that use the Selenium Page Factory will have to call the init function
 * They can, therefore, extend this class, inheriting this, so will not have to contain that code
 */
public class SeleniumPageFactory {
    public SeleniumPageFactory() {
        PageFactory.initElements(ContextOfTest.actor.getDriver(), this);
    }
}
