package testSteps;

import framework.ContextOfScenario;
import framework.ContextOfTest;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import objects.HomePage;
import objects.RandomPostcodeResponse;
import org.apache.http.HttpStatus;
import org.junit.Assert;

public class Smoke {
    @When("I see the default resource")
    public void iSeeTheDefaultResource() {
        switch (ContextOfTest.actorType) {
            case FIREFOX:
            case CHROME:
            case INTERNET_EXPLORER:
            case SAFARI:
                ContextOfTest.actor.getResource(ContextOfTest.sutBaseURL);
                break;
            case ANDROID:
            case IOS:
                break;
            case API:
                try {
                    String apiURL = ContextOfTest.sutBaseURL;
                    apiURL += '/' + ContextOfTest.sutConfiguration.getProperty("apiDefaultResource");
                    ContextOfTest.actor.getResource(apiURL);
                    break;
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                    Assert.fail("Problem requesting the default API endpoint");
                }
                break;
        }
    }

    @Then("that resource is retrieved")
    public void thatResourceIsRetrieved() {
        switch (ContextOfTest.actorType) {
            case FIREFOX:
            case CHROME:
            case INTERNET_EXPLORER:
            case SAFARI:
                HomePage homePage = new HomePage(ContextOfScenario.driver);

                String pageTitle = homePage.getPageTitle();
                ContextOfTest.actor.writeToHtmlReport(String.format("Page has title :%s:\n", pageTitle));

                Assert.assertTrue("The page should have a title", pageTitle.length() > 0);
                break;
            case ANDROID:
            case IOS:
                break;
            case API:
                RandomPostcodeResponse randomPostcodeResponse = new RandomPostcodeResponse(ContextOfTest.actor.getResponse());

                int code = randomPostcodeResponse.getStatus();
                ContextOfTest.actor.writeToHtmlReport(String.format("The Response code is :%d:", code));
                ContextOfTest.actor.writeToHtmlReport(String.format("The random Postcode is :%s:", randomPostcodeResponse.getPostcode()));

                Assert.assertEquals("The response code should be OK", code, HttpStatus.SC_OK);
                break;
        }

    }
}
