package testSteps;

import framework.ContextOfTest;
import framework.helpers.DateHelpers;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import objects.MembershipForm;
import org.junit.Assert;

public class Membership {
    private MembershipForm membershipForm = null;
    private String thatEmailAddress = "";
    private String thatPassword = "";

    private MembershipForm getPageObject() {
        if (this.membershipForm == null)
            this.membershipForm = new MembershipForm();
        return this.membershipForm;
    }

    @Given("I am not logged in")
    public void iAmNotLoggedIn() {
        // ToDo: provide a proper way of ensuring that the user is ot logged in
        // This is a cludge, as it relies n proper operation of the form

        if (getPageObject().logOutFormIsDisplayed())
            getPageObject().triggerLogOut();

        Assert.assertTrue("The login form should be visible", getPageObject().logInFormIsDisplayed());
    }

    @Then("I see the login form")
    public void iSeeTheLoginForm() {
        Assert.assertTrue("The log IN form should be visible", getPageObject().logInFormIsDisplayed());
    }

    @Then("I do not see the login form")
    public void iDoNotSeeTheLoginForm() {
        Assert.assertFalse("The log IN form should be visible", getPageObject().logInFormIsDisplayed());
    }

    @Then("I see the logout form")
    public void iSeeTheLogoutForm() {
        Assert.assertTrue("The log OUT form should be visible", getPageObject().logOutFormIsDisplayed());
    }

    @And("I do not see the logout form")
    public void iDoNotSeeTheLogoutForm() {
        Assert.assertFalse("Thee Log Out Form should not be visible", getPageObject().logOutFormIsDisplayed());
    }


    @And("I provide email {string}")
    public void iProvideEmail(String arg0) {
        if (arg0.equalsIgnoreCase("unique")) {
            arg0 = DateHelpers.uniqueFileName() + "@gmail.com";
            ContextOfTest.actor.writeToHtmlReport("Unique name:" + arg0 + ":");
        }
        thatEmailAddress = arg0;
        getPageObject().setEmailAddressField(arg0);
    }

    @And("I provide password {string}")
    public void iProvidePassword(String arg0) {
        thatPassword = arg0;
        getPageObject().setPasswordField(arg0);
    }

    @When("I log in")
    public void iLogIn() {
        getPageObject().triggerLogIn();
    }

    @And("I see that the problem is {string}")
    public void iSeeThatTheProblemIs(String arg0) {
        String errorMsg = "";
        boolean result;
        String actual = getPageObject().getStatusMessage().toLowerCase();
        switch (arg0.toLowerCase()) {
            case "bad credentials":
            case "missed credentials":
                String option1 = "Please provide a valid email address";
                String option2 = "A valid password contains only";
                errorMsg = String.format(
                        "Expected the status to contain :%s:, or :%s:. It was :%s:",
                        option1, option2, actual
                );
                result = (
                        actual.contains(option1.toLowerCase()) || actual.contains(option2.toLowerCase())
                );
                Assert.assertTrue(errorMsg, result);
                break;
            default:
                Assert.fail("Verification for problem : " + arg0.toLowerCase() + ": has not been implemented");
        }
    }

    @When("I sign up")
    public void iSignUp() {
        getPageObject().triggerSignUp();
    }

    @When("I log out")
    public void iLogOut() {
        getPageObject().triggerLogOut();
    }

    @When("I provide that email address")
    public void iProvideThatEmailAddress() {
        getPageObject().setEmailAddressField(thatEmailAddress);
    }

    @And("I provide that password")
    public void iProvideThatPassword() {
        getPageObject().setPasswordField(thatPassword);
    }

    @And("the password field contains {string}")
    public void thePasswordFieldContains(String expectedContents) {
        Assert.assertEquals("The password field contains unexpected data", expectedContents, getPageObject().getPasswordField());
    }

    @And("the email field contains {string}")
    public void theEmailFieldContains(String expectedContents) {
        Assert.assertEquals("The password field contains unexpected data", expectedContents, getPageObject().getEmailAddressField());
    }
}
