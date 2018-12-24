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
import android.widget.Toast;

import com.handyhelp.handlyhelp.R;
import com.handyhelp.handlyhelp.constants.RestConstants;
import com.handyhelp.handlyhelp.pojo.Booked;
import com.handyhelp.handlyhelp.pojo.Helper;
import com.handyhelp.handlyhelp.rest.JSONService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tanmay on 06/08/18.
 */

public class BookedListing extends AppCompatActivity implements RestConstants {
    private List<Booked> bookedModelList = new ArrayList<>();
    ArrayList<Booked> helperModels;
    private RecyclerView recyclerView;
    private BookedListingAdapter mAdapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String IP;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booked_user_listing);
        setTitle("Booked Appointment");
        dialog = new ProgressDialog(BookedListing.this);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplication());
        editor=sharedPreferences.edit();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_booked);

        RecyclerView.LayoutManager mlayoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mlayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getHelperListing(sharedPreferences.getString("userid",""));
    }

    private void getHelperListing(String user_id) {
        dialog.setMessage("Getting booking list, Please Wait...");
        dialog.show();
        IP=sharedPreferences.getString("ip","");
        final JSONService service = new JSONService();
        AsyncTask<String, Void, Boolean> task = new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... args) {
                String url = args[0].trim();
                String user_id = args[1].trim();
                HashMap<String, String> params = new HashMap<String, String>();
                params.put(USER_ID, user_id);
                return service.getResponse(url, params);
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
        String url = SITE1+IP+SITE2+ BOOKED_APPOINTMENT_USERS;
        task.execute(url,user_id);
    }

    private void process(JSONObject object) {
        try {

            bookedModelList = new ArrayList<>();
            helperModels = new ArrayList<>();

            JSONArray parentArray = object.getJSONArray("appointments");

            for (int i = 0; i < parentArray.length(); i++) {

                JSONObject finalObject = parentArray.getJSONObject(i);

                Booked reportModel = new Booked();

                reportModel.setUser_id(finalObject.getString(USER_ID));
                reportModel.setFull_name_user(finalObject.getString(FULLNAME));
                reportModel.setEmail_user(finalObject.getString(EMAIL));
                reportModel.setContact_user(finalObject.getString(CONTACT));

                reportModel.setHeler_id(finalObject.getString(HELPER_ID));
                reportModel.setFull_name_helper(finalObject.getString("full_name_helper"));
                reportModel.setEmail_helper(finalObject.getString("email_helper"));
                reportModel.setContact_helper(finalObject.getString("contact_helper"));
                reportModel.setDate(finalObject.getString("date"));

                reportModel.setRating(finalObject.getString(RATING));
                reportModel.setService_id(finalObject.getString(SERVICE_ID));
                reportModel.setService_name(finalObject.getString("service_name"));

                bookedModelList.add(reportModel);
                mAdapter=new BookedListingAdapter(bookedModelList,getApplicationContext());
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
