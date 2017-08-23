package com.example.android.plm.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.android.plm.R;
import com.example.android.plm.app.MyApplication;
import com.example.android.plm.model.Shop;
import com.example.android.plm.util.CircleNetworkImageView;

import java.util.List;

public class MemberAdapter extends BaseAdapter {

    public static final String TAG = MemberAdapter.class.getSimpleName();

    Activity activity;
    LayoutInflater inflater;
    List<Shop> shopList;
    ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();

    public MemberAdapter(Activity activity, List<Shop> shopList) {
        this.activity = activity;
        this.shopList = shopList;
    }

    public int getCount() {
        return shopList.size();
    }

    @Override
    public Object getItem(int position) {
        return shopList.get(position);
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

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.list_member, null);
        }

        if(imageLoader == null) {
            imageLoader = MyApplication.getInstance().getImageLoader();
        }

        CircleNetworkImageView logo = (CircleNetworkImageView) convertView
                .findViewById(R.id.list_item_icon);
        TextView name = (TextView) convertView.findViewById(R.id.list_item_name);

        Shop shop = shopList.get(position);

        name.setText(shop.getName());
        logo.setImageUrl(shop.getLogoUrl(), imageLoader);

        return convertView;

    }

    public void clear() {
        shopList.clear();
    }

}
