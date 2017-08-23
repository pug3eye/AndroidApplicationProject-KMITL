package com.example.android.plm.activity;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.android.plm.R;
import com.example.android.plm.app.Endpoints;
import com.example.android.plm.app.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = SignupActivity.class.getSimpleName();

    @Bind(R.id.rootLayout) LinearLayout rootLayout;
    @Bind(R.id.input_username) EditText _usernameText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.password_confirm) EditText _confirmPassword;
    @Bind(R.id.input_first_name) EditText _firstNameText;
    @Bind(R.id.input_last_name) EditText _lastNameText;
    @Bind(R.id.input_email) EditText _email;
    @Bind(R.id.input_phone) EditText _phoneNumber;
    @Bind(R.id.btn_signup) Button _signupButton;

    @OnClick(R.id.btn_signup)
    public void signup() {

        Log.e(TAG, "In signup method");

        if(!validate()) {
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this, R.style.AppTheme_Login_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        final String username = _usernameText.getText().toString();
        final String password = _passwordText.getText().toString();
        final String firstName = _firstNameText.getText().toString();
        final String lastName = _lastNameText.getText().toString();
        final String email = _email.getText().toString();
        final String phoneNumber = _phoneNumber.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Endpoints.SIGNUP_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e(TAG, response);
                        progressDialog.dismiss();
                        _signupButton.setEnabled(true);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("error") == false) {
                                Log.e(TAG, "In if jsonObject.getBoolean");
                                finish();
                            } else {
                                String errorMessage = jsonObject.getString("error_message");
                                AlertDialog.Builder builder = new AlertDialog.Builder
                                        (SignupActivity.this, R.style.AppTheme_Login_Dialog);
                                builder.setTitle("Sign Up Fail");
                                builder.setMessage(errorMessage);
                                builder.setPositiveButton("Close", null);
                                builder.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e(TAG, "Error : " + error.getMessage());
                        progressDialog.dismiss();
                        _signupButton.setEnabled(true);

                    }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);
                params.put("firstname", firstName);
                params.put("lastname", lastName);
                params.put("email", email);
                params.put("phone_number", phoneNumber);
                return params;
            }
        };

        MyApplication.getInstance().addToRequestQueue(stringRequest);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
    }

    public boolean validate() {

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();
        String confirmPassword = _confirmPassword.getText().toString();
        String firstName = _firstNameText.getText().toString();
        String lastName = _lastNameText.getText().toString();
        String email = _email.getText().toString();
        String phoneNumber = _phoneNumber.getText().toString();

        if (username.isEmpty() || username.length() < 6) {
            Snackbar.make(rootLayout, "Username require at least 6 characters", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if (password.isEmpty() || password.length() < 6) {
            Snackbar.make(rootLayout, "Password length at least 6 characters", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if (firstName.isEmpty()) {
            Snackbar.make(rootLayout, "First Name is require", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if (lastName.isEmpty()) {
            Snackbar.make(rootLayout, "Last Name is require", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Snackbar.make(rootLayout, "Invalid E-mail address", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if (phoneNumber.isEmpty() || phoneNumber.length() != 10) {
            Snackbar.make(rootLayout, "Invalid Phone Number", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if (password.compareTo(confirmPassword) != 0) {
            Snackbar.make(rootLayout, "Password and Confirm Password not match", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        return true;

    }



}
