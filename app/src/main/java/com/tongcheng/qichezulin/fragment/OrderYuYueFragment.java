package com.tongcheng.qichezulin.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tongcheng.qichezulin.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

/**
 * Created by 林尧 on 2016/8/18.
 */

@ContentView(R.layout.fragment_order_yu_yue)

public class OrderYuYueFragment extends Fragment {
    private boolean injected = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        injected = true;
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected) {
            x.view().inject(this, this.getView());
        }
    }
}
