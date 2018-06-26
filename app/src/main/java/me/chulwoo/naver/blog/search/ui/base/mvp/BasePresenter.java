package me.chulwoo.naver.blog.search.ui.base.mvp;

import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter<View extends MvpView> implements MvpPresenter<View> {

    protected CompositeDisposable compositeDisposable;
    protected View view;

    @Override
    public void attach(View view) {
        this.view = view;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void detach() {
        this.compositeDisposable.dispose();
    }
}
