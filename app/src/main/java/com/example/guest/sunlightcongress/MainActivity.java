package com.example.guest.sunlightcongress;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private Legislator mLegislator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String apiKey = "b77ebc6b16a64f1ab2a2a1c8d0271963";
        double latitude = 37.8267;
        double longitude = -122.423;
        String congressURL = "https://congress.api.sunlightfoundation.com" + apiKey + "/" + latitude + "," + longitude;

        if (isNetworkAvailable()) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(congressURL)
                    .build();

            com.squareup.okhttp.Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(Response response) throws IOException {
                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            mLegislator = getCurrentDetails(jsonData);
                        } else {
                            alertUserAboutError();
                        }
                    }
                    catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                    catch (JSONException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                }
            });
        }
        else {
            Toast.makeText(this, R.string.network_error_msg, Toast.LENGTH_LONG).show();
        }
        Log.d(TAG, "Main UI code is running");
    }

    public Legislator getCurrentDetails(String jsonData) throws JSONException {
        JSONObject legislatorDetails = new JSONObject(jsonData);
        String zipCode = legislatorDetails.getString("zipCode");
        Log.i(TAG, "From JSON: " + zipCode);

        JSONObject currently = legislatorDetails.getJSONObject("currently");

        Legislator legislator = new Legislator();
        legislator.setZipCode(currently.getString("zipcode"));
        legislator.setFirstName(currently.getString("firstName"));
        legislator.setLastName(currently.getString("lastName"));

        return legislator;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailble = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailble = true;
        }
        return isNetworkAvailable();
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }
}

