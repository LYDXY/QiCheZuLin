package com.tongcheng.qichezulin.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.code19.library.DateUtils;
import com.jiongbull.jlog.JLog;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.model.OrderModel;

import java.util.Date;

/**
 * 行程钩子文件
 */
public class XingChengHolderView implements Holder<OrderModel> {

    private TextView tv_car_name;
    private TextView tv_end_to_now_time;
    private View view;

    @Override
    public View createView(Context context) {
        //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
        view = LayoutInflater.from(context).inflate(R.layout.convenientbanner_item0, null);
        return view;
    }

    @Override
    public void UpdateUI(Context context, int position, OrderModel data) {
        tv_car_name = (TextView) view.findViewById(R.id.tv_car_name);
        tv_end_to_now_time = (TextView) view.findViewById(R.id.tv_end_to_now_time);
        tv_car_name.setText(data.FCarName);
        long jiange = DateUtils.subtractDate(DateUtils.string2Date(data.FEndTime, "yyyy-MM-dd HH:mm:ss"), new Date()) / 1000;//除以1000是为了转换成秒;
        long day1 = jiange / (24 * 3600);
        long hour1 = jiange % (24 * 3600) / 3600;
        long minute1 = jiange % 3600 / 60;
        long second1 = jiange % 60 / 60;
        if (jiange >0) {
            tv_end_to_now_time.setText(Math.abs(day1) + "天" + Math.abs(hour1) + "小时" + Math.abs(minute1) + "分" + Math.abs(second1) + "秒");
        }else{
            tv_end_to_now_time.setText("已超时" +Math.abs(day1) + "天" + Math.abs(hour1) + "小时" + Math.abs(minute1) + "分" + Math.abs(second1) + "秒");
        }

    }
}

