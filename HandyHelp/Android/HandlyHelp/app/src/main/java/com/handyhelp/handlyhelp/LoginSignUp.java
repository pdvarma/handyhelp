package com.handyhelp.handlyhelp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.handyhelp.handlyhelp.users.LoginUsers;
import com.handyhelp.handlyhelp.users.SignUpActivity;

/**
 * Created by tanmay on 08/07/18.
 */

public class LoginSignUp extends AppCompatActivity {
    Button button_sign_up,button_login;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);
        setTitle("Login / Registration");
        button_sign_up = (Button)findViewById(R.id.button_login_options_sign_up);
        button_login = (Button)findViewById(R.id.button_login);

        button_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginSignUp.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginSignUp.this,LoginUsers.class);
                startActivity(intent);
            }
        });
    }
}
