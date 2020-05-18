package framework;

import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;

/**
 * Use this class to share data between steps in a scenario
 */
public class ContextOfScenario {

    public static WebDriver driver;
    public static Scenario scenario;
}