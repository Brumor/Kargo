package com.app.kargo;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by pbric on 04/09/2017.
 */

public class GeocodeAddressIntentService extends IntentService {


    protected ResultReceiver resultReceiver;
    private static final String TAG = "GEOCODE_SERVICE";

    public GeocodeAddressIntentService() {
        super("GeocodeAddressIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e(TAG, "onHandleIntent");
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        String errorMessage = "";
        List<Address> addresses = null;


        String name = intent.getStringExtra("LOCATION_NAME_DATA_EXTRA");

        try {
            addresses = geocoder.getFromLocationName(name, 1);
        } catch (IOException e) {
            errorMessage = "Service not available";
            Log.e(TAG, errorMessage, e);
        }

        resultReceiver = intent.getParcelableExtra("RECEIVER");
        if (addresses == null || addresses.size()  == 0) {
            if (errorMessage.isEmpty()) {
                errorMessage = "Not Found";
                Log.e(TAG, errorMessage);
            }
            deliverResultToReceiver(1, errorMessage, null);
        } else {
            for(Address address : addresses) {
                String outputAddress = "";
                for(int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    outputAddress += " --- " + address.getAddressLine(i);
                }
                Log.e(TAG, outputAddress);
            }
            Address address = addresses.get(0);
            ArrayList<String> addressFragments = new ArrayList<>();

            for(int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                addressFragments.add(address.getAddressLine(i));
            }
            Log.e(TAG, "Address Found");
            deliverResultToReceiver(0,
                    TextUtils.join(System.getProperty("line.separator"),
                            addressFragments), address);
        }
    }

    private void deliverResultToReceiver(int resultCode, String message, Address address) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("RESULT_ADDRESS", address);
        bundle.putString("RESULT_DATA_KEY", message);
        resultReceiver.send(resultCode, bundle);
    }

}
