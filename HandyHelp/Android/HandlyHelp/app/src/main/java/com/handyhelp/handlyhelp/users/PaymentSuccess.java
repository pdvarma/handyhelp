package com.handyhelp.handlyhelp.users;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.handyhelp.handlyhelp.R;
import com.handyhelp.handlyhelp.constants.RestConstants;

/**
 * Created by tanmay on 09/08/18.
 */

public class PaymentSuccess extends AppCompatActivity implements RestConstants {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_success);
    }
}
