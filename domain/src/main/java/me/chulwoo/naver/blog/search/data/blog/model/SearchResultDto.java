package me.chulwoo.naver.blog.search.data.blog.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by chulwoo on 2017. 12. 9..
 */
@AllArgsConstructor
@Getter
public class SearchResultDto<T> {

    /**
     * 검색 결과를 생성한 시간이다.
     */
    String lastBuildDate;

    /**
     * 검색 결과 문서의 총 개수를 의미한다.
     */
    int total;

    /**
     * 검색 결과 문서 중, 문서의 시작점을 의미한다.
     */
    int start;

    /**
     * 검색된 검색 결과의 개수이다.
     */
    int display;

    /**
     * 개별 검색 결과.
     */
    @SerializedName("items")
    List<T> items;
}
