package com.apollo.andorid.apollosearch.view.intro;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.apollo.andorid.apollosearch.view.base.BaseNavigator;
import com.apollo.andorid.apollosearch.view.base.Navigator;
import com.apollo.andorid.apollosearch.view.search.SearchActivity;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public class IntroNavigator {

    private final Navigator navigator;

    public IntroNavigator(@NonNull Activity activity) {
        this.navigator = new BaseNavigator(activity);
    }

    public void openSearch(String keyword) {
        Intent intent = navigator.intentFor(SearchActivity.class);
        intent.putExtra(SearchActivity.EXTRA_KEYWORD, keyword);
        navigator.start(intent);
    }
}
