package duc.assessment.weatherforecast.utilities;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by ducnguyen on 18/11/2015.
 * Unit tests for JSONDownloader class.
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class JSONDownloaderTest {
    @Test
    public void getJSONObjectFromApi_returnsCorrectJSONObject() throws Exception {
        JSONObject result = JSONDownloader.getJSONObjectFromApi(
                "https://api.forecast.io/forecast/e853da9b766bfeae67d9d518abf8d7c0/37.8267,-122.423");
        assertEquals("Test downloading correct data", "America/Los_Angeles", result.getString("timezone"));
    }

    @Test
    public void getJSONObjectFromApi_wrongAPIKey() throws Exception {
        JSONObject result = JSONDownloader.getJSONObjectFromApi(
                "https://api.forecast.io/forecast/invalidapikey/37.8267,-122.423");
        assertEquals("Test wrong API key", null, result);
    }

    @Test
    public void getJSONObjectFromApi_invalidLocation() throws Exception {
        JSONObject result = JSONDownloader.getJSONObjectFromApi(
                "https://api.forecast.io/forecast/e853da9b766bfeae67d9d518abf8d7c0/1234567");
        assertEquals("Test invalid location", null, result);
    }
}