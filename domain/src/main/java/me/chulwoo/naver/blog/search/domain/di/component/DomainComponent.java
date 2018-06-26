package me.chulwoo.naver.blog.search.domain.di.component;

import javax.inject.Singleton;

import dagger.Component;
import me.chulwoo.naver.blog.search.data.di.module.ConverterModule;
import me.chulwoo.naver.blog.search.data.di.module.DbModule;
import me.chulwoo.naver.blog.search.data.di.module.RepositoryModule;
import me.chulwoo.naver.blog.search.data.di.module.ServiceModule;
import me.chulwoo.naver.blog.search.domain.di.module.SearchModule;
import me.chulwoo.naver.blog.search.domain.di.module.UseCaseExposes;

@Singleton
@Component(modules = {
        ConverterModule.class,
        DbModule.class,
        RepositoryModule.class,
        ServiceModule.class,
        SearchModule.class,
})
public interface DomainComponent extends UseCaseExposes {

    @Component.Builder
    interface Builder {
        DomainComponent build();
    }
}
