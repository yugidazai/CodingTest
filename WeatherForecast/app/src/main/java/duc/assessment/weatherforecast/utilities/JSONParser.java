package duc.assessment.weatherforecast.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import duc.assessment.weatherforecast.models.SimpleWeatherForecastInfo;

/**
 * Created by ducnguyen on 18/11/2015.
 * This class is to receive and parse a JSONObject to retrieve the detailed data of forecast weather
 * (for currently, minutely, hourly and daily) in order to display UI on device's screen.
 * A jsonObject from forecast weather service is described as in
 * https://developer.forecast.io/docs/v2#forecast_call
 */
public class JSONParser {

    private static final String TIMEZONE_KEY = "timezone";
    private static final String OFFSET_KEY = "offset";
    private static final String CURRENTLY_KEY = "currently";
    private static final String MINUTELY_KEY = "minutely";
    private static final String HOURLY_KEY = "hourly";
    private static final String DAILY_KEY = "daily";
    private static final String SUMMARY_KEY = "summary";
    private static final String ICON_KEY = "icon";

    public static SimpleWeatherForecastInfo parseCurrentlyResult(JSONObject jsonObj) {
        if (jsonObj != null) {
            try {
                return parseSimpleDataPoint(jsonObj.getJSONObject(CURRENTLY_KEY));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /* Get simple info with summary and icon from a data point JSONObject.
     * From https://developer.forecast.io/docs/v2#forecast_call,
     * "A data point object represents the various weather phenomena occurring
     * at a specific instant of time, and has many varied properties".
     * */
    private static SimpleWeatherForecastInfo parseSimpleDataPoint(JSONObject jsonObject) throws JSONException{
        return new SimpleWeatherForecastInfo(
                jsonObject.getString(SUMMARY_KEY), jsonObject.getString(ICON_KEY));
    }
}
