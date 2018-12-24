package com.handyhelp.handlyhelp.users;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.handyhelp.handlyhelp.R;
import com.handyhelp.handlyhelp.constants.RestConstants;
import com.handyhelp.handlyhelp.rest.JSONService;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by tanmay on 03/08/18.
 */

public class LoginUsers extends AppCompatActivity implements RestConstants {
    EditText editText_email,editText_password;
    Button button_login_user;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String IP;
    private ProgressDialog dialog;
    String email;
    String password;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_user);
        setTitle("Login User");
        dialog = new ProgressDialog(LoginUsers.this);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplication());
        editor=sharedPreferences.edit();

        editText_email = (EditText)findViewById(R.id.editText_email_login);
        editText_password = (EditText)findViewById(R.id.editText_password_login);
        button_login_user = (Button)findViewById(R.id.button_login_user);

        button_login_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = editText_email.getText().toString().trim();
                password = editText_password.getText().toString().trim();
                login_user();
            }
        });

    }
    private void login_user() {
        dialog.setMessage("Logging in, Please Wait...");
        dialog.show();
        IP=sharedPreferences.getString("ip","");
        if(email.length() <= 0) {
            editText_email.setError(Html.fromHtml("<font color='red'>Your Employee ID</font>"));
            editText_email.requestFocus();
            return;
        }
        if(password.length()<= 0) {
            editText_password.setError(Html.fromHtml("<font color='red'>Your Password</font>"));
            editText_password.requestFocus();
            return;
        }
        final JSONService service = new JSONService();
        AsyncTask<String, Void, Boolean> task = new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... args) {
                String url = args[0].trim();
                String username = args[1].trim();
                String password = args[2].trim();
                HashMap<String, String> params = new HashMap<String, String>();
                params.put(EMAIL, username);
                params.put(PASSWORD, password);

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
        String url = SITE1+IP+SITE2+ LOGIN_USER;
        task.execute(url, email, password);
    }


    private void process(JSONObject object) {
        try {
            String user_name= object.getString("full_name");
            String userid= object.getString(USER_ID);

            toast("login userId : " + userid + " : " + user_name);
            editor.putString("userid", userid);
            editor.apply();
            String a=sharedPreferences.getString("userid","");
            Log.d("SP", a);
            Intent intent = new Intent(LoginUsers.this,HelperListing.class);
            startActivity(intent);
        } catch (Exception ex) {
            Log.e(tag, Log.getStackTraceString(ex));
        }
    }
    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
