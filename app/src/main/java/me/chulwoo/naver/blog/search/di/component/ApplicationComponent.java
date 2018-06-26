package me.chulwoo.naver.blog.search.di.component;

import android.app.Application;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import me.chulwoo.naver.blog.search.SearchApp;
import me.chulwoo.naver.blog.search.di.module.ActivityBindingModule;
import me.chulwoo.naver.blog.search.di.module.ApplicationModule;
import me.chulwoo.naver.blog.search.di.scope.ApplicationScope;
import me.chulwoo.naver.blog.search.domain.di.component.DomainComponent;

@ApplicationScope
@Component(
        dependencies = {
                DomainComponent.class,
        },
        modules = {
                AndroidSupportInjectionModule.class,
                ActivityBindingModule.class,
                ApplicationModule.class
        })
public interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        Builder plus(DomainComponent domainComponent);

        ApplicationComponent build();
    }

    void inject(SearchApp app);
}
