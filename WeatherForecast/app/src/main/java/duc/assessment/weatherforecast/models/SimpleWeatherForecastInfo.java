package duc.assessment.weatherforecast.models;

/**
 * Created by ducnguyen on 18/11/2015.
 */
public class SimpleWeatherForecastInfo {

    private String mSummary;
    private String mIconType;

    public SimpleWeatherForecastInfo(String mSummary, String iconType) {
        this.mSummary = mSummary;
        this.mIconType = iconType;
    }

    public String getSummary() {
        return mSummary;
    }

    public String getIcon() {
        return mIconType;
    }
}
