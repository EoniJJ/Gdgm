package com.example.arron.gdgm.base;

import com.example.arron.gdgm.network.NetworkManager;

import java.lang.ref.WeakReference;

/**
 * Created by Arron on 2017/4/12.
 */

public abstract class BasePresenterImpl<V extends BaseView> implements BasePresenter<V> {
    protected WeakReference<V> view;
    @Override
    public void attachView(V view) {
        this.view = new WeakReference<V>(view);
        init();
    }

    public V getView() {
        return view.get();
    }

    public boolean isAttachView() {
        if (view != null && view.get() != null) {
            return true;
        }
        return false;
    }

    protected abstract void init();

    @Override
    public void detachView() {
        cancelTasks();
        view.clear();
        view = null;
    }

    protected void cancelTasks() {
        NetworkManager.cancel(getClass().getSimpleName());
    }
}
