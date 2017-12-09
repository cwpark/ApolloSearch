package com.apollo.andorid.apollosearch.data;

import com.google.gson.annotations.SerializedName;

import org.threeten.bp.LocalDate;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public class Blog {


    /**
     * 검색 결과 문서의 제목을 나타낸다. 제목에서 검색어와 일치하는 부분은 태그로 감싸져 있다.
     */
    String title;
    /**
     * 검색 결과 문서의 하이퍼텍스트 link를 나타낸다.
     */
    String link;

    /**
     * 검색 결과 문서의 내용을 요약한 패시지 정보이다. 문서 전체의 내용은 link를 따라가면 읽을 수 있다. 패시지에서 검색어와 일치하는 부분은 태그로 감싸져 있다.
     */
    String description;

    /**
     * 검색 결과 블로그 포스트를 작성한 블로거의 이름이다.
     */
    @SerializedName("bloggername")String bloggerName;

    /**
     * 검색 결과 블로그 포스트를 작성한 블로거의 하이퍼텍스트 link이다.
     */
    @SerializedName("bloggerlink") String bloggerLink;

    /**
     * 블로그 포스트를 작성한 날짜이다.
     */
    @SerializedName("postdate") LocalDate postDate;

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getBloggerName() {
        return bloggerName;
    }

    public String getBloggerLink() {
        return bloggerLink;
    }

    public LocalDate getPostDate() {
        return postDate;
    }
}
