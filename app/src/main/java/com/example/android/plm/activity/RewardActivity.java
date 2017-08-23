package com.example.android.plm.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.example.android.plm.R;
import com.example.android.plm.app.Endpoints;
import com.example.android.plm.app.MyApplication;
import com.example.android.plm.model.Reward;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RewardActivity extends AppCompatActivity {

    public static final String TAG = RewardActivity.class.getSimpleName();
    ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();
    Reward reward;

    @Bind(R.id.reward_barcode) TextView _barcode;
    @Bind(R.id.reward_name) TextView _name;
    @Bind(R.id.use_point) TextView _point;
    @Bind(R.id.reward_detail) TextView _detail;
    @Bind(R.id.reward_image) NetworkImageView _image;

    @OnClick(R.id.redeem_reward)
    public void redeemReward() {

        Log.e(TAG, "IN REDEEM REWARD METHOD");

        final ProgressDialog progressDialog = new ProgressDialog(RewardActivity.this, R.style.AppTheme_Login_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Redeem...");
        progressDialog.show();

        final String unique_id = MyApplication.getInstance().getPreferenceManager().getUniqueId();

        StringRequest request = new StringRequest(Request.Method.POST, Endpoints.REDEEM_REWARD_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        Log.e(TAG, response);
                        progressDialog.dismiss();

                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            if(jsonObject.getBoolean("error") == false) {

                                Log.e(TAG, "NO ERROR");

                                if(jsonObject.getBoolean("can_redeem")) {

                                    // can redeem
                                    String code = jsonObject.getString("message");
                                    Intent intent = new Intent(getApplicationContext(), RedeemSuccessActivity.class);
                                    intent.putExtra(Intent.EXTRA_TEXT, code);
                                    startActivity(intent);
                                    finish();

                                } else {

                                    String errorMessage = jsonObject.getString("message");
                                    Log.e(TAG, "JSONObject has error : " + errorMessage);
                                    AlertDialog.Builder builder = new AlertDialog.Builder
                                            (RewardActivity.this, R.style.AppTheme_Login_Dialog);
                                    builder.setTitle("Redeem Reward Fail");
                                    builder.setMessage(errorMessage);
                                    builder.setPositiveButton("Close", null);
                                    builder.show();

                                }

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

                    }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("unique_id", unique_id);
                params.put("reward_id", String.valueOf(reward.getId()));
                return params;
            }
        };

        MyApplication.getInstance().addToRequestQueue(request);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);
        ButterKnife.bind(this);

        Intent intent = this.getIntent();
        reward = (Reward) intent.getSerializableExtra("reward");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(reward.getName());

        _barcode.setText(reward.getBarcode());
        _name.setText(reward.getName());
        _point.setText(String.valueOf(reward.getPointUse()) + " points");
        _detail.setText(reward.getDetail());
        _image.setImageUrl(reward.getImageURL(), imageLoader);

    }

}
