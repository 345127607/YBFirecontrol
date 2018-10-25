package com.yubo.firecontrol.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by lyf on 2018/8/28 17:18
 *
 * @author lyf
 * desc：懒加载Fragment
 */
public abstract class BaseLazyLoadFragment extends Fragment {

    /**
     * 视图是否已经创建
     */
    private boolean isViewCreated;
    /**
     * 该fragment是否对用户可见
     */
    private boolean isUiVisible;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isUiVisible = true;
            lazyLoad();
        }else{
            isUiVisible = false;
        }
    }

    private void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,
        // 并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUiVisible) {
            lazyLoadData();
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUiVisible = false;

        }
    }

    /**
     * 懒加载数据（网络请求数据等）
     */
    protected abstract void lazyLoadData();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewCreated = false;
        isUiVisible = false;
    }

}
