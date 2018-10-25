package com.yubo.firecontrol.http;

import android.annotation.SuppressLint;
import com.blankj.utilcode.util.NetworkUtils;
import com.yubo.firecontrol.utils.ToastBuilder;

import rx.Subscriber;

/**
 * Created by gj on 2018/5/11.
 * 该类仅供参考，实际业务Code, 根据需求来定义，
 */
public abstract class BaseSubscriber<T> extends Subscriber<T> {

    private boolean isNeedCahe;

    public BaseSubscriber() {
    }

    public abstract void onResult(T t);

    @SuppressLint("MissingPermission")
    @Override
    public void onStart() {
        super.onStart();
        // if  NetworkAvailable no !   must to call onCompleted
        if (!NetworkUtils.isConnected()) {
        //if (PreferencesUtil.getBoolean(Constant.NET_STATE)) {
            ToastBuilder.showShortWarning("无网络，读取缓存数据");
            onCompleted();
        }
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ExceptionHandle.ResponeThrowable) {
            onError((ExceptionHandle.ResponeThrowable) e);
        } else {
            onError(ExceptionHandle.handleException(e));
        }
    }

    public abstract void onError(ExceptionHandle.ResponeThrowable e);

    @Override
    public void onNext(T o) {
        onResult(o);
    }
}