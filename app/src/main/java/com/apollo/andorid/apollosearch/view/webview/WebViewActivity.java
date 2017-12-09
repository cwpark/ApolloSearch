package com.apollo.andorid.apollosearch.view.webview;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.apollo.andorid.apollosearch.view.base.BaseActivity;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public class WebViewActivity extends BaseActivity {

    public static final String EXTRA_URL = "url";

    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            url = savedInstanceState.getString(EXTRA_URL);
        } else if (getIntent() != null) {
            url = getIntent().getStringExtra(EXTRA_URL);
        }

        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, WebViewFragment.newInstance(url))
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(EXTRA_URL, url);
        super.onSaveInstanceState(outState);
    }
}
