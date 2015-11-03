package com.example.guest.sunlightcongress;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private Button mZipCodeButton;
    private EditText mEnteripCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String apiKey = "b77ebc6b16a64f1ab2a2a1c8d0271963";
        String congressURL = "congress.api.sunlightfoundation.com/legislators/locate?zip" + apiKey + "/";

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
                            mLegislator = getLegislatorDetails(jsonData);
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

    public Legislator getLegislatorDetails(String jsonData) throws JSONException {
        JSONObject legislatorDetails = new JSONObject(jsonData);
        String firstName = legislatorDetails.getString("first_name");
        String lastName = legislatorDetails.getString("last_name");

        //Log.i(TAG, "From JSON: " + zipCode);

        JSONObject currently = legislatorDetails.getJSONObject("currently");

        Legislator legislator = new Legislator();

        legislator.setFirstName(currently.getString("first_name"));
        legislator.setLastName(currently.getString("last_name"));

        return legislator;


        mZipCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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

