package com.example.android.plm.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.android.plm.R;
import com.example.android.plm.adapter.SearchAdapter;
import com.example.android.plm.app.Endpoints;
import com.example.android.plm.app.MyApplication;
import com.example.android.plm.model.Shop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {

    public static final String TAG = SearchActivity.class.getSimpleName();

    @Bind(R.id.searchText) EditText _searchText;
    @Bind(R.id.searchListView) ListView _listView;
    @Bind(R.id.root) CoordinatorLayout rootLayout;
    @Bind(R.id.progress_layout) LinearLayout _progressLayout;

    private List<Shop> shopList = new ArrayList<Shop>();
    private SearchAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        _searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_SEARCH) || (actionId == KeyEvent.KEYCODE_ENTER)) {
                    performSearch();
                    return true;
                }
                return false;
            }

        });

    }

    private void performSearch() {

        _progressLayout.setVisibility(View.VISIBLE);

        final String searchText = _searchText.getText().toString();
        String url = Endpoints.SEARCH_SHOP_URL + "?search=" + searchText;

        adapter = new SearchAdapter(SearchActivity.this, shopList);
        adapter.clear();

        _listView.setAdapter(adapter);
        _listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Shop shop = (Shop) adapter.getItem(position);
                Intent intent = new Intent(getApplicationContext(), SearchDetailActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, String.valueOf(shop.getId()));
                intent.putExtra("name", shop.getName());
                startActivity(intent);
            }
        });

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        Log.e(TAG, response);
                        _progressLayout.setVisibility(View.GONE);

//                        progressDialog.dismiss();

                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            if(jsonObject.getBoolean("found") == true) {

                                Log.e(TAG, "Search found !");
                                JSONArray searchResult = jsonObject.getJSONArray("shops");

                                for(int i = 0; i < searchResult.length(); i++) {
                                    JSONObject result = searchResult.getJSONObject(i);
                                    int id = result.getInt("id");
                                    String name = result.getString("name");
                                    String owner = result.getString("owner");
                                    String logo = result.getString("image");

                                    if (logo.equalsIgnoreCase("null")) {
                                        logo = Endpoints.NO_LOGO_SHOP_URL;
                                    } else {
                                        logo = Endpoints.BASE_SHOP_LOGO_URL + logo;
                                    }

                                    Shop shop = new Shop();

                                    shop.setId(id);
                                    shop.setName(name);
                                    shop.setOwner(owner);
                                    shop.setLogoUrl(logo);

                                    shopList.add(shop);
                                }

                            } else {
                                Log.e(TAG, "NOT FOUND");
                                Snackbar.make(rootLayout, "Result not found.", Snackbar.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        adapter.notifyDataSetChanged();

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

}
