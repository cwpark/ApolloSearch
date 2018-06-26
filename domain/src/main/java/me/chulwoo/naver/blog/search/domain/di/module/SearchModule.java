package me.chulwoo.naver.blog.search.domain.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.chulwoo.naver.blog.search.domain.interactor.search.SearchBlogPosts;
import me.chulwoo.naver.blog.search.domain.repository.BlogRepository;

@Module
public class SearchModule {

    @Singleton
    @Provides
    SearchBlogPosts searchBlogPosts(BlogRepository blogRepository) {
        return new SearchBlogPosts(blogRepository);
    }

    public interface Exposes {

        SearchBlogPosts searchBlogPosts();
    }
}
