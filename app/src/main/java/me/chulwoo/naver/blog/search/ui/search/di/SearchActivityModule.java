package me.chulwoo.naver.blog.search.ui.search.di;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.chulwoo.naver.blog.search.di.module.BaseActivityModule;
import me.chulwoo.naver.blog.search.di.scope.ActivityScope;
import me.chulwoo.naver.blog.search.di.scope.FragmentScope;
import me.chulwoo.naver.blog.search.ui.search.SearchActivity;
import me.chulwoo.naver.blog.search.ui.search.SearchContract;
import me.chulwoo.naver.blog.search.ui.search.SearchFragment;
import me.chulwoo.naver.blog.search.ui.search.SearchPresenter;

@Module
public abstract class SearchActivityModule implements BaseActivityModule<SearchActivity> {

    @ActivityScope
    @Binds
    abstract SearchContract.Presenter bindSearchPresenter(SearchPresenter presenter);

    @FragmentScope
    @ContributesAndroidInjector
    abstract SearchFragment bindSearchFragment();
}
