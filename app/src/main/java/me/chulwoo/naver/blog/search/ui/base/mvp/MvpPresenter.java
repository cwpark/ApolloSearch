package me.chulwoo.naver.blog.search.ui.base.mvp;

public interface MvpPresenter<View extends MvpView> {

    void attach(View view);

    void detach();
}
