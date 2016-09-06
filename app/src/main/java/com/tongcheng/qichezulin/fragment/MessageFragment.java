package com.tongcheng.qichezulin.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;


import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiongbull.jlog.JLog;
import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.tongcheng.qichezulin.Param.ParamGetMessage;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.model.JsonBase2;
import com.tongcheng.qichezulin.model.MessageModel;
import com.tongcheng.qichezulin.pulltorefresh.PullToRefreshLayout;
import com.tongcheng.qichezulin.pulltorefresh.PullableListView;
import com.tongcheng.qichezulin.utils.UtilsJson;
import com.tongcheng.qichezulin.utils.UtilsUser;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.List;


/**
 * Created by 林尧 on 2016/8/18.
 */

@ContentView(R.layout.fragment_message)
public class MessageFragment extends PuTongFragment {

    @ViewInject(R.id.prl_prl_03)
    PullToRefreshLayout prl_prl_03;

    @ViewInject(R.id.plv_order_finish_list)
    PullableListView plv_order_finish_list;

    Adapter adapter;

    @ViewInject(R.id.tl_4)
    SegmentTabLayout tl_4;
    private String[] mTitles_2 = {"订单消息", "系统消息"};

    @Override
    void setClickListenerOnView() {

    }

    @Override
    void initData() {

    }

    @Override
    void initView() {
        getSystemMessage(UtilsUser.getUserID(getActivity()),"2");
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tl_4.setTabData(mTitles_2);
        tl_4.setCurrentTab(0);
     //   tl_4.showDot(1);
        tl_4.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (position == 0) {
                    adapter.clear();
                    getSystemMessage(UtilsUser.getUserID(getActivity()),"2");
                }else if (position == 1) {
                    adapter.clear();
                    getSystemMessage(UtilsUser.getUserID(getActivity()),"3");
                }
            }

            @Override
            public void onTabReselect(int position) {
                if (position == 0) {
                    adapter.clear();
                    getSystemMessage(UtilsUser.getUserID(getActivity()),"2");
                }else if (position == 1) {
                    adapter.clear();
                    getSystemMessage(UtilsUser.getUserID(getActivity()),"3");
                }

            }
        });
        prl_prl_03.setPullDownEnable(false);
        prl_prl_03.setPullUpEnable(false);
    }

    @Override
    public void onStop() {
        super.onStop();
        JLog.w("onStop");

    }

    @Override
    public void onPause() {
        super.onPause();
        JLog.w("onPause");

    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        JLog.w("onDestroy");

    }

    @Override
    public void onResume() {
        super.onResume();
        JLog.w("onResume");
    }


    //获取系统/订单消息 列表
    public void getSystemMessage(String user_id,String type_id){


        ParamGetMessage paramGetMessage = new ParamGetMessage();
        paramGetMessage.user_id = user_id;
        paramGetMessage.type_id = type_id + "";
        Callback.Cancelable cancelable
                = x.http().post(paramGetMessage, new Callback.CommonCallback<String>() {
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }


            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(String result) {
                try {
                    UtilsJson.printJsonData(result);
                    Gson gson = new Gson();
                    Type type = new TypeToken<JsonBase2<List<MessageModel>>>() {
                    }.getType();
                    JsonBase2<List<MessageModel>> base = gson
                            .fromJson(result, type);
                    if (!base.status.toString().trim().equals("0")) {
                        if (base.data != null) {
                            JLog.w("获取消息成功");
                            if (adapter == null) {
                                adapter = new Adapter<MessageModel>(getActivity(), R.layout.listview_message_item0) {
                                    @Override
                                    protected void convert(final AdapterHelper helper, final MessageModel item) {
                                        final int position = helper.getPosition();
                                        helper.setText(R.id.tv_show_context, item.FContent)
                                                .setText(R.id.tv_create_date, item.FSendDate);
                                        helper.getView(R.id.ck_is_choose).setVisibility(View.VISIBLE);
                                        CheckBox checkBox = helper.getView(R.id.ck_is_choose);
                                        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                            @Override
                                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                                            }
                                        });
                                    }
                                };
                                adapter.addAll(base.data);
                                plv_order_finish_list.setAdapter(adapter);
                            } else {
                                adapter.addAll(base.data);
                                adapter.notifyDataSetChanged();
                            }


                        }
                    } else {
                        JLog.w("获取消息失败");
                    }
                } catch (Exception E) {
                    E.printStackTrace();
                }

            }
        });

    }


}

