package me.chulwoo.naver.blog.search.data.blog.converter;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import me.chulwoo.naver.blog.search.data.Converter;
import me.chulwoo.naver.blog.search.data.blog.model.PostDto;
import me.chulwoo.naver.blog.search.data.blog.model.SearchResultDto;
import me.chulwoo.naver.blog.search.domain.entity.PageInfo;
import me.chulwoo.naver.blog.search.domain.entity.Post;

@RequiredArgsConstructor
public class BlogConverterImpl implements BlogConverter {

    private final Converter<PostDto, Post> postConverter;
    private final Converter<SearchResultDto<Post>, PageInfo<Post>> pageInfoConverter;

    @Override
    public PageInfo<Post> convert(SearchResultDto<PostDto> dto) {
        List<Post> posts = new ArrayList<>();
        for (PostDto postDto : dto.getItems()) {
            posts.add(postConverter.convert(postDto));
        }
        SearchResultDto<Post> postSearchResult = new SearchResultDto<>(
                dto.getLastBuildDate(), dto.getTotal(), dto.getStart(), dto.getDisplay(), posts);
        return pageInfoConverter.convert(postSearchResult);
    }
}
