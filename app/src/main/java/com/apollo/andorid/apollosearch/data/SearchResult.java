package com.apollo.andorid.apollosearch.data;

import com.google.gson.annotations.SerializedName;

import org.threeten.bp.LocalDateTime;

import java.util.List;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public class SearchResult<T> {

    private static final int SEARCH_START_LIMIT = 1000;

    /**
     * 검색 결과를 생성한 시간이다.
     */
    LocalDateTime lastBuildDate;

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
    @SerializedName("items") List<T> items;

    public LocalDateTime getLastBuildDate() {
        return lastBuildDate;
    }

    public int getTotal() {
        return total;
    }

    public int getStart() {
        return start;
    }

    public int getDisplay() {
        return display;
    }

    public List<T> getItems() {
        return items;
    }

    public boolean hasNext() {
        return (start + display - 1) != total && start <= SEARCH_START_LIMIT;
    }
}
