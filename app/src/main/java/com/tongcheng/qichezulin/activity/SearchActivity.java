package com.tongcheng.qichezulin.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.tongcheng.qichezulin.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

/**
 * Created by 林尧 on 2016/8/16.
 */
@ContentView(R.layout.activity_search)
public class SearchActivity extends Activity implements View.OnClickListener {
    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }
}
