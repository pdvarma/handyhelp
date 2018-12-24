package com.handyhelp.handlyhelp.users;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.handyhelp.handlyhelp.R;
import com.handyhelp.handlyhelp.constants.RestConstants;
import com.handyhelp.handlyhelp.pojo.Helper;
import com.handyhelp.handlyhelp.pojo.Service;
import com.handyhelp.handlyhelp.rest.JSONService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanmay on 05/08/18.
 */

public class ServiceListing extends AppCompatActivity implements RestConstants{
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String IP;
    private RecyclerView recyclerView;
    private ServiceListingAdapter mAdapter;
    private ProgressDialog dialog;
    private List<Service> serviceModelList = new ArrayList<>();
    ArrayAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_listing);

        dialog = new ProgressDialog(ServiceListing.this);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplication());
        editor=sharedPreferences.edit();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_service);

        RecyclerView.LayoutManager mlayoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mlayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        getServiceListing();
    }

    private void getServiceListing() {
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
        String url = SITE1+IP+SITE2+ GET_SERVICES;
        task.execute(url);
    }

    private void process(JSONObject object) {
        try {

            serviceModelList = new ArrayList<>();

            JSONArray parentArray = object.getJSONArray("services");
            Log.d("JSONArray ", String.valueOf(object.getJSONArray("services")));

            for (int i = 0; i < parentArray.length(); i++) {

                JSONObject finalObject = parentArray.getJSONObject(i);

                Service serviceModel = new Service();

                serviceModel.setService_name(finalObject.getString("name"));
                serviceModel.setService_id(finalObject.getString(SERVICE_ID));

                serviceModelList.add(serviceModel);

                mAdapter=new ServiceListingAdapter(serviceModelList,getApplicationContext());
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
}
