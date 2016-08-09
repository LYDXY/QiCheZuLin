package com.tongcheng.qichezulin.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by 林尧 on 2016/8/1.
 */
public class ViewPagerWithoutSlide extends ViewPager {
    private boolean isScrollable = false;

    public ViewPagerWithoutSlide(Context context) {
        super(context);
    }

    public ViewPagerWithoutSlide(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isScrollable == false) {
            return false;
        } else {
            return super.onTouchEvent(event);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (isScrollable == false) {
            return false;
        } else {
            return super.onInterceptTouchEvent(event);
        }
    }

    // public void setPagingEnabled(boolean b) {
    // this.isPagingEnabled = b;
    // }
    //
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);

    }

}