package com.receiptbank.android.testapp.activities;

import android.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.receiptbank.android.testapp.R;
import com.receiptbank.android.testapp.adapters.CustomAdapter;
import com.receiptbank.android.testapp.fragments.MainFragment;
import com.receiptbank.android.testapp.models.CustomItem;
import com.receiptbank.android.testapp.ui.DividerItemDecoration;
import com.receiptbank.android.testapp.ui.DotsView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity
        extends
        AppCompatActivity
        implements
        TabLayout.OnTabSelectedListener,
        ViewPager.OnPageChangeListener {

    // constants
    public static final String BASIC_TAG = MainActivity.class.getName();

    private static final int TAB_RECENT = 0;
    private static final int TAB_ALL = 1;
    private static final int TAB_PROCESSING = 2;

    private static final int RECYCLERVIEW_ITEM_COUNT = 6;
    private static final int VIEWPAGER_ITEM_COUNT = 2;

    // variables
    private CustomAdapter mRecyclerViewAdapter;
    private ViewPagerAdapter mViewPagerAdapter;

    // UI
    @Bind(R.id.toolbar_activity_main)
    Toolbar toolbar;
    @Bind(R.id.tl_activity_main)
    TabLayout tl;
    @Bind(R.id.vp_activity_main_horizontal)
    ViewPager vpHorizontal;
    @Bind(R.id.dv_activity_main)
    DotsView dv;
    @Bind(R.id.rv_activity_main)
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initVariables();
        initListeners();
        setupToolbar();
        setupRecyclerView();
        setupDots();
        setupViewPager();
    }

    private void initVariables() {
        mRecyclerViewAdapter = new CustomAdapter(this);
        mViewPagerAdapter = new ViewPagerAdapter();
    }

    private void initListeners() {
        tl.setOnTabSelectedListener(this);
        vpHorizontal.addOnPageChangeListener(this);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupRecyclerView() {
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        rv.setAdapter(mRecyclerViewAdapter);

        refreshAdapterData(TAB_RECENT);
    }

    private void setupDots() {
        dv.setDots(VIEWPAGER_ITEM_COUNT);
        dv.setSelectedPosition(0);
    }

    private void setupViewPager() {
        vpHorizontal.setAdapter(mViewPagerAdapter);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        refreshAdapterData(tab.getPosition());
    }

    private void refreshAdapterData(int position) {
        mRecyclerViewAdapter.clearData(true);
        ArrayList<CustomItem> items = new ArrayList<>();

        int imageResId = 0;
        String title = null;
        String content = null;

        switch (position) {
            case TAB_RECENT: {
                imageResId = R.drawable.ic_restore_black_24dp;
                title = getString(R.string.tab_recent);
                content = getString(R.string.tab_recent);
                break;
            }

            case TAB_ALL: {
                imageResId = R.drawable.ic_content_paste_black_24dp;
                title = getString(R.string.tab_all);
                content = getString(R.string.tab_all);
                break;
            }

            case TAB_PROCESSING: {
                imageResId = R.drawable.ic_file_upload_black_24dp;
                title = getString(R.string.tab_processing);
                content = getString(R.string.tab_processing);
                break;
            }
        }

        for (int i = 0; i < RECYCLERVIEW_ITEM_COUNT; i++) {
            CustomItem item = new CustomItem(imageResId,
                    getString(R.string.tv_item_recyclerview_title, title),
                    getString(R.string.tv_item_recyclerview_content, content));

            items.add(item);
        }

        mRecyclerViewAdapter.addData(items, true);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        // Do nothing
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        // Do nothing
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // Do nothing
    }

    @Override
    public void onPageSelected(int position) {
        dv.setSelectedPosition(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // Do nothing
    }

    // inner classes
    private class ViewPagerAdapter extends FragmentPagerAdapter {

        // constructor
        public ViewPagerAdapter() {
            super(getFragmentManager());
        }

        // methods
        @Override
        public Fragment getItem(int position) {
            return MainFragment.getInstance(position);
        }

        @Override
        public int getCount() {
            return VIEWPAGER_ITEM_COUNT;
        }
    }
}
