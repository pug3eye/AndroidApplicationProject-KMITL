package com.example.android.plm.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailFragment extends Fragment {

    public static final String TAG = DetailFragment.class.getSimpleName();
    ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();

    @Bind(R.id.progress_layout) LinearLayout _progressLayout;
    @Bind(R.id.shop_image) NetworkImageView _shopImage;
    @Bind(R.id.shop_name) TextView _shopName;
    @Bind(R.id.shop_owner) TextView _shopOwner;
    @Bind(R.id.shop_discount) TextView _shopDiscount;
    @Bind(R.id.shop_email) TextView _shopEmail;
    @Bind(R.id.shop_address) TextView _shopAddress;
    @Bind(R.id.shop_detail) TextView _shopDetail;

    public DetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, rootView);

        getShopDetail();

        return rootView;

    }

    private void getShopDetail() {

        String shopId = MyApplication.getInstance().getPreferenceManager().getShopActive();
        String url = Endpoints.BASE_SHOP_URL + shopId;
        
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        Log.e(TAG, response);
                        _progressLayout.setVisibility(View.GONE);

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
                _progressLayout.setVisibility(View.GONE);
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

        if(!detail.equalsIgnoreCase("null")) {
            _shopDetail.setText(detail);
        }
        if(!address.equalsIgnoreCase("null")) {
            _shopAddress.setText(address);
        }
        if(!discount.equalsIgnoreCase("null")) {
            _shopDiscount.setText(discount + "%");
        }

    }

}
