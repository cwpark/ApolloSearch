package me.chulwoo.naver.blog.search.ui.intro.di;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.chulwoo.naver.blog.search.di.module.BaseActivityModule;
import me.chulwoo.naver.blog.search.di.scope.ActivityScope;
import me.chulwoo.naver.blog.search.di.scope.FragmentScope;
import me.chulwoo.naver.blog.search.ui.intro.IntroActivity;
import me.chulwoo.naver.blog.search.ui.intro.IntroContract;
import me.chulwoo.naver.blog.search.ui.intro.IntroFragment;
import me.chulwoo.naver.blog.search.ui.intro.IntroPresenter;

@Module
public abstract class IntroActivityModule implements BaseActivityModule<IntroActivity> {

    @ActivityScope
    @Binds
    abstract IntroContract.Presenter bindIntroPresenter(IntroPresenter presenter);

    @FragmentScope
    @ContributesAndroidInjector
    abstract IntroFragment bindIntroFragment();
}
