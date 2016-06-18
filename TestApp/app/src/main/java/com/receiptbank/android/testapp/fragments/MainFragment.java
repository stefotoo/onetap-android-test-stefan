package com.receiptbank.android.testapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.receiptbank.android.testapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainFragment extends Fragment {

    // constants
    public static final String BASIC_TAG = MainFragment.class.getName();

    private static final String BUNDLE_POSITION = "position";

    // variables
    private int mPosition;

    // UI
    @Bind(R.id.tv_fragment_main_title)
    TextView tvTitle;
    @Bind(R.id.tv_fragment_main_subtitle)
    TextView tvSubtitle;
    @Bind(R.id.iv_fragment_main)
    ImageView iv;

    // get instance methods
    public static MainFragment getInstance(int position) {
        MainFragment fragment = new MainFragment();

        Bundle args = new Bundle();
        args.putInt(BUNDLE_POSITION, position);

        fragment.setArguments(args);

        return fragment;
    }

    // methods
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initArgs();
        setupUi();
    }

    private void initArgs() {
        Bundle args = getArguments();

        mPosition = args.getInt(BUNDLE_POSITION);
    }

    private void setupUi() {
        tvTitle.setText(mPosition == 0 ?
                getString(R.string.tv_fragment_main_first_title) :
                getString(R.string.tv_fragment_main_second_title));
        tvSubtitle.setText(mPosition == 0 ?
                getString(R.string.tv_fragment_main_first_subtitle) :
                getString(R.string.tv_fragment_main_second_subtitle));
        iv.setImageResource(mPosition == 0 ?
                R.drawable.ic_trending_up_black_24dp :
                R.drawable.ic_description_black_24dp);
    }
}
