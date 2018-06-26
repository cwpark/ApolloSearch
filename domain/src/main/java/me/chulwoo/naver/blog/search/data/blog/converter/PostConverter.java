package me.chulwoo.naver.blog.search.data.blog.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import me.chulwoo.naver.blog.search.data.Converter;
import me.chulwoo.naver.blog.search.data.blog.model.PostDto;
import me.chulwoo.naver.blog.search.domain.entity.Post;

public class PostConverter implements Converter<PostDto, Post> {

    @Override
    public Post convert(PostDto dto) {
        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setSummary(dto.getDescription());
        post.setBlogUrl(dto.getBloggerLink());
        post.setBloggerName(dto.getBloggerName());
        post.setPostUrl(dto.getLink());
        try {
            String strCreatedAt = dto.getPostDate();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
            post.setCreatedAt(format.parse(strCreatedAt).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return post;
    }
}
