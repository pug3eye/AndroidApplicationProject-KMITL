package com.example.android.plm.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.android.plm.R;
import com.example.android.plm.adapter.MemberAdapter;
import com.example.android.plm.adapter.SearchAdapter;
import com.example.android.plm.app.Endpoints;
import com.example.android.plm.app.MyApplication;
import com.example.android.plm.model.Shop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private List<Shop> shopList = new ArrayList<Shop>();
    private MemberAdapter adapter;

    @Bind(R.id.progress_layout) LinearLayout _progressLayout;
    @Bind(R.id.member_listView) ListView _memberListView;
    @Bind(R.id.empty_text) TextView _emptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

        if (!MyApplication.getInstance().getPreferenceManager().getLogin()) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }

        if (MyApplication.getInstance().getPreferenceManager().getUniqueId() != null) {
            getMember();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
            return true;
        }

        if (id == R.id.action_refresh) {
            recreate();
        }

        return super.onOptionsItemSelected(item);
    }

    private void getMember() {

        _progressLayout.setVisibility(View.VISIBLE);

        adapter = new MemberAdapter(MainActivity.this, shopList);
        adapter.clear();

        _memberListView.setAdapter(adapter);
        _memberListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Shop shop = (Shop) adapter.getItem(position);

                Intent intent = new Intent(getApplicationContext(), ServiceActivity.class);
                intent.putExtra("name", shop.getName());

                MyApplication.getInstance().getPreferenceManager().setShopActive(String.valueOf(shop.getId()));

                startActivity(intent);
            }
        });

        final String unique_id = MyApplication.getInstance().getPreferenceManager().getUniqueId();

        final StringRequest request = new StringRequest(Request.Method.POST, Endpoints.MEMBERS_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        Log.e(TAG, response);
                        _progressLayout.setVisibility(View.GONE);

                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            if(jsonObject.getBoolean("error") == false) {

                                if (jsonObject.getBoolean("has_member") == true) {

                                    Log.e(TAG, "HAS MEMBERS");
                                    JSONArray membersResult = jsonObject.getJSONArray("members");

                                    for(int i=0; i < membersResult.length(); i++) {

                                        JSONObject result = membersResult.getJSONObject(i);
                                        int id = result.getInt("id");
                                        String name = result.getString("name");
                                        String logo = result.getString("image");

                                        if (logo.equalsIgnoreCase("null")) {
                                            logo = Endpoints.NO_LOGO_SHOP_URL;
                                        } else {
                                            logo = Endpoints.BASE_SHOP_LOGO_URL + logo;
                                        }

                                        Shop shop = new Shop(id, name, logo);
                                        shopList.add(shop);

                                    }

                                } else {

                                    _memberListView.setEmptyView(_emptyText);

                                }

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
//                        Log.e(TAG, "Error : " + error.getMessage());

                    }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("unique_id", unique_id);
                return params;
            }
        };

        MyApplication.getInstance().addToRequestQueue(request);

    }

}
