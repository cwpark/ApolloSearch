package me.chulwoo.naver.blog.search.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.chulwoo.naver.blog.search.di.scope.ActivityScope;
import me.chulwoo.naver.blog.search.ui.intro.IntroActivity;
import me.chulwoo.naver.blog.search.ui.intro.di.IntroActivityModule;
import me.chulwoo.naver.blog.search.ui.search.SearchActivity;
import me.chulwoo.naver.blog.search.ui.search.di.SearchActivityModule;
import me.chulwoo.naver.blog.search.ui.webview.WebViewActivity;
import me.chulwoo.naver.blog.search.ui.webview.di.WebViewActivityModule;

@Module
public interface ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = {IntroActivityModule.class, BundleModule.class})
    IntroActivity bindIntroActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = {SearchActivityModule.class, BundleModule.class})
    SearchActivity bindSearchActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = {WebViewActivityModule.class, BundleModule.class})
    WebViewActivity bindWebViewActivity();
}
