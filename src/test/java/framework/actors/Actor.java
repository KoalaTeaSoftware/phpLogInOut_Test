package framework.actors;

import framework.ContextOfScenario;
import framework.ContextOfTest;
import io.cucumber.java.Scenario;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public abstract class Actor {

    protected static Integer pageLoadWait;
    protected static int implicitWait;
    protected CloseableHttpResponse httpResponse;
    protected WebDriver driver;

    /**
     * Constructor for the generic type.
     * Sets up some environment configuration, so as to keep this stuff in one place
     */
    public Actor() {
        try {
            ContextOfTest.catchBrowserLogs = ContextOfTest.testConfiguration.getProperty("catchBrowserLogs").equalsIgnoreCase("true");
        } catch (NoSuchFieldException e) {
            writeToHtmlReport("[info] Won't be catching browser logs");
            ContextOfTest.catchBrowserLogs = false;
        }

        try {
            implicitWait = Integer.parseInt(ContextOfTest.testConfiguration.getProperty("implicitWaitTime"));
        } catch (NoSuchFieldException e) {
            implicitWait = 0;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Assert.fail("Unable to understand the implicitWaitTime test configuration value");
        }

        try {
            pageLoadWait = Integer.valueOf(ContextOfTest.testConfiguration.getProperty("waitForPageLoad"));
        } catch (NoSuchFieldException e) {
            pageLoadWait = 0;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Assert.fail("Unable to understand the waitForPageLoad test configuration value");
        }

        try {
            ContextOfTest.sutBaseURL = deriveBaseUrl();
        } catch (NoSuchFieldException e) {
            // so as not to complicate matters, don't do any more checking here, but make a point.
            ContextOfTest.sutBaseURL = "";
            writeToHtmlReport("[info] Protocol and domain name are not defined");
        }

        httpResponse = null;
    }

    /**
     * For browsers this is what you think, for an API it will grab the response
     *
     * @param scenario - found in the scenario context
     * @param label    - what you want to se written in the report
     */
    public void embedScreenShot(Scenario scenario, String label) {
        scenario.write(label);

        TakesScreenshot ts = (TakesScreenshot) ContextOfScenario.driver;
        byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);

        // whilst this is complaining the embed is deprecated, it still works
        scenario.embed(screenshot, "image/png");
    }

    /**
     * Just what is says on the tin.
     *
     * @param message - what you ant to see in the report
     */
    public void writeToHtmlReport(String message) {
        ContextOfScenario.scenario.write(message);
    }

    /**
     * Get the driver so you can do all of the interesting stuff that Selenium lets you do
     * If necessary, it will make a driver for you
     *
     * @return - A working web driver
     */
    public WebDriver getDriver() {
        if (null == driver) {
            startService();
            createDriver();
        }
        return driver;
    }

    /**
     * This is only intended for use by the framework to allow it to isolate each Scenario
     * It should not be used elsewhere, unless under extraordinary circumstances
     */
    public void closeDriver() {
        if (null != driver) {
            System.out.println("[info] Closing driver");
            driver.quit();
            driver = null; // Essential to do this, else you keep getting the dead driver
        } else
            // maybe something has gone wrong with the framework?
            System.out.println("[info] No driver to close");
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Most of the different types of actor will have to do a different something at each of these points in the in the test lifecycle
    // So force them to be created

    /**
     * The code below is appropriate to web pages that are slow to load
     * The different actors may Override this to implement their own, appropriate version of this
     *
     * @param fullURL - of the page that you want to see. Can be relative
     */
    public void getResource(String fullURL) {
        getDriver().get(fullURL);

        awaitPageLoad(pageLoadWait);
    }

    /**
     * This one works for all of the browsers
     * Override it for the API Actor
     *
     * @return
     * @throws NoSuchFieldException
     */
    public String deriveBaseUrl() throws NoSuchFieldException {
        return ContextOfTest.sutConfiguration.getProperty("uiProtocol") + "://" + ContextOfTest.sutConfiguration.getProperty("uiDomainName");
    }

    /**
     * Use this with the API Actor is the operating Actor, so that the response can be analysed
     * Otherwise (e.g. for th browser actors) the response will be null (initial value of the httpResponse)
     *
     * @return
     */
    public CloseableHttpResponse getResponse() {
        return httpResponse;
    }

    protected abstract void startService();

    protected abstract void stopService();

    protected abstract void createDriver();

    /**
     * When looking at web pages, the implicit wait may not be sufficient,
     * so explicitly ask the browser if it thinks it has got everything
     *
     * @param maxWaitSeconds -
     */
    private void awaitPageLoad(int maxWaitSeconds) {
        if (maxWaitSeconds == 0)
            return; // don't even create the executor

        String state = "";
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        if (js != null)
            for (int i = 0; i < maxWaitSeconds; i++) {
                try {
                    state = js.executeScript("return document.readyState").toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    writeToHtmlReport("Failed to get document state " + e.getMessage());
                    // Right at the beginning, if the browser has got nothing yet.
                    // we may hit "org.openqa.selenium.JavascriptException: javascript error: Cannot read property 'outerHTML' of null"
                    // In this case, we do not stop waiting.
                }
                if (state.equals("complete")) {
                    return;
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignored) {
                        // don't care
                    }
                }
            }
        else {
            System.out.println("[warning] unable to execute JavaScript to determine if page has loaded");
            writeToHtmlReport("[warning] unable to execute JavaScript to determine if page has loaded");
        }
    }

    /**
     * Choose one of these when you instantiate your actor
     */
    public enum ActorType {
        FIREFOX,
        CHROME,
        INTERNET_EXPLORER,
        SAFARI,
        ANDROID, // actually using android to do things, ie an app
        IOS, // also an app
        API
    }
}
