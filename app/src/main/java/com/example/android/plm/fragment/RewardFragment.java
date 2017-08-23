package com.example.android.plm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.android.plm.R;
import com.example.android.plm.activity.RewardActivity;
import com.example.android.plm.adapter.RewardAdapter;
import com.example.android.plm.app.Endpoints;
import com.example.android.plm.app.MyApplication;
import com.example.android.plm.model.Reward;

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

public class RewardFragment extends Fragment {

    public static final String TAG = RewardFragment.class.getSimpleName();

    private List<Reward> rewardList = new ArrayList<Reward>();
    private RewardAdapter adapter;

    @Bind(R.id.progress_layout) LinearLayout _progressLayout;
    @Bind(R.id.points) TextView _points;
    @Bind(R.id.reward_grid_view) GridView _rewardGridView;
    @Bind(R.id.empty_view) FrameLayout _emptyView;

    public RewardFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_reward, container, false);
        ButterKnife.bind(this, rootView);
        getPointAndReward();

        return rootView;
    }

    private void getPointAndReward() {

        _progressLayout.setVisibility(View.VISIBLE);

        adapter = new RewardAdapter(getActivity(), rewardList);
        _rewardGridView.setAdapter(adapter);
        _rewardGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Reward reward = (Reward) adapter.getItem(position);
                Intent intent = new Intent(getActivity(), RewardActivity.class);
                intent.putExtra("reward", reward);
                startActivity(intent);

            }
        });

        final String unique_id = MyApplication.getInstance().getPreferenceManager().getUniqueId();
        String shop_id = MyApplication.getInstance().getPreferenceManager().getShopActive();
        String url = Endpoints.BASE_SHOP_URL + shop_id + Endpoints.REWARD_URL;

        final StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        _progressLayout.setVisibility(View.GONE);
                        Log.e(TAG, response);

                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            if(jsonObject.getBoolean("error") == false) {

                                _points.setText(jsonObject.getString("points"));

                                JSONArray rewardsResult = jsonObject.getJSONArray("rewards");

                                for(int i = 0; i < rewardsResult.length(); i++) {

                                    JSONObject result = rewardsResult.getJSONObject(i);
                                    int id = result.getInt("id");
                                    String barcode = result.getString("barcode");
                                    String name = result.getString("name");
                                    int point_use = result.getInt("point_use");
                                    String detail = result.getString("detail");
                                    String imageURL = result.getString("image");

                                    if (imageURL.equalsIgnoreCase("null")) {
                                        imageURL = Endpoints.NO_REWARD_IMAGE_URL;
                                    } else {
                                        imageURL = Endpoints.BASE_REWARD_IMAGE_URL + imageURL;
                                    }

                                    Reward reward = new Reward(id, barcode, name, point_use, detail, imageURL);
                                    rewardList.add(reward);

                                }

                                if(rewardsResult.length() == 0) {
                                    _rewardGridView.setEmptyView(_emptyView);
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
                        Log.e(TAG, error.getMessage());

                    }
        }){
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
