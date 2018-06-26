package me.chulwoo.naver.blog.search.di.module;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.chulwoo.naver.blog.search.AndroidSchedulerProvider;
import me.chulwoo.naver.blog.search.SchedulerProvider;
import me.chulwoo.naver.blog.search.di.qualifier.ApplicationContext;
import me.chulwoo.naver.blog.search.di.scope.ApplicationScope;

@Module
public class ApplicationModule {

    @Provides
    @ApplicationScope
    @ApplicationContext
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @ApplicationScope
    SchedulerProvider provideSchedulerProvider() {
        return new AndroidSchedulerProvider();
    }
}
