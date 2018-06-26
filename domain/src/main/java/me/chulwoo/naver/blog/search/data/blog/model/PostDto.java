package me.chulwoo.naver.blog.search.data.blog.model;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

@RequiredArgsConstructor
@Getter
public class PostDto {

    /**
     * 검색 결과 문서의 제목을 나타낸다. 제목에서 검색어와 일치하는 부분은 태그로 감싸져 있다.
     */
    private final String title;

    /**
     * 검색 결과 문서의 하이퍼텍스트 link를 나타낸다.
     */
    private final String link;

    /**
     * 검색 결과 문서의 내용을 요약한 패시지 정보이다. 문서 전체의 내용은 link를 따라가면 읽을 수 있다. 패시지에서 검색어와 일치하는 부분은 태그로 감싸져 있다.
     */
    private final String description;

    /**
     * 검색 결과 블로그 포스트를 작성한 블로거의 이름이다.
     */
    @SerializedName("bloggername")
    private final String bloggerName;

    /**
     * 검색 결과 블로그 포스트를 작성한 블로거의 하이퍼텍스트 link이다.
     */
    @SerializedName("bloggerlink")
    private final String bloggerLink;

    /**
     * 블로그 포스트를 작성한 날짜이다.
     */
    @SerializedName("postdate")
    private final String postDate;
}
