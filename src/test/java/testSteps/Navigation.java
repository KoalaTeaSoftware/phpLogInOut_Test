package testSteps;

import framework.ContextOfTest;
import io.cucumber.java.en.When;

public class Navigation {
    @When("I go to the home page")
    public void iGoToTheHomePage() {
        // Assume that we are just testing the UI
        // no need to be messing about with different types of actor
        ContextOfTest.actor.getResource(ContextOfTest.sutBaseURL);
    }
}
