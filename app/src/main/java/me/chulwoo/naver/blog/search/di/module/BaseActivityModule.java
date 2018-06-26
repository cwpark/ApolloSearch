package me.chulwoo.naver.blog.search.di.module;


import dagger.Binds;
import dagger.Module;
import me.chulwoo.naver.blog.search.navigator.SearchNavigator;
import me.chulwoo.naver.blog.search.navigator.SearchNavigatorImpl;
import me.chulwoo.naver.blog.search.navigator.WebNavigator;
import me.chulwoo.naver.blog.search.navigator.WebNavigatorImpl;
import me.chulwoo.naver.blog.search.ui.base.BaseActivity;

@Module
public interface BaseActivityModule<A extends BaseActivity> {

    @Binds
    BaseActivity bindActivity(A activity);

    @Binds
    SearchNavigator bindSearchNavigator(SearchNavigatorImpl navigator);

    @Binds
    WebNavigator bindWebNavigator(WebNavigatorImpl navigator);
}
