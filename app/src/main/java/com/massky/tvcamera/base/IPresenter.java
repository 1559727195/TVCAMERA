package com.massky.tvcamera.base;

public interface IPresenter<T extends IView> {
    void attachView(T view);
    void detachView();
}
