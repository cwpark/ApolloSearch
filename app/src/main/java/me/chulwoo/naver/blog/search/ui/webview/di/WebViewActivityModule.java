package me.chulwoo.naver.blog.search.ui.webview.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.chulwoo.naver.blog.search.di.module.BaseActivityModule;
import me.chulwoo.naver.blog.search.di.scope.FragmentScope;
import me.chulwoo.naver.blog.search.ui.webview.WebViewActivity;
import me.chulwoo.naver.blog.search.ui.webview.WebViewFragment;

@Module
public abstract class WebViewActivityModule implements BaseActivityModule<WebViewActivity> {

    @FragmentScope
    @ContributesAndroidInjector
    abstract WebViewFragment bindWebViewFragment();
}
