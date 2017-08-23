package com.example.android.plm.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.plm.R;
import com.example.android.plm.model.History;

import java.util.List;

public class HistoryAdapter extends BaseAdapter {

    public static final String TAG = HistoryAdapter.class.getSimpleName();

    Activity activity;
    LayoutInflater inflater;
    List<History> historyList;

    public HistoryAdapter(Activity activity, List<History> historyList){
        this.activity = activity;
        this.historyList = historyList;
    }

    public int getCount() {
        return historyList.size();
    }

    @Override
    public Object getItem(int position) {
        return historyList.get(position);
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
            convertView = inflater.inflate(R.layout.list_point_history, parent, false);
        }

        TextView detail = (TextView) convertView.findViewById(R.id.text_detail);
        TextView dateTime = (TextView) convertView.findViewById(R.id.date_time);
        TextView point = (TextView) convertView.findViewById(R.id.points);
        ImageView isAdd = (ImageView) convertView.findViewById(R.id.is_add);

        History history = historyList.get(position);

        if(history.isAdd()) {
            isAdd.setImageResource(R.drawable.ic_add_black_36dp);
        } else {
            isAdd.setImageResource(R.drawable.ic_remove_black_36dp);
        }

        detail.setText(history.getDetail());
        dateTime.setText(history.getCreatedAt());
        point.setText(String.valueOf(history.getPoint()));

        return convertView;

    }
}
