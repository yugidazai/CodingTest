package duc.assessment.weatherforecast.utilities;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ducnguyen on 18/11/2015.
 * This class is to open HttpURLConnection of given API with location value (latitude, langitude)
 * to retrieve data as a JSON object
 */
public class JSONDownloader {
    private static final String TAG = JSONDownloader.class.getSimpleName();

    public static JSONObject getJSONObjectFromApi(String api) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(api);
            connection = (HttpURLConnection) url.openConnection();

            if (connection != null) {
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_UNAVAILABLE
                        || responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
                    return null;
                }
                // get data stream from URL then read data by using BufferReader
                InputStream inputStream = connection.getInputStream();
                BufferedReader buffReader = new BufferedReader(new InputStreamReader(inputStream));

                // StringBuilder to store the retrieved JSON string from data stream
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = buffReader.readLine()) != null) {
                    sb.append(line);
                }

                buffReader.close();
                inputStream.close();

                return new JSONObject(sb.toString());
            }
        } catch (JSONException e) {
            Log.e(TAG, "Cannot parse data string to JSON object: " + e.toString());
        } catch (MalformedURLException e) {
            Log.e(TAG, "Wrong format URL:" + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return null;
    }
}
