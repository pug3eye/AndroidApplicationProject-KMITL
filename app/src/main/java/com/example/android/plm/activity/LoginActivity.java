package com.example.android.plm.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();

    @Bind(R.id.rootLayout) LinearLayout rootLayout;
    @Bind(R.id.input_username) EditText _usernameText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_login) Button _loginButton;
    @Bind(R.id.link_signup) TextView _signupLink;

    @OnClick(R.id.link_signup)
    public void toSignup() {
        Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_login)
    public void login() {

        Log.e(TAG, "login method");

        if(!validate()) {
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Login_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Login...");
        progressDialog.show();

        final String username = _usernameText.getText().toString();
        final String password = _passwordText.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Endpoints.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e(TAG, response);
                        progressDialog.dismiss();
                        _loginButton.setEnabled(true);

                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            if(jsonObject.getBoolean("error") == false) {

                                Log.e(TAG, "No error");
                                JSONObject resultObject = jsonObject.getJSONObject("user");

                                // store data to sharedPreference.
                                MyApplication.getInstance().getPreferenceManager().setLogin(true);
                                MyApplication.getInstance().getPreferenceManager().setID(resultObject.getInt("id"));
                                MyApplication.getInstance().getPreferenceManager().setUniqueId(resultObject.getString("unique_id"));
                                MyApplication.getInstance().getPreferenceManager().setUsername(resultObject.getString("username"));
                                MyApplication.getInstance().getPreferenceManager().setFirstname(resultObject.getString("firstname"));
                                MyApplication.getInstance().getPreferenceManager().setLastname(resultObject.getString("lastname"));
                                MyApplication.getInstance().getPreferenceManager().setEmail(resultObject.getString("email"));
                                MyApplication.getInstance().getPreferenceManager().setPhoneNumber(resultObject.getString("phone_number"));

                                // go to main activity.
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();

                            } else {

                                String errorMessage = jsonObject.getString("error_message");
                                Log.e(TAG, "JSONObject has error : " + errorMessage);
                                AlertDialog.Builder builder = new AlertDialog.Builder
                                        (LoginActivity.this, R.style.AppTheme_Login_Dialog);
                                builder.setTitle("Login Fail");
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

//                        Log.e(TAG, "Error : " + error.getMessage());
                        progressDialog.dismiss();
                        _loginButton.setEnabled(true);

                    }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        MyApplication.getInstance().addToRequestQueue(stringRequest);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    public boolean validate() {

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        if (username.isEmpty() || username.length() < 6) {
            Snackbar.make(rootLayout, "Please enter username", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if (password.isEmpty() || password.length() < 6) {
            Snackbar.make(rootLayout, "Please enter password", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        return true;

    }


}
