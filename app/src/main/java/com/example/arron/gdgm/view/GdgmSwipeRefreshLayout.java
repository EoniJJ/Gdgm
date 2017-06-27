package com.example.arron.gdgm.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

import com.example.arron.gdgm.R;

/**
 * Created by Arron on 2017/4/18.
 */

public class GdgmSwipeRefreshLayout extends SwipeRefreshLayout {
    public GdgmSwipeRefreshLayout(Context context) {
        super(context);
    }

    public GdgmSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setColorSchemeResources(R.color.titleBackgroundColor);
    }
}
