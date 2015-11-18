package duc.assessment.weatherforecast.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import duc.assessment.weatherforecast.R;
import duc.assessment.weatherforecast.models.SimpleWeatherForecastInfo;
import duc.assessment.weatherforecast.utilities.JSONDownloader;
import duc.assessment.weatherforecast.utilities.JSONParser;
import duc.assessment.weatherforecast.utilities.TextViewHelper;

public class WeatherForecastActivity extends Activity {

    private static final int MIN_TIME_FOR_REFRESH_LOCATION = 0; // 15 sec
    private static final int MIN_DISTANCE_FOR_REFRESH_LOCATION = 0;
    private static final String FORECAST_LINK = "http://forecast.io/";
    private static final String CLEAR_DAY = "clear-day";
    private static final String CLEAR_NIGHT = "clear-night";
    private static final String RAIN = "rain";
    private static final String SNOW = "snow";
    private static final String SLEET = "fleet";
    private static final String WIND = "wind";
    private static final String FOG = "fog";
    private static final String CLOUDY = "cloudy";
    private static final String PARTLY_CLOUDY_DAY = "partly-cloudy-day";
    private static final String PARTLY_CLOUDY_NIGHT = "partly-cloudy-night";

    private ProgressDialog mProgressDialog;
    private Location mDeviceLocation;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;

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

    private void loadCurrentLocation() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mDeviceLocation = location;
                startDownloadInfo();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (updateLocation()) return;

        if (mDeviceLocation == null) {
            // still cannot get location here, so use the example (37.8267,-122.423)
            new DataDownloadAsyncTask().execute(37.8267, -122.423);
        }
    }

    private boolean updateLocation() {
        String gpsProvider = LocationManager.GPS_PROVIDER;
        String networkProvider = LocationManager.NETWORK_PROVIDER;
        boolean networkProviderEnabled = mLocationManager.isProviderEnabled(networkProvider);
        boolean gpsProviderEnabled = mLocationManager.isProviderEnabled(gpsProvider);
        if (!networkProviderEnabled && !gpsProviderEnabled) {
            dismissProgressDialog();
            Toast.makeText(this, getString(R.string.fail_to_load_location), Toast.LENGTH_LONG).show();
            return true;
        }
        if (networkProviderEnabled) {
            loadLocation(mLocationManager, networkProvider);
        }
        if (gpsProviderEnabled) {
            loadLocation(mLocationManager, gpsProvider);
        }
        return false;
    }

    private void startDownloadInfo() {
        if (mDeviceLocation != null) {
            new DataDownloadAsyncTask().execute(
                    mDeviceLocation.getLatitude(), mDeviceLocation.getLongitude());
        }
    }

    private void loadLocation(LocationManager locationManager, String provider) {
        locationManager.requestLocationUpdates(
                provider
                , MIN_TIME_FOR_REFRESH_LOCATION
                , MIN_DISTANCE_FOR_REFRESH_LOCATION
                , mLocationListener);
        mDeviceLocation = locationManager.getLastKnownLocation(provider);
    }

    private void loadForecastWeather() {
        // only display progress dialog once at the beginning
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.progress_dialog_msg));
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }
        if (mDeviceLocation == null) { // initialize
            loadCurrentLocation();
        }
    }

    private void setCreditSection() {
        TextViewHelper.createHyperLink(
                (TextView) findViewById(R.id.credit_section), FORECAST_LINK,
                getString(R.string.credit_display_text));
    }

    private int getWeatherIconId(String iconType) {
        if (CLEAR_DAY.equals(iconType)) {
            return R.drawable.clear_day;
        } else if (CLEAR_NIGHT.equals(iconType)) {
            return R.drawable.clear_night;
        } else if (RAIN.equals(iconType)) {
            return R.drawable.rain;
        } else if (SNOW.equals(iconType)) {
            return R.drawable.snow;
        } else if (SLEET.equals(iconType)) {
            return R.drawable.sleet;
        } else if (WIND.equals(iconType)) {
            return R.drawable.wind;
        } else if (FOG.equals(iconType)) {
            return R.drawable.fog;
        } else if (CLOUDY.equals(iconType)) {
            return R.drawable.cloudy;
        } else if (PARTLY_CLOUDY_DAY.equals(iconType)) {
            return R.drawable.partly_cloudy_day;
        } else if (PARTLY_CLOUDY_NIGHT.equals(iconType)) {
            return R.drawable.partly_cloudy_night;
        } else {
            return R.drawable.clear_day;
        }
    }

    private void updateUI(JSONObject jsonObject) {
        if (jsonObject == null) {
            Toast.makeText(this, getString(R.string.fail_to_load_msg), Toast.LENGTH_LONG).show();
        } else {
            setText(R.id.timezone_text, JSONParser.parseTimezone(jsonObject));
            setText(R.id.offset_text, "GMT " + JSONParser.parseOffset(jsonObject));

            SimpleWeatherForecastInfo currentlyInfo = JSONParser.parseCurrentlyInfo(jsonObject);
            setText(R.id.current_summary_text, currentlyInfo.getSummary());
            setIcon(R.id.current_icon, currentlyInfo.getIcon());

            SimpleWeatherForecastInfo minutelyInfo = JSONParser.parseMinutelyInfo(jsonObject);
            setText(R.id.minutely_summary_text, minutelyInfo.getSummary());
            setIcon(R.id.minutely_icon, minutelyInfo.getIcon());

            SimpleWeatherForecastInfo hourlyInfo = JSONParser.parseHourlyInfo(jsonObject);
            setText(R.id.hourly_summary_text, hourlyInfo.getSummary());
            setIcon(R.id.hourly_icon, hourlyInfo.getIcon());

            SimpleWeatherForecastInfo dailyInfo = JSONParser.parseDailyInfo(jsonObject);
            setText(R.id.daily_summary_text, dailyInfo.getSummary());
            setIcon(R.id.daily_icon, dailyInfo.getIcon());
        }

    }

    private void setText(int textViewResId, String text) {
        ((TextView) findViewById(textViewResId)).setText(text);
    }

    private void setIcon(int imageViewResId, String iconType) {
        ((ImageView) findViewById(imageViewResId)).setImageResource(getWeatherIconId(iconType));
    }

    class DataDownloadAsyncTask extends AsyncTask<Double, Void, JSONObject> {

        private static final String APIKEY = "e853da9b766bfeae67d9d518abf8d7c0";
        private static final String WEATHER_URL_FORMAT = "https://api.forecast.io/forecast/" + APIKEY + "/";

        @Override
        protected JSONObject doInBackground(Double... params) {
            // Get JSON object from url with current location (latitude, longitude),
            // then parse JSON object to get a summary and icon data of "currently", "minutely", "hourly" and "daily"
            return JSONDownloader.getJSONObjectFromApi(
                    WEATHER_URL_FORMAT + params[0] + ',' + params[1]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            dismissProgressDialog();

            updateUI(result);
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationManager.removeUpdates(mLocationListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateLocation();
    }
}
