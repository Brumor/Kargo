package com.app.kargo.FragmentLayout;


import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.os.ResultReceiver;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.kargo.Adapter.CurrentDeliveriesRecyclerAdapter;
import com.app.kargo.Delivery;
import com.app.kargo.DeliveryConfirmActivity;
import com.app.kargo.DirectionsJSONParser;
import com.app.kargo.GeocodeAddressIntentService;
import com.app.kargo.MapsActivity;
import com.app.kargo.PackageCollectActivity;
import com.app.kargo.R;
import com.app.kargo.RecyclerItemClickListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentDeliveriesFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        LocationListener, GoogleApiClient.OnConnectionFailedListener{

    private RecyclerView recyclerView;
    private CurrentDeliveriesRecyclerAdapter adapter;
    LinearLayoutManager manager;

    private TextView pickupTextView;
    private TextView totalTimeTextView;
    private TextView totalPackageTextView;

    private LinearLayout collectBar;

    private CheckBox collectCheckBox;

    public final List<Delivery> deliveryList = new ArrayList<>();
    public List<Delivery> toDoList = new ArrayList<>();

    public int deliveryDone = 0;
    private Button NavButton;

    private Location mLastKnownLocation;
    private LocationRequest locationRequest;
    private FusedLocationProviderApi fusedLocationProviderApi;
    private GoogleApiClient googleApiClient;

    private AddressResultReceiver mResultReceiver;

    LatLng currentLatLng = new LatLng(45.7640, 4.8357);
    LatLng destinationLatLng;

    private int duration;


    public CurrentDeliveriesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View rootview =  inflater.inflate(R.layout.fragment_current_deliveries, container, false);

        totalTimeTextView = rootview.findViewById(R.id.total_time_textview);
        totalPackageTextView = rootview.findViewById(R.id.total_package_textview);
        pickupTextView = rootview.findViewById(R.id.pickup_textview);
        collectCheckBox = rootview.findViewById(R.id.collect_checkbox);
        NavButton = rootview.findViewById(R.id.gotonav_button);
        recyclerView = rootview.findViewById(R.id.recyclerView);
        collectBar = rootview.findViewById(R.id.collect_bar);

        manager = new LinearLayoutManager(getContext());
        mResultReceiver = new AddressResultReceiver(null);

        //Faux elements
        deliveryList.add(new Delivery(2, "Ikea, Boulevard André Bouloche, 69800 Saint-Priest", "Paris", "Mme Durand", 5, 1, false));
        deliveryList.add(new Delivery(1, "IKEA, Lyon", "Lyon", "Mr. Dupont", 3, 1, false));
        deliveryList.add(new Delivery(3, "Ikea Saint Priest", "Saint-Etienne", "Mr Martin",5, 2, false));
        deliveryList.add(new Delivery(4, "Ikea Saint Priest", "Marseille", "Mr Jean",3, 2, false));
        deliveryList.add(new Delivery(5, "Ikea Saint Priest", "Lyon 7eme", "Mme Duchamps", 2, 3, false));
        toDoList = new ArrayList<>(deliveryList);


        adapter = new CurrentDeliveriesRecyclerAdapter(getContext(), deliveryList);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {

                //Cree et Ouvre un Popup menu quand on clique sur l'element
                PopupMenu popupMenu = new PopupMenu(getContext(), view);
                popupMenu.getMenuInflater().inflate(R.menu.current_delivery_onclick_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        int id = menuItem.getItemId();

                        //Ouvre la page "Bon de livraison"
                        if (id == R.id.current_delivery_end) {

                            Intent ConfirmActivity = new Intent(getContext(), DeliveryConfirmActivity.class);
                            //Envoie la position de l'element dans la liste
                            ConfirmActivity.putExtra("holder", position);

                            //Commencer une activite avec resultat pour avoir la confirmation que le bon a bien ete signe
                            startActivityForResult(ConfirmActivity, 1);

                        } else if (id == R.id.current_delivery_navigation) {

                            //Intent mapActivity = new Intent(getActivity(), MapsActivity.class);

                            //mapActivity.putExtra("code", 1);

                            //startActivity(mapActivity);


                        }

                        return true;

                    }
                });

                popupMenu.show();

            }

            @Override
            public void onLongItemClick(View view, int position) {}
        }));


        getLocation();


        //Mets a jour la barre du haut
        setTotals(deliveryList);
        pickupTextView.setText(setPickUp());

        //Ouvre le scanner.
        collectBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), PackageCollectActivity.class);
                startActivityForResult(i, 2);
            }
        });

       //Ouvre la carte
        NavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), MapsActivity.class);

                if (!collectCheckBox.isChecked()) {
                    i.putExtra("nextDestination", toDoList.get(0).getDeliveryStartingPoint());
                } else {
                    i.putExtra("nextDestination", toDoList.get(0).getDeliveryDestination());
                }

                startActivity(i);
            }
        });

        return rootview;
    }

    @Override
    public void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    private void getLocation() {

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1000);
        fusedLocationProviderApi = LocationServices.FusedLocationApi;
        googleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        try {

            fusedLocationProviderApi.requestLocationUpdates(googleApiClient, locationRequest, this);

            mLastKnownLocation = fusedLocationProviderApi.getLastLocation(googleApiClient);

        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        } finally {

            if (mLastKnownLocation != null) {

                Log.e("Geo", "Current found");
                currentLatLng = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
            }

            if (toDoList.size() > 0) {
                getAddress();
            }
        }

    }

    private void getAddress() {

        Intent intent = new Intent(getContext(), GeocodeAddressIntentService.class);
        intent.putExtra("RECEIVER", mResultReceiver);

        if (!collectCheckBox.isChecked()) {
            intent.putExtra("LOCATION_NAME_DATA_EXTRA", toDoList.get(0).getDeliveryStartingPoint());
        } else {
            intent.putExtra("LOCATION_NAME_DATA_EXTRA", toDoList.get(0).getDeliveryDestination());
        }


        Log.e("101", "Starting Service");
        getActivity().startService(intent);
        Log.e("101", "Service Started");

    }

    public void setTotals (List<Delivery> list) {

        if (list.isEmpty()) {
            totalTimeTextView.setText(Integer.toString(0) + "h");
            totalPackageTextView.setText(Integer.toString(0));
            return;
        }
        int totalPackage = 0;

        for (int i = 0 ; i < list.size(); i++) {

            totalPackage = totalPackage + list.get(i).getPackageNumber();
        }
        totalPackageTextView.setText(Integer.toString(totalPackage));
    }

    private String setPickUp() {

        String pickUp = deliveryList.get(0).getDeliveryStartingPoint();

        if (pickUp.length() > 12) {

            StringBuilder builder = new StringBuilder();
            builder.append(pickUp);
            builder.delete(12, pickUp.length());
            builder.append("...");
            pickUp = builder.toString();

        }

        return pickUp;

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        mLastKnownLocation = location;
        if (mLastKnownLocation != null) {

            Log.e("Geo", "Current found");
            currentLatLng = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
        }

        if (toDoList.size() > 0) {
            getAddress();
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==1) {

            int position = data.getIntExtra("holder", 1);

            //Change la disposition de l'element
            CurrentDeliveriesRecyclerAdapter.CurrentDeliveryViewHolder holder = adapter.getCDViewHolder(position);
            holder.container.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.kargoBlueButton));
            holder.timeTextView.setVisibility(View.GONE);
            holder.doneIcon.setVisibility(View.VISIBLE);

            deliveryList.get(position).setDelivered(true);

            toDoList.remove(position - deliveryDone);

            deliveryDone++;

            setTotals(toDoList);


        } else if (resultCode==2) {

            collectCheckBox.setChecked(true);
            collectCheckBox.setEnabled(false);

        }

    }



    private class AddressResultReceiver extends ResultReceiver {

        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, final Bundle resultData) {
            if (resultCode == 0) {

                // here you check the value of getActivity() and break up if needed
                if(getActivity() == null)
                    return;

                final Address address = resultData.getParcelable("RESULT_ADDRESS");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Log.e("Geo", "Adress found");

                        destinationLatLng = new LatLng(address.getLatitude(), address.getLongitude());
                        getDuration(currentLatLng, destinationLatLng);

                    }
                });
            }
            else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        Toast.makeText(getContext(), "Adresse non Trouvee", Toast.LENGTH_LONG).show();



                    }
                });
            }
        }
    }

    private void getDuration(LatLng currentLatLng, LatLng latLng) {

        LatLng origin = currentLatLng;
        LatLng dest = latLng;

        // Getting URL to the Google Directions API
        String url = getDirectionsUrl(origin, dest);

        DownloadTask downloadTask = new DownloadTask();

        // Start downloading json data from Google Directions API
        downloadTask.execute(url);

    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        Log.e("URL", url);


        return url;

    }

    private class DownloadTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();


            parserTask.execute(result);

        }
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {

                sb.append(line);

            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private class ParserTask extends AsyncTask<String, Integer, Void>{
        @Override
        protected Void doInBackground(String... jsonData) {
            JSONObject jObject;

            try {

                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                 duration= parser.getDuration(jObject);

            } catch (Exception e) {

                e.printStackTrace();

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            long hours = TimeUnit.SECONDS.toHours(duration);


            if (hours == 0) {

                long minutes = TimeUnit.SECONDS.toMinutes(duration);

                totalTimeTextView.setText(String.valueOf(minutes ) + "min");

            } else {

                duration -= TimeUnit.HOURS.toSeconds(hours);
                long minutes = TimeUnit.SECONDS.toMinutes(duration);

                totalTimeTextView.setText(String.valueOf(hours) + "h" + String.valueOf(minutes));



            }

            Log.e("Time", "done");
        }
    }


}
