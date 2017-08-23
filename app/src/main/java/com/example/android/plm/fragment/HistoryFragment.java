package com.example.android.plm.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.android.plm.R;
import com.example.android.plm.adapter.HistoryAdapter;
import com.example.android.plm.app.Endpoints;
import com.example.android.plm.app.MyApplication;
import com.example.android.plm.model.History;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HistoryFragment extends Fragment {

    public static final String TAG = HistoryFragment.class.getSimpleName();

    private List<History> historyList = new ArrayList<History>();
    private HistoryAdapter adapter;

    @Bind(R.id.progress_layout) LinearLayout _progressLayout;
    @Bind(R.id.history_list) ListView _historyListView;
    @Bind(R.id.empty_view) FrameLayout _emptyView;

    public HistoryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, rootView);
        getPointHistories();

        return rootView;

    }

    private void getPointHistories() {

        Log.e(TAG, "IN METHOD GET");

        _progressLayout.setVisibility(View.VISIBLE);

        adapter = new HistoryAdapter(getActivity(), historyList);
        _historyListView.setAdapter(adapter);

        final String unique_id = MyApplication.getInstance().getPreferenceManager().getUniqueId();
        String shop_id = MyApplication.getInstance().getPreferenceManager().getShopActive();
        String url = Endpoints.BASE_SHOP_URL + shop_id + Endpoints.MEMBER_HISTORY_URL;

        final StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        _progressLayout.setVisibility(View.GONE);
                        Log.e(TAG, response);

                        try {

                            Log.e(TAG, "in response");
                            JSONObject jsonObject = new JSONObject(response);

                            if(jsonObject.getBoolean("error") == false) {

                                JSONArray historiesResult = jsonObject.getJSONArray("histories");

                                for(int i = 0; i < historiesResult.length(); i++) {

                                    JSONObject result = historiesResult.getJSONObject(i);

                                    int id = result.getInt("id");
                                    int memberID = result.getInt("member_id");
                                    int point = result.getInt("point");
                                    boolean isAdd;
                                    if(result.getInt("is_add") == 1) {
                                        isAdd = true;
                                    } else {
                                        isAdd = false;
                                    }
                                    String detail = result.getString("detail");
                                    String createdAt = result.getString("created_at");

                                    History history = new History(id, memberID, point, isAdd, detail, createdAt);
                                    historyList.add(history);
                                }

                                if(historiesResult.length() == 0) {
                                    _historyListView.setEmptyView(_emptyView);
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
//                        Log.e(TAG, error.getMessage());

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
