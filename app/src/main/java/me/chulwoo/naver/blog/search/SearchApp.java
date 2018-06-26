package me.chulwoo.naver.blog.search;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.jakewharton.threetenabp.AndroidThreeTen;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;
import me.chulwoo.naver.blog.search.di.component.ApplicationComponent;
import me.chulwoo.naver.blog.search.di.component.DaggerApplicationComponent;
import me.chulwoo.naver.blog.search.domain.di.component.DaggerDomainComponent;
import me.chulwoo.naver.blog.search.domain.di.component.DomainComponent;

public class SearchApp extends Application implements HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityInjector;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        DomainComponent dataComponent = DaggerDomainComponent.builder().build();
        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder()
                .application(this)
                .plus(dataComponent)
                .build();
        applicationComponent.inject(this);
        AndroidThreeTen.init(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityInjector;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }
}
