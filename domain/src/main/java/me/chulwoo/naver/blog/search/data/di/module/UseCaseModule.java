package me.chulwoo.naver.blog.search.data.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.chulwoo.naver.blog.search.domain.interactor.search.SearchBlogPosts;
import me.chulwoo.naver.blog.search.domain.repository.BlogRepository;

@Module
public class UseCaseModule {

    @Singleton
    @Provides
    SearchBlogPosts provideSearchBlogPosts(BlogRepository blogRepository) {
        return new SearchBlogPosts(blogRepository);
    }
}
