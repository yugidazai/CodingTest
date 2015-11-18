package duc.assessment.weatherforecast.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import duc.assessment.weatherforecast.R;
import duc.assessment.weatherforecast.models.SimpleWeatherForecastInfo;
import duc.assessment.weatherforecast.utilities.TextViewHelper;

public class WeatherForecastActivity extends AppCompatActivity {

    private static final String FORECAST_LINK = "http://forecast.io/";
    private static final String APIKEY = "e853da9b766bfeae67d9d518abf8d7c0";
    private static final String DATA_URL_FORMAT = "https://api.forecast.io/forecast/" + APIKEY + "/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        loadForecastWeather();

        findViewById(R.id.refresh_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadForecastWeather();
            }
        });
        setCreditSection();
    }

    private void loadForecastWeather() {

    }

    private void setCreditSection() {
        TextViewHelper.createHyperLink(
                (TextView) findViewById(R.id.credit_section), FORECAST_LINK,
                getString(R.string.credit_display_text));
    }

    private int getWeatherIconId(SimpleWeatherForecastInfo.WeatherType iconType) {
        switch (iconType) {
            case CLEAR_DAY:
                return R.drawable.clear_day;
            case CLEAR_NIGHT:
                return R.drawable.clear_night;
            case RAIN:
                return R.drawable.rain;
            case SNOW:
                return R.drawable.snow;
            case SLEET:
                return R.drawable.sleet;
            case WIND:
                return R.drawable.wind;
            case FOG:
                return R.drawable.fog;
            case CLOUDY:
                return R.drawable.cloudy;
            case PARTLY_CLOUDY_DAY:
                return R.drawable.partly_cloudy_day;
            case PARTLY_CLOUDY_NIGHT:
                return R.drawable.partly_cloudy_night;
            default:
                return R.drawable.clear_day;
        }
    }
}
