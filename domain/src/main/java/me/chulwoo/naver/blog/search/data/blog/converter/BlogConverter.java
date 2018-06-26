package me.chulwoo.naver.blog.search.data.blog.converter;

import me.chulwoo.naver.blog.search.data.blog.model.PostDto;
import me.chulwoo.naver.blog.search.data.blog.model.SearchResultDto;
import me.chulwoo.naver.blog.search.domain.entity.PageInfo;
import me.chulwoo.naver.blog.search.domain.entity.Post;

public interface BlogConverter {

    PageInfo<Post> convert(SearchResultDto<PostDto> dto);
}
