package com.tongcheng.qichezulin.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiongbull.jlog.JLog;
import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.tongcheng.qichezulin.Param.ParamInvoicelist;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.model.InvoicelistModel;
import com.tongcheng.qichezulin.model.JsonBase2;
import com.tongcheng.qichezulin.pulltorefresh.PullToRefreshLayout;
import com.tongcheng.qichezulin.pulltorefresh.PullableListView;
import com.tongcheng.qichezulin.utils.UtilsJson;
import com.tongcheng.qichezulin.utils.UtilsUser;

import org.xutils.common.Callback;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by 林尧 on 2016/8/18.
 */
public class PeiSongAddressFragment extends Fragment implements View.OnClickListener {


    private TextView iv_add_pei_song_address;
    private PullToRefreshLayout prl_prl_05;
    private PullableListView plv_pei_song_list; //list 控件

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pei_song_address, null);
        prl_prl_05 = (PullToRefreshLayout) v.findViewById(R.id.prl_prl_05);
        plv_pei_song_list = (PullableListView) v.findViewById(R.id.plv_pei_song_list);
        iv_add_pei_song_address = (TextView) v.findViewById(R.id.iv_add_pei_song_address);
        iv_add_pei_song_address.setOnClickListener(this);
        setOnPullListenerOnprl_prl();
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (UtilsUser.getUser(getContext()) != null) {
            get_address(UtilsUser.getUser(getContext()).PID);
        }

    }

    @Override
    public void onClick(View view) {

    }

    public void setOnPullListenerOnprl_prl() {
        prl_prl_05.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                // get_order_yu_yue_list(user_id,status,page,page_size);
                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        });
    }

    //获取配送地址
    public void get_address(String user_id) {

    }
}
