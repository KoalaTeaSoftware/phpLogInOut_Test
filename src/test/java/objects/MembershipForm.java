package objects;

import framework.objects.SeleniumPageFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MembershipForm extends SeleniumPageFactory {

    @FindBy(id = "membershipForm")
    private WebElement logInForm;
    @FindBy(id = "errorFlag")
    private WebElement errorFlag;
    @FindBy(id = "emailAddress")
    private WebElement emailAddressField;
    @FindBy(id = "pwd")
    private WebElement passwordField;
    @FindBy(name = "logIn")
    private WebElement loginButton;
    @FindBy(name = "signUp")
    private WebElement signUpButton;
    @FindBy(id = "logOutForm")
    private WebElement logOutForm;
    @FindBy(name = "logout")
    private WebElement logOutButton;

    public void setEmailAddressField(String emailAddressField) {
        this.emailAddressField.sendKeys(emailAddressField);
    }

    public void setPasswordField(String passwordField) {
        this.passwordField.sendKeys(passwordField);
    }

    public void triggerLogIn() {
        this.loginButton.click();
    }

    public void triggerSignUp() {
        this.signUpButton.click();
    }

    public void triggerLogOut() {
        this.logOutButton.click();
    }

    public boolean logInFormIsDisplayed() {
        try {
            return logInForm.isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public boolean logOutFormIsDisplayed() {
        try {
            return logOutForm.isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public String getErrorFlag() {
        return errorFlag.getAttribute("value");
    }
}
