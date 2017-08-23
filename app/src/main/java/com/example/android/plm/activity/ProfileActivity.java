package com.example.android.plm.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.android.plm.R;
import com.example.android.plm.app.MyApplication;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    @Bind(R.id.profile_username) TextView _username;
    @Bind(R.id.profile_name) TextView _name;
    @Bind(R.id.profile_email) TextView _email;
    @Bind(R.id.profile_phone_number) TextView _phoneNumber;
    @Bind(R.id.profile_unique_id) TextView _uniqueID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String firstname = MyApplication.getInstance().getPreferenceManager().getFirstname();
        String lastname = MyApplication.getInstance().getPreferenceManager().getLastname();

        _username.setText(MyApplication.getInstance().getPreferenceManager().getUsername());
        _name.setText(firstname + " " + lastname);
        _email.setText(MyApplication.getInstance().getPreferenceManager().getEmail());
        _phoneNumber.setText(MyApplication.getInstance().getPreferenceManager().getPhoneNumber());
        _uniqueID.setText(MyApplication.getInstance().getPreferenceManager().getUniqueId());

    }
}
