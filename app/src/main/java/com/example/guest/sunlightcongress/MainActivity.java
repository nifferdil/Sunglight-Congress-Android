package com.example.guest.sunlightcongress;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guest.sunlightcongress.adapters.LegislatorAdapter;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends ListActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private Legislator mLegislator;
    private Button mZipCodeButton;
    private Button mPhoneButton;
    private EditText mEnterZipCode;
    private String mZipCode;
    private TextView mNameLabel;
    private TextView mPhoneLabel;
    private LegislatorAdapter mAdapter;
    private ArrayList<Legislator> mLegislators;
    private Button mFriendButton;
    static final int PICK_CONTACT = 1;  // The request code
    private String cNumber;
    static final int REQUEST_IMAGE_CAPTURE = 1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNameLabel = (TextView) findViewById(R.id.nameLabel);
        //mLastNameLabel = (TextView) findViewById(R.id.lastNameLabel);
        mZipCodeButton = (Button) findViewById(R.id.enterZipCodeButton);
        mPhoneButton = (Button) findViewById(R.id.photoButton);
        mEnterZipCode = (EditText) findViewById(R.id.enterZipCode);
        mFriendButton = (Button) findViewById(R.id.friendButton);
        mLegislators = new ArrayList<Legislator>();

        mAdapter = new LegislatorAdapter(this, mLegislators);
        setListAdapter(mAdapter);


        mZipCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mZipCode = mEnterZipCode.getText().toString();
                mEnterZipCode.setText("");
                findRepresentatives(mZipCode);

            }
        });


        mPhoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


//        mFriendButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {

//                private void pickContact() {
//                    Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
//                    pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
//                    startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
//                }

//                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
//                startActivityForResult(intent, PICK_CONTACT);
//            }
//        });
//
//    }
//
//    //code
//    @Override
//    public void onActivityResult(int reqCode, int resultCode, Intent data) {
//        super.onActivityResult(reqCode, resultCode, data);
//
//        switch (reqCode) {
//            case (PICK_CONTACT) :
//                if (resultCode == Activity.RESULT_OK) {
//
//                    Uri contactData = data.getData();
//                    Cursor c =  managedQuery(contactData, null, null, null, null);
//                    if (c.moveToFirst()) {
//
//
//                        String id =c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
//
//                        String hasPhone =c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
//
//                        if (hasPhone.equalsIgnoreCase("1")) {
//                            Cursor phones = getContentResolver().query(
//                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
//                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,
//                                    null, null);
//                            phones.moveToFirst();
//                            cNumber = phones.getString(phones.getColumnIndex("data1"));
//                            System.out.println("number is:"+cNumber);
//                        }
//                        String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//
//
//                    }
//                }
//                break;
//        }


    private void findRepresentatives(String zipCode) {
        String apiKey = "b77ebc6b16a64f1ab2a2a1c8d0271963";
        String congressURL = "https://congress.api.sunlightfoundation.com/legislators/locate?zip=" + zipCode + "&apikey=" + apiKey;

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
                            mLegislators = getLegislatorDetails(jsonData);
                            if(mLegislators != null){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mAdapter.notifyDataSetChanged();
                                }
                            });} else {alertUserAboutError();}
                        } else {
                            alertUserAboutError();
                        }
                    }
                    catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                        Toast.makeText(getApplicationContext(), "Could not find congresspeople :(", Toast.LENGTH_LONG).show();
                    }
                    catch (JSONException e) {
                        Log.e(TAG, "Exception caught: ", e);
                        Toast.makeText(getApplicationContext(), "Could not find congresspeople :(", Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
        else {
            Toast.makeText(this, R.string.network_error_msg, Toast.LENGTH_LONG).show();
        }
        Log.d(TAG, "Main UI code is running");

    }

//    private void updateDisplay() {
//        mNameLabel.setText(mLegislator.getFirstName());
//        mLastNameLabel.setText(mLegislator.getLastName());
//
//    }



    public ArrayList<Legislator> getLegislatorDetails(String jsonData) throws JSONException {

        JSONObject legislatorDetails = new JSONObject(jsonData);
        JSONArray representatives = legislatorDetails.getJSONArray("results");
        if (representatives.length() == 0){
            return null;
        }
        for (int i = 0; i < representatives.length(); i++) {
            JSONObject nextLegistlator = representatives.getJSONObject(i);

            String firstName = nextLegistlator.getString("first_name");
            String lastName = nextLegistlator.getString("last_name");
            String phone = nextLegistlator.getString("phone");
            String office = nextLegistlator.getString("office");
            String state = nextLegistlator.getString("state");

            Legislator legislator = new Legislator(firstName, lastName, phone, office, state);
            mLegislators.add(legislator);
        }

        return mLegislators;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }
}

