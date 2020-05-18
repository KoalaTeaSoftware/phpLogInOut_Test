package framework;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

    @Before
    public void beforeScenario(Scenario givenScenario) {
        ContextOfScenario.scenario = givenScenario;
        ContextOfTest.actor = ActorFactory.getActor(ContextOfTest.actorType);
        ContextOfScenario.driver = ContextOfTest.actor.getDriver();
    }

    @After
    public void afterScenario(Scenario scenario) {

        // this may seem a bit involved, but a direct use of getStatus().equals does not yield the hoped-for result
        if (!scenario.getStatus().name().equalsIgnoreCase("passed")) {
            ContextOfTest.actor.embedScreenShot(
                    scenario,
                    "Screenshot taken because this scenario is marked as " + scenario.getStatus().toString());
        }

        ContextOfTest.actor.closeDriver();
    }
}
