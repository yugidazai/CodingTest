package duc.assessment.weatherforecast.utilities;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;

import duc.assessment.weatherforecast.models.SimpleWeatherForecastInfo;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by ducnguyen on 18/11/2015.
 * Unit tests for JSONParser class.
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class JSONParserTest {

    private static final String JSON_TEST_STRING =
            "{\"timezone\":\"America/Los_Angeles\",\"offset\":-8," +
            "\"currently\":{\"summary\":\"Partly Cloudy\",\"icon\":\"partly-cloudy-night\"}" +
            ",\"minutely\":{\"summary\":\"Partly cloudy for the hour.\",\"icon\":\"partly-cloudy-night\"}" +
            ",\"hourly\":{\"summary\":\"Partly cloudy throughout the day.\",\"icon\":\"partly-cloudy-day\"}" +
            ",\"daily\":{\"summary\":\"Rain on Tuesday, with temperatures peaking at 68°F on Friday.\",\"icon\":\"rain\"}}";

    @Mock
    JSONObject mMockJSONObj = mock(JSONObject.class);

    /* parseTimeZone method */
    @Test
    public void parseTimezone_returnsCorrectTimezone() throws Exception {
        when(mMockJSONObj.getString("timezone")).thenReturn("America/Los_Angeles");
        String result = JSONParser.parseTimezone(mMockJSONObj);
        assertEquals("Test parsing correct timezone", "America/Los_Angeles", result);
    }

    @Test
    public void parseTimezone_nullJSONObj() throws Exception {
        String result = JSONParser.parseTimezone(null);
        assertEquals("Test parsing Timezone with null JSONObject", null, result);
    }

    /* parseOffset method */
    @Test
    public void parseOffset_returnsCorrectOffset() throws Exception {
        when(mMockJSONObj.getString("offset")).thenReturn("-8");
        String result = JSONParser.parseOffset(mMockJSONObj);
        assertEquals("Test parsing correct offset", "-8", result);
    }

    @Test
    public void parseOffset_nullJSONObj() throws Exception {
        String result = JSONParser.parseOffset(null);
        assertEquals("Test parsing offset with null JSONObject", null, result);
    }

    /* parseCurrentlyInfo method */
    @Test
    public void parseCurrentlyInfo_returnsCorrectInfo() throws Exception {
        String expectedSummary = "Partly Cloudy";
        String expectedIcon = "partly-cloudy-night";
        prepareMockJSONObject(expectedSummary, expectedIcon);
        SimpleWeatherForecastInfo result = JSONParser.parseCurrentlyInfo(mMockJSONObj);
        assertEquals("Test parsing correct \"currently\" summary",
                expectedSummary, result.getSummary());
        assertEquals("Test parsing correct \"currently\" icon",
                expectedIcon, result.getIcon());
    }

    @Test
    public void parseCurrentlyInfo_nullJSONObj() throws Exception {
        SimpleWeatherForecastInfo result = JSONParser.parseCurrentlyInfo(null);
        assertEquals("Test parsing \"currently\" info with null JSONObject", null, result);
    }

    @Test
    public void parseCurrentlyInfo_withNoCurrentlyKey() throws Exception {
        prepareMockJSONObject();
        SimpleWeatherForecastInfo result = JSONParser.parseCurrentlyInfo(mMockJSONObj);
        assertThat("Test parsing no \"currently\" key with summary info", result.getSummary(), is(""));
        assertThat("Test parsing no \"currently\" key with icon info", result.getIcon(), is(""));
    }

    /* parseMinutelyInfo method */
    @Test
    public void parseMinutelyInfo_returnsCorrectInfo() throws Exception {
        String expectedSummary = "Partly cloudy for the hour.";
        String expectedIcon = "partly-cloudy-night";
        prepareMockJSONObject(expectedSummary, expectedIcon);
        SimpleWeatherForecastInfo result = JSONParser.parseMinutelyInfo(mMockJSONObj);
        assertEquals("Test parsing correct \"minutely\" summary",
                expectedSummary, result.getSummary());
        assertEquals("Test parsing correct \"minutely\" icon",
                expectedIcon, result.getIcon());
    }

    @Test
    public void parseMinutelyInfo_nullJSONObj() throws Exception {
        SimpleWeatherForecastInfo result = JSONParser.parseMinutelyInfo(null);
        assertEquals("Test parsing \"minutely\" info with null JSONObject", null, result);
    }

    @Test
    public void parseMinutelyInfo_withNoMinutelyKey() throws Exception {
        prepareMockJSONObject();
        SimpleWeatherForecastInfo result = JSONParser.parseMinutelyInfo(mMockJSONObj);
        assertThat("Test parsing no \"minutely\" key with summary info", result.getSummary(), is(""));
        assertThat("Test parsing no \"minutely\" key with icon info", result.getIcon(), is(""));
    }

    /* parseHourlyInfo method */
    @Test
    public void parseHourlyInfo_returnsCorrectInfo() throws Exception {
        String expectedSummary = "Partly cloudy throughout the day.";
        String expectedIcon = "partly-cloudy-day";
        prepareMockJSONObject(expectedSummary, expectedIcon);
        SimpleWeatherForecastInfo result = JSONParser.parseHourlyInfo(mMockJSONObj);
        assertEquals("Test parsing correct \"hourly\" summary",
                expectedSummary, result.getSummary());
        assertEquals("Test parsing correct \"hourly\" icon",
                expectedIcon, result.getIcon());
    }

    @Test
    public void parseHourlyInfo_nullJSONObj() throws Exception {
        SimpleWeatherForecastInfo result = JSONParser.parseHourlyInfo(null);
        assertEquals("Test parsing \"hourly\" info with null JSONObject", null, result);
    }

    @Test
    public void parseHourlyInfo_withNoHourlyKey() throws Exception {
        prepareMockJSONObject();
        SimpleWeatherForecastInfo result = JSONParser.parseHourlyInfo(mMockJSONObj);
        assertThat("Test parsing no \"hourly\" key with summary info", result.getSummary(), is(""));
        assertThat("Test parsing no \"hourly\" key with icon info", result.getIcon(), is(""));
    }

    /* parseHourlyInfo method */
    @Test
    public void parseDailyInfo_returnsCorrectInfo() throws Exception {
        String expectedSummary = "Rain on Tuesday, with temperatures peaking at 68°F on Friday.";
        String expectedIcon = "rain";
        prepareMockJSONObject(expectedSummary, expectedIcon);
        SimpleWeatherForecastInfo result = JSONParser.parseDailyInfo(mMockJSONObj);
        assertEquals("Test parsing correct \"daily\" summary",
                expectedSummary, result.getSummary());
        assertEquals("Test parsing correct \"daily\" icon",
                expectedIcon, result.getIcon());
    }

    @Test
    public void parseDailyInfo_nullJSONObj() throws Exception {
        SimpleWeatherForecastInfo result = JSONParser.parseDailyInfo(null);
        assertEquals("Test parsing \"daily\" info with null JSONObject", null, result);
    }

    @Test
    public void parseDailyInfo_withNoDailyKey() throws Exception {
        prepareMockJSONObject();
        SimpleWeatherForecastInfo result = JSONParser.parseDailyInfo(mMockJSONObj);
        assertThat("Test parsing no \"daily\" key with summary info", result.getSummary(), is(""));
        assertThat("Test parsing no \"daily\" key with icon info", result.getIcon(), is(""));
    }

    private void prepareMockJSONObject() throws JSONException {
        JSONObject mockDataPoint = mock(JSONObject.class);
        when(mMockJSONObj.getJSONObject(anyString())).thenReturn(mockDataPoint);
        when(mockDataPoint.getString(anyString())).thenReturn("");
    }

    private void prepareMockJSONObject(String summaryString, String iconType) throws Exception {
        JSONObject mockDataPoint = mock(JSONObject.class);
        when(mMockJSONObj.getJSONObject(anyString())).thenReturn(mockDataPoint);
        when(mockDataPoint.getString("summary")).thenReturn(summaryString);
        when(mockDataPoint.getString("icon")).thenReturn(iconType);
    }
}