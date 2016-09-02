package com.tongcheng.qichezulin.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiongbull.jlog.JLog;
import com.tongcheng.qichezulin.Param.ParamSetInvoice;
import com.tongcheng.qichezulin.Param.ParamSetInvoiceAddress;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.model.JsonBase2;
import com.tongcheng.qichezulin.model.SetInvoiceModel;
import com.tongcheng.qichezulin.spinnerView.NiceSpinner;
import com.tongcheng.qichezulin.utils.Utils;
import com.tongcheng.qichezulin.utils.UtilsJson;
import com.tongcheng.qichezulin.utils.UtilsUser;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 林尧 on 2016/8/18.
 */

@ContentView(R.layout.activity_add_fa_piao_address)
public class AddFaPiaoAddressActivity extends PuTongActivity {




    @Override
    void initData() {

    }

    @Override
    void initView() {
        tv_first.setVisibility(View.VISIBLE);
        tv_first.setText("新增配送地址");
        tv_second.setText("保存");
        tv_second.setVisibility(View.VISIBLE);
        List<String> dataset = new LinkedList<>(Arrays.asList("One", "Two", "Three", "Four", "Five"));
//        NiceSpinner_area.attachDataSource(dataset);

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
    public void add_fa_piao_address(String user_id,String text) {
        ParamSetInvoiceAddress paramSetInvoice = new ParamSetInvoiceAddress();
        paramSetInvoice.user_id = user_id;
        paramSetInvoice.name=text;
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
                        Utils.ShowText2(AddFaPiaoAddressActivity.this,base.info);

                    } else {
                        JLog.w("插入发票抬头失败");
                        Utils.ShowText2(AddFaPiaoAddressActivity.this,base.info);

                    }
                } catch (Exception E) {
                    E.printStackTrace();
                }

            }
        });
    }

}
