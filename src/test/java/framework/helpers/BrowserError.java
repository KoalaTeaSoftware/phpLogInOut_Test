package framework.helpers;

import framework.ContextOfTest;
import org.junit.Assert;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * To help with understanding errors detected by the browser
 */
public class BrowserError {
    public final String level;
    public final Date timeStamp;
    public final String message;

    public BrowserError(LogEntry jsEntry) {
        this.level = jsEntry.getLevel().getName();
        this.timeStamp = new Date(jsEntry.getTimestamp());
        this.message = jsEntry.getMessage();
    }

    public String toString() {
        return String.format("Level:%s\nTimestamp:%s\nMessage:%s", level, timeStamp.toString(), message);
    }

    /* *
     * The information that would appear in the console log
     *
     * @return - list of browser errors
     */
    public List<BrowserError> analyzeBrowserLog() {
        List<BrowserError> errorList = new ArrayList<>();

        if (ContextOfTest.catchBrowserLogs) {
            // use the driver from the context as anything else gives you scope nightmares
            LogEntries logEntries = ContextOfTest.actor.getDriver().manage().logs().get(LogType.BROWSER);
            for (LogEntry entry : logEntries) {
                // https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/logging/LogEntry.html
                errorList.add(new BrowserError(entry));

            }
        } else {
            Assert.fail("Set catchBrowserLogs=true in the test framework properties before you try to check them.");
        }
        return errorList;
    }

}
