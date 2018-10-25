package com.yubo.firecontrol.base;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by lyf on 2017/6/15.
 * Presenter公共注册类，所有界面与数据交互的类都需要通过继承自此类
 * @author lyf
 */
public abstract class BasePresenter<T extends BaseActivity> {

    private CompositeSubscription mCompositeSubscription;

    /**
     * RxJava取消注册，以避免内存泄露
     */
    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    /**
     * RxJava注册
     * @param subscriber subscriber
     */
    public void addSubscription(Subscription subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscriber);
    }
}
