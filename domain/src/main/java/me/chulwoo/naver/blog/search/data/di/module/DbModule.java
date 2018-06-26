package me.chulwoo.naver.blog.search.data.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.chulwoo.naver.blog.search.data.blog.db.BlogDb;
import me.chulwoo.naver.blog.search.data.blog.db.BlogDbImpl;

@Module
public class DbModule {

    @Provides
    @Singleton
    BlogDb provideBlogDb() {
        return new BlogDbImpl();
    }
}
