package com.tongcheng.qichezulin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.model.CarTypeModel;
import com.tongcheng.qichezulin.view.ChoiceView;

import java.util.List;
import java.util.zip.DeflaterOutputStream;

/**
 * Created by Administrator on 2016/8/30 0030.
 */
public class CarTypeAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<CarTypeModel> list;
    private Context c;

    public CarTypeAdapter(Context c, List<CarTypeModel> list) {
        this.list = list;
        this.c = c;
        layoutInflater = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return this.list.size();

    }

    @Override
    public CarTypeModel getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final ChoiceView view;
        if (convertView == null) {
            view = new ChoiceView(c);
        } else {
            view = (ChoiceView) convertView;
        }
        view.setText(list.get(position).FTypeName);
        return view;
    }


    private class ViewHolder {
        TextView tv_show_car_type_name;
        ImageView iv_is_chose;
    }
}
