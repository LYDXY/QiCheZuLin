package com.tongcheng.qichezulin.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.model.BannerModel;

/**
 * Created by 林尧 on 2016/7/26.
 */
public class NetworkImageHolderView implements Holder<BannerModel> {

    private ImageView imageView;

    @Override
    public View createView(Context context) {
        //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, BannerModel data) {
        // imageView.setImageResource(R.mipmap.yi_jian_zu_che);
        ImageLoader.getInstance().displayImage(data.FImg, imageView);
    }
}

