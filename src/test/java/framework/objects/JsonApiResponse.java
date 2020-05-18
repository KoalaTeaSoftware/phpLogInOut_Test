package framework.objects;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.junit.Assert;

import java.io.IOException;

/**
 * A wrapper, a bit like a Page Object to ease handling of API responses
 * Will mean the involvement of JSON and XML parsers
 * Avoid any code other than Selection of elements of the response
 */
public class JsonApiResponse {

    private final int responseCode;
    private String responseBodyString = "";

    public JsonApiResponse(CloseableHttpResponse response) {
        responseCode = response.getStatusLine().getStatusCode();
        HttpEntity responseBodyEntity = response.getEntity();
        try {
            responseBodyString = EntityUtils.toString(responseBodyEntity);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("Unable to get the body of the response");
        } catch (ParseException e) {
            e.printStackTrace();
            Assert.fail("Unable to understand the body of the response");
        }
    }

    public int getStatus() {
        return responseCode;
    }

    public String getBodyAsString() {
        return responseBodyString;
    }

    public JSONObject getBodyAsJsonObject() {
        return new JSONObject(getBodyAsString());
    }
}
