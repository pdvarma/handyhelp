package com.handyhelp.handlyhelp.users;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.handyhelp.handlyhelp.R;
import com.handyhelp.handlyhelp.constants.RestConstants;
import com.handyhelp.handlyhelp.pojo.Helper;
import com.handyhelp.handlyhelp.rest.JSONService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tanmay on 05/08/18.
 */

public class HelperListing extends AppCompatActivity implements RestConstants,com.google.android.gms.location.LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private List<Helper> helperModelList = new ArrayList<>();
    ArrayList<Helper> helperModels;
    private RecyclerView recyclerView;
    private HelperListingAdapter mAdapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String IP;
    private ProgressDialog dialog;

    static double Radius = 6372.8;

    //Location
    final String TAG = "GPS";
    private long UPDATE_INTERVAL = 90 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */
    static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    double l,lon;
    GoogleApiClient gac;
    LocationRequest locationRequest;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helper_list);
        setTitle("Helper Listing");
        dialog = new ProgressDialog(HelperListing.this);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplication());
        editor=sharedPreferences.edit();
        if(getIntent()!=null){
            if(getIntent().getStringExtra("SERVICE_ID") == " " || getIntent().getStringExtra("SERVICE_ID") == null || getIntent().getStringExtra("SERVICE_ID") == ""){
                getHelperListing();
            }else{
                getHelperListingWithServiceId(getIntent().getStringExtra("SERVICE_ID"));
            }
//            if(getIntent().getStringExtra("location") == " "|| getIntent().getStringExtra("location") == null || getIntent().getStringExtra("location") == ""){
//
//            }else{
//                getHelperListingWithLocation();
//            }
        }else{
            getHelperListing();
        }
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mlayoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mlayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //Location
        isGooglePlayServicesAvailable();

        if(!isLocationEnabled())
            showAlert();

        locationRequest = new LocationRequest();
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        gac = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

    }

    @Override
    protected void onStart() {
        gac.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        gac.disconnect();
        super.onStop();
    }
    private void getHelperListing() {
        dialog.setMessage("Getting Helpers, Please Wait...");
        dialog.show();
        IP=sharedPreferences.getString("ip","");
        final JSONService service = new JSONService();
        AsyncTask<String, Void, Boolean> task = new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... args) {
                String url = args[0].trim();
                return service.getResponse(url, null);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    // success registered
                    // sho home page

                    process(service.getJSONObject());
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                } else {
                    try {
                        String response = service.getJSONObject().getString(STATUS);
                        toast("Failed :: " + response);
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    } catch (Exception ex) {
                        Log.e(tag, Log.getStackTraceString(ex));
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                }
                super.onPostExecute(aBoolean);
            }
        };
        String url = SITE1+IP+SITE2+ GET_HELPERS;
        task.execute(url);
    }

    private void process(JSONObject object) {
        try {

            helperModelList = new ArrayList<>();
            helperModels = new ArrayList<>();

            JSONArray parentArray = object.getJSONArray("helpers");
            Log.d("JSONArray ", String.valueOf(object.getJSONArray("helpers")));

            for (int i = 0; i < parentArray.length(); i++) {

                JSONObject finalObject = parentArray.getJSONObject(i);

                Helper reportModel = new Helper();

                reportModel.setFull_name(finalObject.getString(FULLNAME));
                reportModel.setEmail(finalObject.getString(EMAIL));
                reportModel.setContact(finalObject.getString(CONTACT));
                reportModel.setRating(finalObject.getString(RATING));
                reportModel.setService_name(finalObject.getString("service_name"));
                reportModel.setServices(finalObject.getString(SERVICE_ID));
                reportModel.setHelper_id(finalObject.getString(HELPER_ID));
                helperModelList.add(reportModel);
                mAdapter=new HelperListingAdapter(helperModelList,getApplicationContext());
                recyclerView.setAdapter(mAdapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getHelperListingWithLocation() {
        dialog.setMessage("Getting Helpers, Please Wait...");
        dialog.show();
        IP=sharedPreferences.getString("ip","");
        final JSONService service = new JSONService();
        AsyncTask<String, Void, Boolean> task = new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... args) {
                String url = args[0].trim();
                return service.getResponse(url, null);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    // success registered
                    // sho home page

                    processWithLocation(service.getJSONObject());
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                } else {
                    try {
                        String response = service.getJSONObject().getString(STATUS);
                        toast("Failed :: " + response);
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    } catch (Exception ex) {
                        Log.e(tag, Log.getStackTraceString(ex));
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                }
                super.onPostExecute(aBoolean);
            }
        };
        String url = SITE1+IP+SITE2+ GET_HELPERS;
        task.execute(url);
    }

    private void processWithLocation(JSONObject object) {
        try {

            helperModelList = new ArrayList<>();
            helperModels = new ArrayList<>();

            JSONArray parentArray = object.getJSONArray("helpers");
            Log.d("JSONArray ", String.valueOf(object.getJSONArray("helpers")));

            for (int i = 0; i < parentArray.length(); i++) {

                JSONObject finalObject = parentArray.getJSONObject(i);

                Helper reportModel = new Helper();

                reportModel.setFull_name(finalObject.getString(FULLNAME));
                reportModel.setEmail(finalObject.getString(EMAIL));
                reportModel.setRating(finalObject.getString(RATING));
                reportModel.setServices(finalObject.getString(SERVICE_ID));
                final String latitude=finalObject.getString("latitude");
                final String longitude = finalObject.getString("longitude");
                double lat= Double.parseDouble(latitude);
                double lng= Double.parseDouble(longitude);
                double hav = haversine(l,lon,lat,lng);
                Log.d("HAV", String.valueOf(hav));
                int distance = 2;
                if(hav < distance){
                    helperModelList.add(reportModel);
                }else{
                    toast("Only helper in 2kms are shown up!");
                }

                mAdapter=new HelperListingAdapter(helperModelList,getApplicationContext());
                recyclerView.setAdapter(mAdapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getHelperListingWithServiceId(String service_id) {
        dialog.setMessage("Getting Helpers, Please Wait...");
        dialog.show();
        IP=sharedPreferences.getString("ip","");
        final JSONService service = new JSONService();
        AsyncTask<String, Void, Boolean> task = new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... args) {
                String url = args[0].trim();
                String service_id = args[1].trim();
                HashMap<String, String> params = new HashMap<String, String>();
                params.put(SERVICE_ID, service_id);
                return service.getResponse(url, params);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    // success registered
                    // sho home page

                    processWithID(service.getJSONObject());
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                } else {
                    try {
                        String response = service.getJSONObject().getString(STATUS);
                        toast("Failed :: " + response);
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    } catch (Exception ex) {
                        Log.e(tag, Log.getStackTraceString(ex));
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                }
                super.onPostExecute(aBoolean);
            }
        };
        String url = SITE1+IP+SITE2+ GET_HELPER_WITH_SERVICE_ID;
        task.execute(url,service_id);
    }

    private void processWithID(JSONObject object) {
        try {

            helperModelList = new ArrayList<>();
            helperModels = new ArrayList<>();

            JSONArray parentArray = object.getJSONArray("helpers");
            Log.d("JSONArray ", String.valueOf(object.getJSONArray("helpers")));

            for (int i = 0; i < parentArray.length(); i++) {

                JSONObject finalObject = parentArray.getJSONObject(i);

                Helper reportModel = new Helper();

                reportModel.setFull_name(finalObject.getString(FULLNAME));
                reportModel.setEmail(finalObject.getString(EMAIL));
                reportModel.setContact(finalObject.getString(CONTACT));
                reportModel.setRating(finalObject.getString(RATING));
                reportModel.setServices(finalObject.getString(SERVICE_ID));
                reportModel.setHelper_id(finalObject.getString(HELPER_ID));
                helperModelList.add(reportModel);
                mAdapter=new HelperListingAdapter(helperModelList,getApplicationContext());
                recyclerView.setAdapter(mAdapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void toast(String message)
    {
        Toast.makeText(getApplication(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.filter_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menu_filter_options:
                // custom dialog
                final Dialog dialog = new Dialog(HelperListing.this);
                dialog.setContentView(R.layout.filter);
                Button locationButton = (Button) dialog.findViewById(R.id.button_location_options);
                Button serviceButton = (Button) dialog.findViewById(R.id.button_service_options);
                // if button is clicked, close the custom dialog
                locationButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        getHelperListingWithLocation();
                    }
                });
                serviceButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Intent intent = new Intent(HelperListing.this,ServiceListing.class);
                        startActivity(intent);
                    }
                });
                dialog.show();

                return true;
            case R.id.menu_booked_app_options:
                Intent intent = new Intent(HelperListing.this,BookedListing.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(HelperListing.this, "Permission was granted!", Toast.LENGTH_LONG).show();

                    try{
                        LocationServices.FusedLocationApi.requestLocationUpdates(
                                gac, locationRequest, (com.google.android.gms.location.LocationListener) this);
                    } catch (SecurityException e) {
                        Toast.makeText(HelperListing.this, "SecurityException:\n" + e.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(HelperListing.this, "Permission denied!", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            updateUI(location);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HelperListing.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

            return;
        }
        Log.d(TAG, "onConnected");

        Location ll = LocationServices.FusedLocationApi.getLastLocation(gac);
        Log.d(TAG, "LastLocation: " + (ll == null ? "NO LastLocation" : ll.toString()));

        LocationServices.FusedLocationApi.requestLocationUpdates(gac, locationRequest, (com.google.android.gms.location.LocationListener) this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(HelperListing.this, "onConnectionFailed: \n" + connectionResult.toString(),
                Toast.LENGTH_LONG).show();
        Log.d("DDD", connectionResult.toString());
    }
    private void updateUI(Location loc) {
        Log.d(TAG, "updateUI");
        l = loc.getLatitude();
        lon = loc.getLongitude();
//        toast(String.valueOf(loc.getLatitude()));
//        toast(String.valueOf(loc.getLongitude()));
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private boolean isGooglePlayServicesAvailable() {
        final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.d(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        Log.d(TAG, "This device is supported.");
        return true;
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
        dialog.show();
    }
    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        Log.d("RADIUS*C", String.valueOf(Radius* c));
        return  Radius* c;
    }

}
