package com.tongcheng.qichezulin.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.jaredrummler.materialspinner.MaterialSpinnerAdapter;
import com.jaredrummler.materialspinner.MaterialSpinnerBaseAdapter;
import com.jiongbull.jlog.JLog;
import com.tongcheng.qichezulin.Param.ParamGetProvince;
import com.tongcheng.qichezulin.Param.ParamSetInvoiceAddress;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.model.JsonBase;
import com.tongcheng.qichezulin.model.JsonBase2;
import com.tongcheng.qichezulin.model.SetInvoiceModel;
import com.tongcheng.qichezulin.model.ShengFenModel;
import com.tongcheng.qichezulin.utils.Utils;
import com.tongcheng.qichezulin.utils.UtilsJson;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 林尧 on 2016/8/18.
 */

@ContentView(R.layout.activity_add_fa_piao_address)
public class AddFaPiaoAddressActivity extends PuTongActivity {

    @ViewInject(R.id.spinner)
    MaterialSpinner spinner;

    @ViewInject(R.id.spinner2)
    MaterialSpinner spinner2;

    @ViewInject(R.id.spinner3)
    MaterialSpinner spinner3;

    @Override
    void initData() {

    }

    @Override
    void initView() {
        tv_first.setVisibility(View.VISIBLE);
        tv_first.setText("新增配送地址");
        tv_second.setText("保存");
        tv_second.setVisibility(View.VISIBLE);
        get_shengs_data();

   /*     spinner2.setItems();
        spinner2.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
              //  Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });*/

        spinner3.setItems("广东省", "广东省", "广东省", "广东省", "广东省", "广东省", "广东省", "广东省", "广东省", "广东省", "广东省", "广东省", "广东省", "广东省", "广东省", "广东省", "广东省", "广东省", "广东省", "广东省", "广东省", "广东省", "广东省", "广东省", "广东省", "广东省", "广东省", "广东省", "广东省", "广东省");
        spinner3.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                //   Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                onBackPressed();
                break;
            case R.id.tv_second:
                JLog.i("保存");
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
        setListenerOnView();
    }


    //保存一条新的配送地址
    public void add_fa_piao_address(String user_id, String text) {
        ParamSetInvoiceAddress paramSetInvoice = new ParamSetInvoiceAddress();
        paramSetInvoice.user_id = user_id;
        paramSetInvoice.name = text;
        Callback.Cancelable cancelable
                = x.http().post(paramSetInvoice, new Callback.CommonCallback<String>() {
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
                    Type type = new TypeToken<JsonBase2<List<SetInvoiceModel>>>() {
                    }.getType();
                    JsonBase2<List<SetInvoiceModel>> base = gson
                            .fromJson(result, type);
                    if (!base.status.toString().trim().equals("0")) {
                        JLog.w("插入发票抬头成功");
                        Utils.ShowText2(AddFaPiaoAddressActivity.this, base.info);

                    } else {
                        JLog.w("插入发票抬头失败");
                        Utils.ShowText2(AddFaPiaoAddressActivity.this, base.info);

                    }
                } catch (Exception E) {
                    E.printStackTrace();
                }

            }
        });
    }

    //读取省数据
    public void get_shengs_data() {
        ParamGetProvince paramSetInvoice = new ParamGetProvince();
        Callback.Cancelable cancelable
                = x.http().post(paramSetInvoice, new Callback.CommonCallback<String>() {
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
                    Type type = new TypeToken<JsonBase<List<ShengFenModel>>>() {
                    }.getType();
                    JsonBase<List<ShengFenModel>> base = gson
                            .fromJson(result, type);
                    if (!base.status.toString().trim().equals("0")) {
                        JLog.w("获取省份数据成功");
                        List<String> strings = new ArrayList<String>();
                        final List<String> sheng_ids = new ArrayList<String>();
                        for (int i = 0; i < base.data.size(); i++) {
                            strings.add(base.data.get(i).FName.toString());
                            sheng_ids.add(base.data.get(i).PID.toString());
                        }

                        spinner.setItems(strings);
                        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                            @Override
                            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                                JLog.w("position" + position);
                                JLog.w("城市id" + sheng_ids.get(position).toString() + "");

                            }
                        });

                    } else {
                        JLog.w("获取省份数据失败");


                    }
                } catch (Exception E) {
                    E.printStackTrace();
                }

            }
        });
    }
    //读取城市数据

}
