package com.tongcheng.qichezulin.utils;

import android.os.Handler;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

public abstract class Timekeeper_lqs {

    private boolean isrunning = false;
    private int timekey = 0;

    private Handler handler_message = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            callback(timekey);
        }
    };

    private Handler handler_timekeeper = new Handler() {

    };

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            timekey--;
            if (isrunning) {
                handler_message.sendEmptyMessage(0);
            }
            if (timekey > 0 && isrunning) {
                handler_timekeeper.postDelayed(runnable, 1000);
            } else {
                stop();
            }
        }
    };

    public abstract void callback(int timekey);

    public void start(int timekey) {
        if (isrunning == false) {
            this.timekey = timekey + 1;
            isrunning = true;
            handler_timekeeper.postDelayed(runnable, 0);
        }
    }

    public void stop() {
        isrunning = false;
    }

}
