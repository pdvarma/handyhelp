package com.handyhelp.handlyhelp.users;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
 * Created by tanmay on 09/07/18.
 */

public class SignUpActivity extends AppCompatActivity implements RestConstants{
    EditText editText_full_name,editText_email,editText_contact,editText_password,editText_confirm_password;
    Button button_create_account_user;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String IP;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Register User");
        dialog = new ProgressDialog(SignUpActivity.this);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplication());
        editor=sharedPreferences.edit();

        editText_full_name = (EditText)findViewById(R.id.editText_full_name);
        editText_email = (EditText)findViewById(R.id.editText_email);
        editText_contact = (EditText)findViewById(R.id.editText_contact);
        editText_password = (EditText)findViewById(R.id.editText_password);
        editText_confirm_password = (EditText)findViewById(R.id.editText_confirm_password);

        button_create_account_user = (Button)findViewById(R.id.button_create_account_user);

        button_create_account_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = editText_full_name.getText().toString().trim();
                String email = editText_email.getText().toString().trim();
                String contact = editText_contact.getText().toString().trim();
                String password = editText_password.getText().toString().trim();
                String confirm = editText_confirm_password.getText().toString().trim();
                if(password.equals(confirm)){
                    registerUser(fullname,email,contact,confirm);
                }else{
                    toast("Password did not match!");
                }
            }
        });

    }
    private void registerUser(String fullname,final String email,String contact,String password) {
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
                HashMap<String, String> params = new HashMap<>();
                params.put(FULLNAME, fullname);
                params.put(EMAIL, email);
                params.put(CONTACT, contact);
                params.put(PASSWORD, password);
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
        String url = SITE1+IP+SITE2+ REGISTER_USER;
        task.execute(url, fullname, email, contact,password);
    }

    private void process(JSONObject object) {
        try {
            String userId = object.getString(USER_ID);
            toast("Registered success with user id : " + userId);
        } catch (Exception ex) {
            Log.e(tag, Log.getStackTraceString(ex));
        }

    }
    void toast(String message)
    {
        Toast.makeText(getApplication(), message, Toast.LENGTH_LONG).show();
    }
}
