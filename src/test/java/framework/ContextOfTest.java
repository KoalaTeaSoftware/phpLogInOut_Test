package framework;

import framework.actors.Actor;
import org.junit.Assert;

import java.io.IOException;

/**
 * This class holds all data that is shared over the life of the test run (i.e. broader scope even than features)
 * As of now, this is just reading the configuration files that will hold data relevant to the whole test run
 */
public class ContextOfTest {
    // these fields are static so that the test steps (which are static) can gain access to them
    public static ConfigReader sutConfiguration;
    public static ConfigReader testConfiguration;

    public static Actor.ActorType actorType;
    public static Actor actor;
    public static String sutBaseURL;
    public static boolean catchBrowserLogs = false;

    /**
     * ***********************************************************************************************************************
     * The following private declaration of one of my kind and the subsequent private constructor ensure that I am a Singleton
     * Initialisation of me does not work if it is in the constructor, it has to be here
     */
    private static ContextOfTest me = new ContextOfTest();

    private ContextOfTest() {
        try {
            testConfiguration = new ConfigReader("src/test/configuration/testFramework.properties");

            sutConfiguration = new ConfigReader(testConfiguration.getProperty("sutConfigPath"));

            actorType = Actor.ActorType.valueOf(testConfiguration.getProperty("actorName").toUpperCase());

            // this system property is required by the fancy HTML reporter from https://gitlab.com/monochromata-de/cucumber-reporting-plugin
            System.setProperty("cucumber.reporting.config.file", testConfiguration.getProperty("reportConfigFile"));
        } catch (IOException | NoSuchFieldException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
