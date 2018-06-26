package me.chulwoo.naver.blog.search.ui.search;

import android.os.Bundle;

import javax.inject.Inject;

import me.chulwoo.naver.blog.search.ui.ExtraKeys;
import me.chulwoo.naver.blog.search.ui.base.SingleFragmentActivity;

public class SearchActivity extends SingleFragmentActivity<SearchFragment> {

    @Inject
    SearchContract.Presenter presenter;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        bundleService.getAll().putString(ExtraKeys.KEYWORD, presenter.getKeyword());
        super.onSaveInstanceState(outState);
    }
}
