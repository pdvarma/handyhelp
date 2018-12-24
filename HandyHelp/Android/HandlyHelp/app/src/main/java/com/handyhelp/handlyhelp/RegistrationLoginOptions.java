package com.handyhelp.handlyhelp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by tanmay on 08/07/18.
 */

public class RegistrationLoginOptions extends AppCompatActivity {
    Button button_login;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_sheet_login);
        button_login = (Button)findViewById(R.id.button_login_options);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationLoginOptions.this,LoginSignUp.class);
                startActivity(intent);
            }
        });
    }
}
