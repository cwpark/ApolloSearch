package me.chulwoo.naver.blog.search.ui.intro;

import android.text.TextUtils;

import javax.inject.Inject;

import me.chulwoo.naver.blog.search.navigator.SearchNavigator;
import me.chulwoo.naver.blog.search.ui.base.mvp.BasePresenter;
import me.chulwoo.naver.blog.search.ui.exception.EmptyKeywordException;

public class IntroPresenter extends BasePresenter<IntroContract.View>
        implements IntroContract.Presenter {

    private SearchNavigator navigator;
    private String currentKeyword;

    @Inject
    public IntroPresenter(SearchNavigator navigator) {
        this.navigator = navigator;
        this.currentKeyword = "";
    }

    @Override
    public void search() {
        if (TextUtils.isEmpty(currentKeyword)) {
            view.showError(new EmptyKeywordException());
        } else {
            navigator.search(currentKeyword);
        }
    }

    @Override
    public void onKeywordChange(CharSequence keyword) {
        currentKeyword = keyword.toString().trim();
        if (TextUtils.isEmpty(currentKeyword)) {
            view.hideKeywordClearView();
        } else {
            view.showKeywordClearView();
        }
    }

    @Override
    public void onClearClick() {
        view.showKeyword("");
    }
}
