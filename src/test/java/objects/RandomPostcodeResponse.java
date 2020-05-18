package objects;

import framework.objects.JsonApiResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.json.JSONObject;

/*
See http://api.postcodes.io/docs
GET https://api.postcodes.io/random/postcodes
Example response:
{
    "status":200,
    "result":{
        "postcode":"CF42 6LR", "quality":1, "eastings":294871, "northings":195994, "country":"Wales",
        "nhs_ha": "Cwm Taf Morgannwg University Health Board", "longitude":-3.521033, "latitude": 51.652874,
        "european_electoral_region":"Wales", "primary_care_trust": "Cwm Taf Morgannwg University Health Board",
        "region":null, "lsoa":"Rhondda Cynon Taf 010C", "msoa": "Rhondda Cynon Taf 010", "incode":"6LR", "outcode":"CF42",
        "parliamentary_constituency": "Rhondda", "admin_district":"Rhondda Cynon Taf", "parish":"Treorchy", "admin_county":null,
        "admin_ward": "Treorchy", "ced":null, "ccg":"Cwm Taf Morgannwg University Health Board", "nuts":"Central Valleys",
        "codes":{
            "admin_district":"W06000016", "admin_county":"W99999999", "admin_ward":"W05000701", "parish":
            "W04000708", "parliamentary_constituency":"W07000052", "ccg":"W11000030", "ccg_id":"7A5", "ced":
            "W99999999", "nuts":"UKL15"
        }
    }
}
*/

public class RandomPostcodeResponse extends JsonApiResponse {

    private JSONObject responseAsJson = null;

    public RandomPostcodeResponse(CloseableHttpResponse response) {
        super(response);
        responseAsJson = this.getBodyAsJsonObject();
    }

    public String getPostcode() {
        return responseAsJson.getJSONObject("result").get("postcode").toString();
    }

}
