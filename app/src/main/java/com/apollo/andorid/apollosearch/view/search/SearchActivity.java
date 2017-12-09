package com.apollo.andorid.apollosearch.view.search;

import android.os.Bundle;

import com.apollo.andorid.apollosearch.view.base.BaseActivity;

public class SearchActivity extends BaseActivity {

    public static final String EXTRA_KEYWORD = "keyword";

    private String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            keyword = savedInstanceState.getString(EXTRA_KEYWORD);
        } else if (getIntent() != null) {
            keyword = getIntent().getStringExtra(EXTRA_KEYWORD);
        }

        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, SearchFragment.newInstance(keyword))
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_KEYWORD, keyword);
    }
}
