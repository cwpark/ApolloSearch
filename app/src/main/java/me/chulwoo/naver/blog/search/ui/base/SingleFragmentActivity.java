package me.chulwoo.naver.blog.search.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

public abstract class SingleFragmentActivity<F extends BaseFragment> extends BaseActivity {

    @Inject
    F injectedFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //noinspection unchecked
        F fragment = (F) getSupportFragmentManager().findFragmentById(android.R.id.content);
        if (fragment == null) {
            fragment = injectedFragment;
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, fragment)
                    .commit();
        }
    }
}
