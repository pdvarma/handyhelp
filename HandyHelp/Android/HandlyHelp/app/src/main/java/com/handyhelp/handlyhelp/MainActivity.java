package com.handyhelp.handlyhelp;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.handyhelp.handlyhelp.helpers.LoginRegistrationOptions;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplication());
        editor=sharedPreferences.edit();
        editor.putString("ip","192.168.0.7");
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menu_login_options:
                        // custom dialog
                        final Dialog dialog = new Dialog(MainActivity.this);
                        dialog.setContentView(R.layout.bottom_sheet_login);
                        Button loginButton = (Button) dialog.findViewById(R.id.button_login_options);
                        Button helperButton = (Button) dialog.findViewById(R.id.button_become_helper);
                        // if button is clicked, close the custom dialog
                        loginButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                Intent intent = new Intent(MainActivity.this,LoginSignUp.class);
                                startActivity(intent);
                            }
                        });
                        helperButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                Intent intent = new Intent(MainActivity.this,LoginRegistrationOptions.class);
                                startActivity(intent);
                            }
                        });
                        dialog.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
