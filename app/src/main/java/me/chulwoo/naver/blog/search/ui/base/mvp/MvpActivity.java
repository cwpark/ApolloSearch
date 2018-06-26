package me.chulwoo.naver.blog.search.ui.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import me.chulwoo.naver.blog.search.ui.base.BaseActivity;
import me.chulwoo.naver.blog.search.util.DLog;

public abstract class MvpActivity<Presenter extends MvpPresenter> extends BaseActivity {

    @Inject
    Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            //noinspection unchecked
            presenter.attach((MvpView) this);
        } catch (ClassCastException e) {
            DLog.e(this, MvpActivity.class.getName() + " must be implemented " + MvpView.class.getName());
        }
    }

    @Override
    protected void onDestroy() {
        presenter.detach();
        super.onDestroy();
    }
}
