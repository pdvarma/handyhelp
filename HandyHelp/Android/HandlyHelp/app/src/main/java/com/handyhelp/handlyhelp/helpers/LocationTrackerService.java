package com.handyhelp.handlyhelp.helpers;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.handyhelp.handlyhelp.constants.RestConstants;
import com.handyhelp.handlyhelp.rest.JSONService;

import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by tanmay on 08/08/18.
 */

public class LocationTrackerService extends Service implements RestConstants, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String IP;
    private static final String tag = "LocationTracker";
    private static final String LINE = System.getProperty("line.separator");
    private static final float MIN_DISTANCE = 0.5f; // minimum distance to be tracked in kilometers
    private static final long MIN_TIME = 1; // in seconds
    private static final long FASTED_INTERVAL = 1; // in seconds
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private File folder;
    String time;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplication());
        editor = sharedPreferences.edit();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        System.out.println(dateFormat.format(cal.getTime()));
        time = dateFormat.format(cal.getTime());
        String extStore = System.getenv("EXTERNAL_STORAGE");
        folder = new File(extStore, "locator");
        if (!folder.exists()) {
            folder.mkdir();
        }
        try {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            Log.e(tag, "Got google Api Client");
            mLocationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(MIN_TIME * 1000)
                    .setFastestInterval(FASTED_INTERVAL * 1000)
                    .setSmallestDisplacement(MIN_DISTANCE * 1000.0f);
            Log.e(tag, "Location request started");
            mGoogleApiClient.connect();
        } catch (Exception ex) {
            Log.e(tag, Log.getStackTraceString(ex));
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        String user = sharedPreferences.getString("helper_id", "");
        Log.e(tag, "Location changed");
        if (location != null) {
            Log.e(tag, "Location not null");
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            // got new location here
            // write your logic
            //  writeFile(lat, lng);
            String s = String.valueOf(location.getLatitude());
            String s1 = String.valueOf(location.getLongitude());
            Log.d("location : ", s + "" + s1);
            updateLocation(s, s1, user, time);
        } else {
            Log.e(tag, "Location is null");
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        String user = sharedPreferences.getString("helper_id", "");
        Log.e(tag, "Connected");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        Log.e(tag, "Location Update Started");
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(location != null) {
            Log.e(tag, "Location not null");
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            // do here the first time logic when user gets connected
            String s= String.valueOf(location.getLatitude());
            String s1= String.valueOf(location.getLongitude());
            Log.d("location : ",s+""+s1);
            updateLocation(s, s1,user,time);
            // writeFile(lat, lng);
        } else {
            Log.e(tag, "Location is null");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(tag, "Connection suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(tag, "Connection failed");
    }

    private void writeFile(double lat, double lng) {
        try {
            FileWriter writer = new FileWriter(new File(folder, "location.txt"), true);
            writer.write(lat + "," + lng);
            writer.write(LINE);
            writer.close();
        } catch (Exception ex) {
            Log.e(tag, Log.getStackTraceString(ex));
        }
    }

    private void updateLocation(String lat,String lng,String u_id,String time) {
        IP=sharedPreferences.getString("ip","");
        final JSONService service = new JSONService();
        AsyncTask<String, Void, Boolean> task = new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... args) {
                String url = args[0].trim();
                String latitude=args[1].trim();
                String longitude=args[2].trim();
                String user_id=args[3].trim();
                HashMap<String, String> params = new HashMap<>();
                params.put(LATITUDE, latitude);
                params.put(LONGITUDE, longitude);
                params.put(HELPER_ID,user_id);
                return service.getResponse(url, params);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    // success registered
                    // sho home page
                    process(service.getJSONObject());
                } else {
                    try {
                        String response = service.getJSONObject().getString(STATUS);
                        toast("Failed :: " + response);
                    } catch (Exception ex) {
                        Log.e("", Log.getStackTraceString(ex));
                    }
                }
                super.onPostExecute(aBoolean);
            }
        };
        String url = SITE1+IP+SITE2+ UPDATE_LOCATION;
        task.execute(url,lat,lng,u_id);
    }

    private void process(JSONObject object) {
        try {
            toast("Location updated successfully");

        } catch (Exception ex) {
            Log.e("", Log.getStackTraceString(ex));
        }

    }
    void toast(String message)
    {
        Toast.makeText(getApplication(), message, Toast.LENGTH_LONG).show();
    }

}
