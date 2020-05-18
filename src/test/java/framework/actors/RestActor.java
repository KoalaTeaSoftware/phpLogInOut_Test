package framework.actors;

import framework.ContextOfTest;
import io.cucumber.java.Scenario;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;

import java.io.IOException;

public class RestActor extends Actor {

    private CloseableHttpClient service;

    @Override
    protected void startService() {
        service = HttpClients.createDefault();
    }

    @Override
    protected void stopService() {
        try {
            service.close();
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("The HTTP client should quietly close");
        }
    }

    @Override
    protected void createDriver() {
        // This will be called by the start scenario hook.
        // Just make sure that we have nothing left over from before
        httpResponse = null;
    }

    /**
     * Override the request that the Actor (parent) gives
     * so that we can use different route to getting
     *
     * @param url the complete url that you want to get from
     *            ToDo: PUT, POST etc.
     *            ToDo: headers - notice this one is assuming that the accept is to be sent
     */
    @Override
    public void getResource(String url) {
        try {
            this.writeToHtmlReport("GET from url:" + url + ":");
            HttpGet request = new HttpGet(url);
            request.addHeader("accept", "application/json");
            httpResponse = service.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * For this type of Actor, just dump the whole thing into the HTML report
     *
     * @param scenario - found in the scenario context
     * @param label    - what you want to se written in the report
     */
    @Override
    public void embedScreenShot(Scenario scenario, String label) {
        try {
            scenario.write(EntityUtils.toString(httpResponse.getEntity(), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
            scenario.write("Failed to interpret the response:" + e.getMessage() + ":");
            Assert.fail();
        }
    }

    /**
     * The API actor uses different properties (so that we can pint the test at the API just by changing the actorName in the framework properties)
     *
     * @return the Base url that can be used for all API calls
     * @throws NoSuchFieldException - if you have not adequately defined things in the SUT properties
     */
    @Override
    public String deriveBaseUrl() throws NoSuchFieldException {
        return ContextOfTest.sutConfiguration.getProperty("apiProtocol") + "://" + ContextOfTest.sutConfiguration.getProperty("apiDomainName");
    }

}
