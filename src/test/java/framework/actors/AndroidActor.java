package framework.actors;

import framework.ContextOfTest;
import framework.helpers.DateHelpers;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.junit.Assert;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class AndroidActor extends Actor {

    private AppiumDriverLocalService service;

    @Override
    protected void startService() {
        if (service == null) {
            System.out.println("[info] Creating a Driver Service for Appium");
            // i.e. Lazy Instantiation of the Service

            try {
                String logLoc = System.getProperty("user.dir");
                logLoc += File.separator + ContextOfTest.testConfiguration.getProperty("driverLogLocation");
                logLoc += File.separator + "Appium-" + DateHelpers.uniqueFileName() + ".txt";

                String driverPath = ContextOfTest.testConfiguration.getProperty("appiumDriverPath");
                String appiumPath = ContextOfTest.testConfiguration.getProperty("appiumJSpath");

                service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                        .withAppiumJS(new File(appiumPath))
                        .usingDriverExecutable(new File(driverPath))
                        .withIPAddress("127.0.0.1")
                        .usingPort(4723).withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER, "4724")
                        .withArgument(AndroidServerFlag.CHROME_DRIVER_PORT, "4725")
                        .withLogFile(new File(logLoc))
                        .withArgument(GeneralServerFlag.LOG_NO_COLORS));
                service.start();
            } catch (Exception e) {
                e.printStackTrace();
                Assert.fail();
            }
        }
    }

    @Override
    protected void stopService() {
        if (null != service && service.isRunning()) {
            System.out.println("[info] Stopping the Driver Service for Appium");
            service.stop();
        }
    }

    @Override
    protected void createDriver() {
        System.out.println("[info] Creating a Web Driver for Appium");
        String serverLocation = "";

        // see http://appium.io/docs/en/writing-running-appium/caps/
        DesiredCapabilities capabilities = new DesiredCapabilities();
        try {
            serverLocation = ContextOfTest.testConfiguration.getProperty("appiumServerURL");

            // see http://appium.io/docs/en/writing-running-appium/caps/
            // Browser name should be blank if you are automating an app,
            // otherwise 'Safari' for iOS and 'Chrome', 'Chromium', or 'Browser' for Android, just like platform name
            String myBrowserName = ContextOfTest.testConfiguration.getProperty("actorName");
            capabilities.setCapability("BROWSER_NAME", myBrowserName.equalsIgnoreCase("android") ? "" : myBrowserName);

            capabilities.setCapability("VERSION", ContextOfTest.testConfiguration.getProperty("PlatformVersion"));
            capabilities.setCapability("deviceName", ContextOfTest.testConfiguration.getProperty("deviceName"));
            capabilities.setCapability("platformName", ContextOfTest.testConfiguration.getProperty("actorName"));
            capabilities.setCapability("udid", ContextOfTest.testConfiguration.getProperty("UDID"));

            capabilities.setCapability("buildToolsVersion", "29.0.2");

            capabilities.setCapability("appPackage", ContextOfTest.sutConfiguration.getProperty("appPackage"));
            capabilities.setCapability("appActivity", ContextOfTest.sutConfiguration.getProperty("appActivity"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            Assert.fail("All required properties must be correctly defined");
        }

        // see http://appium.io/docs/en/writing-running-appium/other/reset-strategies/index.html
        // the default may be more appropriate for normal testing - the following two are mutually exclusive :~
        capabilities.setCapability("noReset", "true");
        //capabilities.setCapability("fullReset", "true");

        URL serverURL = null;
        try {
            serverURL = new URL(serverLocation + "/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Assert.fail("Server Location (" + serverLocation + "/wd/hub" + ") should be an acceptable URL");
        }

        try {
            driver = new AndroidDriver(serverURL, capabilities);
        } catch (org.openqa.selenium.remote.UnreachableBrowserException e) {
            e.printStackTrace();
            Assert.fail("Is the device connected and the server running?");
        }

        driver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
    }
}