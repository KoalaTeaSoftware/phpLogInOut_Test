package framework.actors;

import framework.ContextOfTest;
import framework.helpers.DateHelpers;
import org.junit.Assert;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverLogLevel;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class IeActor extends Actor {

    private InternetExplorerDriverService service;

    /**
     * IE refuses to close windows. This is quite widely reported, and
     * there appears to be no clear idea of exactly why.
     * This blunderbus has been suggested in a few places and is a workable option
     */
    @Override
    public void closeDriver() {
        if (null != driver) {
            System.out.println("[info] Closing IE driver");
            driver.quit(); // try it, just in case they fix the bug
            driver = null; // Essential to do this, else you keep getting the dead driver
            try {
                Runtime.getRuntime().exec("taskkill /F /T /IM iexplore.exe");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            // maybe something has gone wrong with the framework?
            System.out.println("[info] No driver to close");
    }

    @Override
    protected void startService() {
        if (null == service) {
            System.out.println("[info] Creating a Driver Service for IE");
            // i.e. Lazy Instantiation of the Service
            try {
                String driverPath = ContextOfTest.testConfiguration.getProperty("ieDriverPath");

                String logLoc = System.getProperty("user.dir");
                logLoc += File.separator + ContextOfTest.testConfiguration.getProperty("driverLogLocation");
                logLoc += File.separator + "InternetExplorer-" + DateHelpers.uniqueFileName() + ".txt";

                service = new InternetExplorerDriverService.Builder()
                        .usingDriverExecutable(new File(driverPath))
                        .usingAnyFreePort()
                        .withLogFile(new File(logLoc))
                        .withLogLevel(InternetExplorerDriverLogLevel.DEBUG)
                        .build();
            } catch (Exception e) {
                e.printStackTrace();
                Assert.fail();
            }
        }
    }

    // NB: this is unused at the moment,but is here for completeness
    @Override
    protected void stopService() {
        if (null != service && service.isRunning()) {
            System.out.println("[info] Stopping the Driver Service for IE");
            service.stop();
        }
    }

    @Override
    public void createDriver() {
        System.out.println("[info] Creating a Web Driver for IE");

        DesiredCapabilities cap = DesiredCapabilities.internetExplorer();
        cap.setCapability("platform", "WIN10");
        cap.setCapability("version", "11");
        cap.setCapability("browserName", "internet explorer");
        cap.setCapability("ignoreProtectedModeSettings", 1);
        cap.setCapability("nativeEvents", "false");
        cap.setCapability("ignoreZoomSetting", true);
        cap.setCapability("requireWindowFocus", "true");
//        cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);

        driver = new InternetExplorerDriver(service, cap);

        driver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
    }
}
