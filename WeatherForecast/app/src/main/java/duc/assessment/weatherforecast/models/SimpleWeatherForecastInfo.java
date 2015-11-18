package duc.assessment.weatherforecast.models;

/**
 * Created by ducnguyen on 18/11/2015.
 */
public class SimpleWeatherForecastInfo {

    private String mSummary;
    private WeatherType mIconType;

    public SimpleWeatherForecastInfo(String mSummary, String iconType) {
        this.mSummary = mSummary;
        this.mIconType = WeatherType.valueOf(iconType);
    }

    public String getSummary() {
        return mSummary;
    }

    public WeatherType getIcon() {
        return mIconType;
    }

    public enum WeatherType {
        CLEAR_DAY("clear-day"),
        CLEAR_NIGHT("clear-night"),
        RAIN("rain"),
        SNOW("snow"),
        SLEET("fleet"),
        WIND("wind"),
        FOG("fog"),
        CLOUDY("cloudy"),
        PARTLY_CLOUDY_DAY("partly-cloudy-day"),
        PARTLY_CLOUDY_NIGHT("partly-cloudy-night");

        String value;
        WeatherType(String value) {
            this.value = value;
        }
    }
}
