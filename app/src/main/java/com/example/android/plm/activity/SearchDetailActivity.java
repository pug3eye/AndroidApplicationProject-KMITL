package com.example.android.plm.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.example.android.plm.R;
import com.example.android.plm.app.Endpoints;
import com.example.android.plm.app.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchDetailActivity extends AppCompatActivity {

    public static final String TAG = SearchDetailActivity.class.getSimpleName();
    ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();

    String shopId;
    String shopName;

    @Bind(R.id.register_member) Button _registerMemberButton;
    @Bind(R.id.search_shop_name) TextView _shopName;
    @Bind(R.id.search_shop_owner) TextView _shopOwner;
    @Bind(R.id.search_shop_start_point) TextView _shopStartPoint;
    @Bind(R.id.search_shop_discount) TextView _discount;
    @Bind(R.id.search_shop_email) TextView _shopEmail;
    @Bind(R.id.search_shop_address) TextView _shopAddress;
    @Bind(R.id.search_shop_detail) TextView _shopDetail;
    @Bind(R.id.search_shop_image) NetworkImageView _shopImage;

    @OnClick(R.id.register_member)
    public void registerMember() {
        _registerMemberButton.setEnabled(false);
        sendRegisterRequest();
    }

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);
        ButterKnife.bind(this);

        Intent intent = this.getIntent();
        shopId = intent.getStringExtra(Intent.EXTRA_TEXT);
        shopName = intent.getStringExtra("name");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(shopName);

        getShopDetail();

    }

    private void getShopDetail() {

        String url = Endpoints.BASE_SHOP_URL + shopId;

        final ProgressDialog progressDialog = new ProgressDialog(SearchDetailActivity.this, R.style.AppTheme_Login_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        Log.e(TAG, response);
                        progressDialog.dismiss();

                        try {

                            JSONObject result = new JSONObject(response);
                            setShopDetail(result);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error : " + error.getMessage());
                    }

        });

        MyApplication.getInstance().addToRequestQueue(request);

    }

    private void setShopDetail(JSONObject result) throws JSONException {

        String imageUrl = result.getString("image");

        if (imageUrl.equalsIgnoreCase("null")) {
            imageUrl = Endpoints.NO_LOGO_SHOP_URL;
        } else {
            imageUrl = Endpoints.BASE_SHOP_LOGO_URL + imageUrl;
        }

        _shopImage.setImageUrl(imageUrl, imageLoader);
        _shopName.setText(result.getString("name"));
        _shopOwner.setText(result.getString("owner"));
        _shopEmail.setText(result.getString("email"));

        String detail = result.getString("detail");
        String address = result.getString("address");
        String discount = result.getString("discount");
        String startPoint = result.getString("start_point");

        if(!detail.equalsIgnoreCase("null")) {
            _shopDetail.setText(detail);
        }
        if(!address.equalsIgnoreCase("null")) {
            _shopAddress.setText(address);
        }
        if(!discount.equalsIgnoreCase("null")) {
            _discount.setText(discount + "%");
        }
        if(!startPoint.equalsIgnoreCase("null")) {
            _shopStartPoint.setText(startPoint + " points");
        }

    }

    private void sendRegisterRequest() {

        final ProgressDialog progressDialog = new ProgressDialog(SearchDetailActivity.this, R.style.AppTheme_Detail_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        final String unique_id = MyApplication.getInstance().getPreferenceManager().getUniqueId();

        StringRequest request = new StringRequest(Request.Method.POST, Endpoints.REGISTER_MEMBER_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        Log.e(TAG, response);
                        progressDialog.dismiss();

                        try {

                            JSONObject result = new JSONObject(response);

                            if(result.getBoolean("error") == false) {

                                if(result.getBoolean("is_member") == false) {

                                    String message = result.getString("message");
                                    AlertDialog.Builder builder = new AlertDialog.Builder
                                            (SearchDetailActivity.this, R.style.AppTheme_Detail_Dialog);
                                    builder.setTitle("Register complete");
                                    builder.setMessage(message);
                                    builder.setPositiveButton("Close", null);
                                    builder.show();


                                } else {

                                    String message = result.getString("message");
                                    AlertDialog.Builder builder = new AlertDialog.Builder
                                            (SearchDetailActivity.this, R.style.AppTheme_Detail_Dialog);
                                    builder.setTitle("Already register");
                                    builder.setMessage(message);
                                    builder.setPositiveButton("Close", null);
                                    builder.show();

                                }

                            } else {

                                String errorMessage = result.getString("error_message");
                                AlertDialog.Builder builder = new AlertDialog.Builder
                                        (SearchDetailActivity.this, R.style.AppTheme_Detail_Dialog);
                                builder.setTitle("Register fail !");
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
                        _registerMemberButton.setEnabled(true);

                        AlertDialog.Builder builder = new AlertDialog.Builder
                                (SearchDetailActivity.this, R.style.AppTheme_Detail_Dialog);
                        builder.setTitle("Some error has occur");
                        builder.setMessage("Please try again");
                        builder.setPositiveButton("Close", null);
                        builder.show();

                }

        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("unique_id", unique_id);
                params.put("shop_id", shopId);
                return params;
            }

        };

        MyApplication.getInstance().addToRequestQueue(request);

    }

}
