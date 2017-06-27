package com.example.arron.gdgm.base;

/**
 * Created by Arron on 2017/4/12.
 */

public interface  BasePresenter <V extends BaseView>{
    void attachView(V view);

    void detachView();
}
