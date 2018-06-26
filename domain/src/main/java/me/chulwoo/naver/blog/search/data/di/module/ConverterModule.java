package me.chulwoo.naver.blog.search.data.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.chulwoo.naver.blog.search.data.Converter;
import me.chulwoo.naver.blog.search.data.blog.converter.BlogConverter;
import me.chulwoo.naver.blog.search.data.blog.converter.BlogConverterImpl;
import me.chulwoo.naver.blog.search.data.blog.converter.PageInfoConverter;
import me.chulwoo.naver.blog.search.data.blog.converter.PostConverter;
import me.chulwoo.naver.blog.search.data.blog.model.PostDto;
import me.chulwoo.naver.blog.search.data.blog.model.SearchResultDto;
import me.chulwoo.naver.blog.search.domain.entity.PageInfo;
import me.chulwoo.naver.blog.search.domain.entity.Post;

@Module
public class ConverterModule {

    @Provides
    @Singleton
    BlogConverter provideBlogConverter(Converter<PostDto, Post> postConverter,
                                       Converter<SearchResultDto<Post>, PageInfo<Post>> pageInfoConverter) {
        return new BlogConverterImpl(postConverter, pageInfoConverter);
    }

    @Provides
    @Singleton
    Converter<PostDto, Post> providePostConverter() {
        return new PostConverter();
    }

    @Provides
    @Singleton
    Converter<SearchResultDto<Post>, PageInfo<Post>> providePageInfoConverter() {
        return new PageInfoConverter<>();
    }


    public interface Exposes {

        BlogConverter blogConverter();
    }
}
