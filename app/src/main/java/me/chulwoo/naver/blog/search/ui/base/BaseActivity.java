package me.chulwoo.naver.blog.search.ui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import lombok.Getter;
import me.chulwoo.naver.blog.search.BundleService;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public abstract class BaseActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    @Getter
    protected BundleService bundleService;

    @LayoutRes
    protected int getLayoutResource() {
        return 0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        bundleService = new BundleService(savedInstanceState, getIntent().getExtras());
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        int layoutResource = getLayoutResource();
        if (layoutResource != 0) {
            setContentView(layoutResource);
        }
        ButterKnife.bind(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putAll(bundleService.getAll());
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }
}
