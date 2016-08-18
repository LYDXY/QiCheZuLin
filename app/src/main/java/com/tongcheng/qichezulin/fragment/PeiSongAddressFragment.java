package com.tongcheng.qichezulin.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tongcheng.qichezulin.R;

/**
 * Created by 林尧 on 2016/8/18.
 */
public class PeiSongAddressFragment extends Fragment {
    public static FaPiaoTaiTouFragment getInstance() {
        FaPiaoTaiTouFragment sf = new FaPiaoTaiTouFragment();

        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pei_song_address, null);
        return v;
    }
}
