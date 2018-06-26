package me.chulwoo.naver.blog.search.ui.intro;

import me.chulwoo.naver.blog.search.ui.base.mvp.MvpPresenter;
import me.chulwoo.naver.blog.search.ui.base.mvp.MvpView;

public interface IntroContract {

    interface View extends MvpView {

        void showKeyword(String keyword);

        void showKeywordClearView();

        void hideKeywordClearView();

        void showError(Throwable e);
    }

    interface Presenter extends MvpPresenter<View> {

        void search();

        void onKeywordChange(CharSequence keyword);

        void onClearClick();
    }
}

