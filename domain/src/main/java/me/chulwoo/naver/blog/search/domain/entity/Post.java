package me.chulwoo.naver.blog.search.domain.entity;

import lombok.Data;

@Data
public class Post {

    String title;
    String summary;

    String postUrl;

    String bloggerName;
    String blogUrl;

    long createdAt;
}
