package com.apollo.andorid.apollosearch.view.search;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.apollo.andorid.apollosearch.data.Blog;
import com.apollo.andorid.apollosearch.view.base.BaseNavigator;
import com.apollo.andorid.apollosearch.view.base.Navigator;
import com.apollo.andorid.apollosearch.view.webview.WebViewActivity;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public class SearchNavigator {

    private final Navigator navigator;

    public SearchNavigator(@NonNull Activity activity) {
        this.navigator = new BaseNavigator(activity);
    }

    public void openBlogPost(@NonNull Blog blog) {
        Intent intent = navigator.intentFor(WebViewActivity.class);
        intent.putExtra(WebViewActivity.EXTRA_URL, blog.getLink());
        navigator.start(intent);
    }

    public void openBlog(@NonNull Blog blog) {
        Intent intent = navigator.intentFor(WebViewActivity.class);
        intent.putExtra(WebViewActivity.EXTRA_URL, blog.getBloggerLink());
        navigator.start(intent);
    }
}
