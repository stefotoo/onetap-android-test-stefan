package com.receiptbank.android.testapp.activities;

import android.app.Fragment;
import android.content.Intent;
import android.location.Location;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
import com.gun0912.tedpermission.PermissionListener;
import com.receiptbank.android.testapp.R;
import com.receiptbank.android.testapp.TestApp;
import com.receiptbank.android.testapp.adapters.WeatherAdapter;
import com.receiptbank.android.testapp.fragments.MainFragment;
import com.receiptbank.android.testapp.rest.model.Forecast;
import com.receiptbank.android.testapp.rest.model.WeatherResponse;
import com.receiptbank.android.testapp.ui.DividerItemDecoration;
import com.receiptbank.android.testapp.ui.DotsView;
import com.receiptbank.android.testapp.utils.PermissionUtil;
import com.receiptbank.android.testapp.utils.Util;
import com.rey.material.app.Dialog;
import com.rey.material.app.SimpleDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rx.Subscription;
import rx.functions.Action1;

public class MainActivity
        extends
        AppCompatActivity
        implements
        TabLayout.OnTabSelectedListener,
        ViewPager.OnPageChangeListener,
        WeatherAdapter.ForecastClickListener {

    // constants
    public static final String BASIC_TAG = MainActivity.class.getName();

    private static final int TAB_5_DAYS = 0;
    private static final int TAB_16_DAYS = 1;

    private static final int VIEWPAGER_ITEM_COUNT = 2;

    // variables
    private WeatherAdapter mRecyclerViewAdapter;
    private ViewPagerAdapter mViewPagerAdapter;
    private List<Forecast> mFiveDaysForecast = new ArrayList<>();
    private List<Forecast> mSixteenDaysForecast = new ArrayList<>();
    private boolean mHasWeatherData;

    // UI
    @Bind(R.id.cl_activity_main_activity_container)
    CoordinatorLayout clContainer;
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
        showLocationDialog();
        getCurrentLocation();
    }

    private void initVariables() {
        mRecyclerViewAdapter = new WeatherAdapter(this, this);
        mViewPagerAdapter = new ViewPagerAdapter();
    }

    private void initListeners() {
        tl.setOnTabSelectedListener(this);
        vpHorizontal.addOnPageChangeListener(this);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
    }

    private void setupRecyclerView() {
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        rv.setAdapter(mRecyclerViewAdapter);
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
        refreshWeatherData(tab.getPosition());
    }

    private void showLocationDialog() {
        SimpleDialog.Builder builder = new SimpleDialog.Builder(com.rey.material.R.style.Material_App_Dialog_Simple_Light);

        builder.message(getString(R.string.dialog_msg_location)).
                title(getString(R.string.dialog_title_warning)).
                positiveAction(getString(R.string.dialog_btn_ok));

        final Dialog dialog = builder.build(this);
        dialog.positiveActionClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void getCurrentLocation() {
        if (PermissionUtil.hasLocationPermissions(this)) {
            turnOnLocationRequests();
        } else {
            PermissionUtil.askLocationPermissions(
                    this,
                    new PermissionListener() {

                        @Override
                        public void onPermissionGranted() {
                            turnOnLocationRequests();
                        }

                        @Override
                        public void onPermissionDenied(ArrayList<String> arrayList) {
                            Toast.makeText(MainActivity.this,
                                    getString(R.string.permissions_location_denied),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
            );
        }
    }

    private void turnOnLocationRequests() {
        LocationRequest request = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setNumUpdates(5)
                .setInterval(10 * 60 * 1000); // update interval 10 minutes

        ReactiveLocationProvider locationProvider = new ReactiveLocationProvider(this);
        locationProvider.getUpdatedLocation(request).subscribe(new Action1<Location>() {
            @Override
            public void call(Location location) {
                loadWeatherData(16, location);
            }
        });
    }

    private void loadWeatherData(int days, Location location) {
        TestApp.getRestClient().getAuthRestService().getDailyForecast(
                location.getLatitude(),
                location.getLongitude(),
                days,
                "889ea6ae9738bd030d9aabb9f82520b1",
                new Callback<WeatherResponse>() {
            @Override
            public void success(WeatherResponse weatherResponse, Response response) {
                if (weatherResponse != null && Util.isListNotEmpty(weatherResponse.getForecastList())) {
                    mSixteenDaysForecast.addAll(weatherResponse.getForecastList());
                    setupFiveDaysList(weatherResponse.getForecastList());
                    mHasWeatherData = true;

                    if (weatherResponse.getCity() != null &&
                            weatherResponse.getCity().getName() != null) {
                        getSupportActionBar().setTitle(weatherResponse.getCity().getName());
                    }

                    refreshWeatherData(TAB_5_DAYS);
                } else {
                    Toast.makeText(MainActivity.this,
                            getString(R.string.toast_no_forecast_available),
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(MainActivity.this,
                        error.toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupFiveDaysList(List<Forecast> fullList) {
        for (int i = 0; i < 5; i++) {
            mFiveDaysForecast.add(fullList.get(i));
        }
    }

    private void refreshWeatherData(int position) {
        mRecyclerViewAdapter.clearData(true);

        if (mHasWeatherData) {
            switch (position) {
                case TAB_5_DAYS: {
                    mRecyclerViewAdapter.addData(mFiveDaysForecast, true);
                    mRecyclerViewAdapter.notifyDataSetChanged();
                    break;
                }
                case TAB_16_DAYS: {
                    mRecyclerViewAdapter.addData(mSixteenDaysForecast, true);
                    mRecyclerViewAdapter.notifyDataSetChanged();
                    break;
                }
            }
        }
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

    @Override
    public void onForecastClick(Forecast forecast) {
        Snackbar.make(clContainer,
                "Pressure: " + forecast.getPressure(),
                Snackbar.LENGTH_SHORT).show();
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
