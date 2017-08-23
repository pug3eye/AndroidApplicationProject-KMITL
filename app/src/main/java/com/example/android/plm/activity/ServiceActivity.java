package com.example.android.plm.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.plm.R;
import com.example.android.plm.fragment.DetailFragment;
import com.example.android.plm.fragment.HistoryFragment;
import com.example.android.plm.fragment.RewardFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ServiceActivity extends AppCompatActivity {

    public final static String TAG = ServiceActivity.class.getSimpleName();

    @Bind(R.id.toolbar) Toolbar _toolbar;
    @Bind(R.id.viewpager) ViewPager _viewPager;
    @Bind(R.id.tabs) TabLayout _tabs;

    String shopName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ButterKnife.bind(this);

        Intent intent = this.getIntent();
        shopName = intent.getStringExtra("name");

        setSupportActionBar(_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(shopName);

        RewardFragment rewardFragment = new RewardFragment();
        HistoryFragment historyFragment = new HistoryFragment();
        DetailFragment detailFragment = new DetailFragment();

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(rewardFragment, "REWARD");
        viewPagerAdapter.addFragment(historyFragment, "POINTS HISTORY");
        viewPagerAdapter.addFragment(detailFragment, "SHOP DETAIL");

        _viewPager.setAdapter(viewPagerAdapter);
        _viewPager.setOffscreenPageLimit(3);
        _tabs.setupWithViewPager(_viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.refresh_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            recreate();
        }

        return super.onOptionsItemSelected(item);
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }
}
