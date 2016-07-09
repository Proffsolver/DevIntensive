package com.softdesign.devintensive.utils;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;


public class MyBehavior extends AppBarLayout.ScrollingViewBehavior {

    public MyBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        child.setY(dependency.getTranslationY() + dependency.getHeight());
        return super.onDependentViewChanged(parent, child, dependency);
    }
}
