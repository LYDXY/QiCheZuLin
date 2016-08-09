package com.tongcheng.qichezulin.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * Created by 林尧 on 2016/8/4.
 */
public class ExpandableListViewForScrollView extends ExpandableListView {

    public ExpandableListViewForScrollView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public ExpandableListViewForScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public ExpandableListViewForScrollView(Context context, AttributeSet attrs,
                                           int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
