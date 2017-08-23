package com.example.android.plm.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.android.plm.R;
import com.example.android.plm.app.MyApplication;
import com.example.android.plm.model.Reward;

import java.util.List;

public class RewardAdapter extends BaseAdapter{

    public static final String TAG = RewardAdapter.class.getSimpleName();

    Activity activity;
    LayoutInflater inflater;
    List<Reward> rewardList;
    ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();

    public RewardAdapter(Activity activity, List<Reward> rewardList) {
        this.activity = activity;
        this.rewardList = rewardList;
    }

    public int getCount() {
        return rewardList.size();
    }

    @Override
    public Object getItem(int position) {
        return rewardList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_reward, null);
        }

        if(imageLoader == null) {
            imageLoader = MyApplication.getInstance().getImageLoader();
        }

        NetworkImageView image = (NetworkImageView) convertView.findViewById(R.id.reward_image);
        TextView name = (TextView) convertView.findViewById(R.id.reward_name);
        TextView pointUse = (TextView) convertView.findViewById(R.id.point_use);

        Reward reward = rewardList.get(position);

        image.setImageUrl(reward.getImageURL(), imageLoader);
        name.setText(reward.getName());
        pointUse.setText(String.valueOf(reward.getPointUse()) + " points");

        return convertView;

    }
}
