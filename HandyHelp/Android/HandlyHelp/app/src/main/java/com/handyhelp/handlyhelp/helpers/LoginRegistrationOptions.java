package com.handyhelp.handlyhelp.helpers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.handyhelp.handlyhelp.R;
import com.handyhelp.handlyhelp.constants.RestConstants;

/**
 * Created by tanmay on 05/08/18.
 */

public class LoginRegistrationOptions extends AppCompatActivity implements RestConstants {
    Button button_login,button_registration;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_registration_options);
        setTitle("Helper");
        button_login = (Button)findViewById(R.id.button_login);
        button_registration = (Button)findViewById(R.id.button_login_options_sign_up);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginRegistrationOptions.this,LoginHelper.class);
                startActivity(intent);
            }
        });
        button_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginRegistrationOptions.this,RegisterHelper.class);
                startActivity(intent);
            }
        });
    }
}
