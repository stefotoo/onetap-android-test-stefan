package com.receiptbank.android.testapp.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.receiptbank.android.testapp.R;
import com.receiptbank.android.testapp.utils.Util;

public class DotsView extends LinearLayout {

    // variables
    private int mDotSize;
    private int mDotSpacing;

    // methods
    public DotsView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mDotSize = Util.convertDpiToPixels(getContext(), 8);
        mDotSpacing = Util.convertDpiToPixels(getContext(), 12);

        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
    }

    public void setDots(int count) {
        removeAllViews();

        for (int i = 0; i < count; i++) {
            LayoutParams params = new LayoutParams(mDotSize, mDotSize);

            if (i < count - 1) {
                params.rightMargin = mDotSpacing;
            }

            ImageView iv = new ImageView(getContext());
            iv.setImageResource(R.drawable.ic_dot);
            iv.setLayoutParams(params);

            addView(iv);
        }
    }

    public void setSelectedPosition(int position) {
        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            getChildAt(i).setSelected(i == position);
        }
    }
}
