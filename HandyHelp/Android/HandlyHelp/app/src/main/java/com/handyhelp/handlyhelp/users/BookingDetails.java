package com.handyhelp.handlyhelp.users;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.handyhelp.handlyhelp.R;
import com.handyhelp.handlyhelp.constants.RestConstants;
import com.handyhelp.handlyhelp.pojo.Helper;
import com.handyhelp.handlyhelp.rest.JSONService;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tanmay on 09/08/18.
 */

public class BookingDetails extends AppCompatActivity implements RestConstants {
    public ArrayList<Helper> helperList;
    TextView  textView_name_booking_details,textView_email_booking_details,textView_contact_booking_details
            , textView_service_booking_details,textView_rating_booking_details;
    Button button_booking_details;
    Spinner spinner2;
    String time,name,email,contact,rating,service,helper_id, service_id;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String IP;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_details);
        dialog = new ProgressDialog(BookingDetails.this);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplication());
        editor=sharedPreferences.edit();
        textView_name_booking_details = (TextView)findViewById(R.id.textView_name_booking_details);
        textView_email_booking_details = (TextView)findViewById(R.id.textView_email_booking_details);
        textView_contact_booking_details = (TextView)findViewById(R.id.textView_contact_booking_details);
        textView_service_booking_details = (TextView)findViewById(R.id.textView_service_booking_details);
        textView_rating_booking_details = (TextView)findViewById(R.id.textView_rating_booking_details);

        button_booking_details = (Button)findViewById(R.id.button_booking_details);

        spinner2 = (Spinner)findViewById(R.id.spinner2);

        if(getIntent()!=null){
//            helperList = (ArrayList<Helper>) getIntent().getSerializableExtra("data");
            name = getIntent().getStringExtra("name");
            email = getIntent().getStringExtra("email");
            contact = getIntent().getStringExtra("contact");
            rating = getIntent().getStringExtra("rating");
            service = getIntent().getStringExtra("service_name");
            helper_id = getIntent().getStringExtra("helper_id");
            service_id = getIntent().getStringExtra("service_id");


        }
        button_booking_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(BookingDetails.this);
                dialog.setContentView(R.layout.confirmation);
                Button button_cancel = (Button) dialog.findViewById(R.id.button_cancel);
                Button button_confirm = (Button) dialog.findViewById(R.id.button_confirm);
                TextView textView = (TextView)dialog.findViewById(R.id.textView4);
                // if button is clicked, close the custom dialog
                textView.setText("Are you sure you want to book appointment " + time + " ?" );
                button_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                button_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bookAppointment(sharedPreferences.getString("userid",""),sharedPreferences.getString("helper_id",""),service_id,"1");
                    }
                });
                dialog.show();

            }
        });

        final List timeList = new ArrayList();
        timeList.add("8:00");
        timeList.add("9:00");
        timeList.add("10:00");
        timeList.add("11:00");
        timeList.add("12:00");
        timeList.add("13:00");
        timeList.add("14:00");
        timeList.add("15:00");
        timeList.add("16:00");
        timeList.add("17:00");
        timeList.add("18:00");
        timeList.add("19:00");
        timeList.add("20:00");
        timeList.add("21:00");
        timeList.add("22:00");

        ArrayAdapter arrayAdapter = new ArrayAdapter(BookingDetails.this,R.layout.text_view_spinner,timeList);
        spinner2.setAdapter(arrayAdapter);

        textView_name_booking_details.setText("Name : "+name);
        textView_email_booking_details.setText("Email : "+email);
        textView_contact_booking_details.setText("Contact : "+contact);
        textView_rating_booking_details.setText("Rating : "+rating);
        textView_service_booking_details.setText("Service Provided : "+service);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                time = String.valueOf(timeList.get(position));
//                Toast.makeText(BookingDetails.this, time, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void bookAppointment(String user_id,String helper_id,String service_id,String payment) {
        dialog.setMessage("Booking appointment, Please Wait...");
        dialog.show();
        IP=sharedPreferences.getString("ip","");

        final JSONService service = new JSONService();
        AsyncTask<String, Void, Boolean> task = new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... args) {
                String url = args[0].trim();
                String user_id = args[1].trim();
                String helper_id = args[2].trim();
                String service_id = args[3].trim();
                String payment = args[4].trim();
                HashMap<String, String> params = new HashMap<String, String>();
                params.put(USER_ID, user_id);
                params.put(HELPER_ID, helper_id);
                params.put(SERVICE_ID, service_id);
                params.put("payment", "1");

                return service.getResponse(url,params);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if(aBoolean)
                {
                    process(service.getJSONObject());
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
                else
                {
                    toast("Invalid Login Credentials.Please try again..");
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
                super.onPostExecute(aBoolean);
            }
        };
        String url = SITE1+IP+SITE2+ BOOK_APPOINTMENT;
        task.execute(url, user_id,helper_id,service_id,payment);
    }


    private void process(JSONObject object) {
        try {
            Intent intent = new Intent(BookingDetails.this,PaymentSuccess.class);
            startActivity(intent);
            finish();
        } catch (Exception ex) {
            Log.e(tag, Log.getStackTraceString(ex));
        }
    }
    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
