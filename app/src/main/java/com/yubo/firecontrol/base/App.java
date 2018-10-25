package com.yubo.firecontrol.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.yubo.firecontrol.utils.Constant;


/**
 * Created by lyf on 2018/4/25 16:20
 *
 * @author lyf
 * desc：
 */
public class App extends Application {

    private static App mInstance;

    public static App getInstance() {
        if (mInstance == null) {
            synchronized (App.class) {
                mInstance = new App();
            }
        }
        return mInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        // 初始化log
        LogUtils.getConfig().setGlobalTag(Constant.APP_DIR);
        Utils.init(mInstance);
    }

    /***
     * 遍历退出
     */
    public void exit() {
        try {
            ActivityUtils.finishAllActivities();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            new Handler().postDelayed(() -> AppUtils.exitApp(), 200);
        }
    }

}
