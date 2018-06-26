package me.chulwoo.naver.blog.search.data.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.chulwoo.naver.blog.search.data.blog.BlogRepositoryImpl;
import me.chulwoo.naver.blog.search.data.blog.converter.BlogConverter;
import me.chulwoo.naver.blog.search.data.blog.db.BlogDb;
import me.chulwoo.naver.blog.search.data.blog.service.BlogService;
import me.chulwoo.naver.blog.search.domain.repository.BlogRepository;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    BlogRepository provideBlogRepository(final BlogDb db,
                                         final BlogService service,
                                         final BlogConverter converter) {
        return new BlogRepositoryImpl(db, service, converter);
    }
}
