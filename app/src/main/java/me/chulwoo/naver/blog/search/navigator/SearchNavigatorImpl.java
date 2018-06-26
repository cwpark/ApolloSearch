package me.chulwoo.naver.blog.search.navigator;

import android.content.Intent;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import me.chulwoo.naver.blog.search.ui.ExtraKeys;
import me.chulwoo.naver.blog.search.ui.base.BaseActivity;
import me.chulwoo.naver.blog.search.ui.search.SearchActivity;

public class SearchNavigatorImpl extends NavigatorImpl implements SearchNavigator {

    @Inject
    public SearchNavigatorImpl(@NonNull BaseActivity activity) {
        super(activity);
    }

    public void search(String keyword) {
        Intent intent = intentFor(SearchActivity.class);
        intent.putExtra(ExtraKeys.KEYWORD, keyword);
        start(intent);
    }
}
