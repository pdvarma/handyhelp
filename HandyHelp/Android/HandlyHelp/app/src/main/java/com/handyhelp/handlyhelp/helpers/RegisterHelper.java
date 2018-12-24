package com.handyhelp.handlyhelp.helpers;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.handyhelp.handlyhelp.R;
import com.handyhelp.handlyhelp.constants.RestConstants;
import com.handyhelp.handlyhelp.pojo.Service;
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

public class RegisterHelper extends AppCompatActivity implements RestConstants {
    EditText editText_full_name,editText_email,editText_contact,editText_password,editText_confirm_password;
    Spinner spinner_services;
    Button button_create_account_helper;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String IP;
    private ProgressDialog dialog;
    private List<Service> serviceModelList = new ArrayList<>();
    SpinnerAdapter spinnerAdapter;
    String spinner_id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_helper);

        setTitle("Register Helper");

        dialog = new ProgressDialog(RegisterHelper.this);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplication());
        editor=sharedPreferences.edit();

        editText_full_name = (EditText)findViewById(R.id.editText_full_name_helper);
        editText_email = (EditText)findViewById(R.id.editText_email_helper);
        editText_contact = (EditText)findViewById(R.id.editText_contact_helper);
        editText_password = (EditText)findViewById(R.id.editText_password_helper);
        editText_confirm_password = (EditText)findViewById(R.id.editText_confirm_password_helper);
        spinner_services = (Spinner)findViewById(R.id.spinner_services);

        button_create_account_helper = (Button)findViewById(R.id.button_create_account_helper);
        getServiceListing();

        spinner_services.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                Service services = spinnerAdapter.getItem(position);
                // Here you can do the action you want to...
                spinner_id = services.getService_id();

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });

        button_create_account_helper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = editText_full_name.getText().toString().trim();
                String email = editText_email.getText().toString().trim();
                String contact = editText_contact.getText().toString().trim();
                String password = editText_password.getText().toString().trim();
                String confirm = editText_confirm_password.getText().toString().trim();

                if(password.equals(confirm)){
                    registerHelper(fullname,email,contact,confirm,spinner_id);
                }else{
                    toast("Password did not match!");
                }
            }
        });

    }
    private void registerHelper(String fullname,final String email,String contact,String password,String service_id) {
        dialog.setMessage("Registering, Please Wait...");
        dialog.show();
        IP=sharedPreferences.getString("ip","");
        final JSONService service = new JSONService();
        AsyncTask<String, Void, Boolean> task = new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... args) {
                String url = args[0].trim();
                String fullname = args[1].trim();
                String email = args[2].trim();
                String contact = args[3].trim();
                String password = args[4].trim();
                String service_id = args[5].trim();
                HashMap<String, String> params = new HashMap<>();
                params.put(FULLNAME, fullname);
                params.put(EMAIL, email);
                params.put(CONTACT, contact);
                params.put(PASSWORD, password);
                params.put(SERVICE_ID, service_id);
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
        String url = SITE1+IP+SITE2+ REGISTER_HELPER;
        task.execute(url, fullname, email, contact,password,service_id);
    }

    private void process(JSONObject object) {
        try {
            String userId = object.getString(HELPER_ID);
            toast("Registered success with user id : " + userId);
        } catch (Exception ex) {
            Log.e(tag, Log.getStackTraceString(ex));
        }

    }
    private void getServiceListing() {
        dialog.setMessage("Getting Services, Please Wait...");
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

                    processService(service.getJSONObject());
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

    private void processService(JSONObject object) {
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
                spinnerAdapter = new SpinnerAdapter(this, R.layout.text_view_spinner, serviceModelList);
                spinner_services.setAdapter(spinnerAdapter);

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
