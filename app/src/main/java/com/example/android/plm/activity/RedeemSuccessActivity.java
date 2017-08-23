package com.example.android.plm.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.plm.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RedeemSuccessActivity extends AppCompatActivity {

    public static final String TAG = RedeemSuccessActivity.class.getSimpleName();

    @Bind(R.id.code) TextView _code;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        ButterKnife.bind(this);
        Intent intent = this.getIntent();
        String code = intent.getStringExtra(Intent.EXTRA_TEXT);
        _code.setText("\"" + code + "\"");
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click 'BACK' again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
