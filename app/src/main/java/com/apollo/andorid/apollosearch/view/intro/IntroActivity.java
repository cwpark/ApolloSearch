package com.apollo.andorid.apollosearch.view.intro;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.apollo.andorid.apollosearch.view.base.BaseActivity;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public class IntroActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, IntroFragment.newInstance())
                .commit();
    }
}
