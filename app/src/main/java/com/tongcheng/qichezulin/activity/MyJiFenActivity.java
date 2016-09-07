package com.tongcheng.qichezulin.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiongbull.jlog.JLog;
import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.tongcheng.qichezulin.Param.ParamJiFenLog;
import com.tongcheng.qichezulin.Param.ParamUserJf;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.model.JiFenLogModel;
import com.tongcheng.qichezulin.model.JiFenModel;
import com.tongcheng.qichezulin.model.JsonBase2;
import com.tongcheng.qichezulin.utils.UtilsJson;
import com.tongcheng.qichezulin.utils.UtilsUser;
import com.tongcheng.qichezulin.view.ListViewForScrollView2;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by 林尧 on 2016/8/18.
 */

@ContentView(R.layout.activity_my_ji_fen)
public class MyJiFenActivity extends PuTongActivity2 {


    private String userid;
    private String token;
    private Adapter adapter;

    @ViewInject(R.id.list_for_s2)
    ListViewForScrollView2 list_for_s2;

    @ViewInject(R.id.tv_show_ji_fen)
    TextView tv_show_ji_fen;

    @Override
    void initData() {

    }

    @Override
    void initView() {
        tv_first.setVisibility(View.VISIBLE);
        tv_first.setText("我的积分");
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                onBackPressed();
                break;


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
        setListenerOnView();
        final UtilsUser utilsUser = new UtilsUser(MyJiFenActivity.this);
        userid=UtilsUser.getUserID(MyJiFenActivity.this);
        token = utilsUser.getToken_lqs();
        if (!token.equals("")) {
            //获取成功
            JLog.w("token获取成功");
            get_now_ji_fen(userid,token);
        } else {
            utilsUser.init_Callback_getToken(new UtilsUser.Callback_getToken() {
                @Override
                public void start() {
                    //tokentime超时，重网络获取
                    JLog.w("tokentime超时，重网络获取token,tokentime");
                    token = utilsUser.getToken_lqs();
                    JLog.w(token);
                    get_now_ji_fen(userid,token);
                }
            });
        }

        get_use_case_list(userid,token);


    }

    //获取当前的积分
    public void get_now_ji_fen(String user_id,String token) {
        ParamUserJf paramUserJf = new ParamUserJf();
        paramUserJf.user_id = user_id;
        paramUserJf.token=token;
        Callback.Cancelable cancelable
                = x.http().post(paramUserJf, new Callback.CommonCallback<String>() {
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
                    Type type = new TypeToken<JsonBase2<List<JiFenModel>>>() {
                    }.getType();
                    JsonBase2<List<JiFenModel>> base = gson
                            .fromJson(result, type);
                    if (!base.status.toString().trim().equals("0")) {
                        if (base.data != null) {
                            JLog.w("获取积分成功");
                            JLog.w("积分值=" + base.data.get(0).FJiFen);
                            tv_show_ji_fen.setText(base.data.get(0).FJiFen);
                        }

                    } else {
                        JLog.w("获取积分值失败");
                    }
                } catch (Exception E) {
                    E.printStackTrace();
                }

            }
        });
    }


    //获取积分使用情况列表
    public void get_use_case_list(String user_id,String token) {
        ParamJiFenLog paramJiFenLog = new ParamJiFenLog();
        paramJiFenLog.user_id = user_id;
        paramJiFenLog.token = token;
        Callback.Cancelable cancelable
                = x.http().post(paramJiFenLog, new Callback.CommonCallback<String>() {
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
                    Type type = new TypeToken<JsonBase2<List<JiFenLogModel>>>() {
                    }.getType();
                    JsonBase2<List<JiFenLogModel>> base = gson
                            .fromJson(result, type);
                    if (!base.status.toString().trim().equals("0")) {
                        if (base.data != null) {
                            JLog.w("获取积分记录成功");
                            if (adapter == null) {
                                adapter = new Adapter<JiFenLogModel>(MyJiFenActivity.this, R.layout.listview_item_log) {
                                    @Override
                                    protected void convert(AdapterHelper helper, JiFenLogModel item) {
                                        if (item.FType.equals("1")) {
                                            helper.setText(R.id.tv_show_ji_fen_type, "充值赠送");
                                        } else if (item.FType.equals("2")) {
                                            helper.setText(R.id.tv_show_ji_fen_type, "租凭赠送");
                                        } else if (item.FType.equals("3")) {
                                            helper.setText(R.id.tv_show_ji_fen_type, "充电赠送");
                                        } else if (item.FType.equals("4")) {
                                            helper.setText(R.id.tv_show_ji_fen_type, "美容赠送");
                                        } else if (item.FType.equals("5")) {
                                            helper.setText(R.id.tv_show_ji_fen_type, "维修赠送");
                                        }
                                        helper.setText(R.id.show_hao_ji_fen, item.FJiFen);
                                        helper.setText(R.id.tv_show_ji_fen_date, item.FCreateDate);

                                    }
                                };
                                adapter.addAll(base.data);
                                list_for_s2.setAdapter(adapter);
                            } else {
                                adapter.addAll(base.data);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    } else {
                        JLog.w("获取积分记录失败");
                    }
                } catch (Exception E) {
                    E.printStackTrace();
                }

            }
        });
    }
}
