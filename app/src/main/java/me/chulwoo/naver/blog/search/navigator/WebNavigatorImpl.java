package me.chulwoo.naver.blog.search.navigator;

import android.content.Intent;

import javax.inject.Inject;

import me.chulwoo.naver.blog.search.ui.ExtraKeys;
import me.chulwoo.naver.blog.search.ui.base.BaseActivity;
import me.chulwoo.naver.blog.search.ui.webview.WebViewActivity;

public class WebNavigatorImpl extends NavigatorImpl implements WebNavigator {

    @Inject
    public WebNavigatorImpl(BaseActivity activity) {
        super(activity);
    }

    @Override
    public void open(String url) {
        Intent intent = intentFor(WebViewActivity.class);
        intent.putExtra(ExtraKeys.URL, url);
        start(intent);
    }
}
