package me.chulwoo.naver.blog.search.data.blog.service;

import io.reactivex.Single;
import me.chulwoo.naver.blog.search.data.blog.model.PostDto;
import me.chulwoo.naver.blog.search.data.blog.model.SearchResultDto;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public interface BlogService {

    String BASE_URL = "/v1/search";

    /**
     * 네이버 블로그 검색 결과를 출력해주는 REST API입니다.
     * <p>
     * query	검색을 원하는 문자열로서	UTF-8로 인코딩한다.
     * display	10(기본값),100(최대)	검색 결과 출력 건수 지정
     * start	1(기본값), 1000(최대)	검색 시작 위치로 최대 1000까지 가능
     * sort		sim(기본값), date		정렬 옵션: sim (유사도순), date (날짜순)
     *
     * @return 블로그 검색 결과
     */
    @GET(BASE_URL + "/blog.json")
    Single<SearchResultDto<PostDto>> search(@Query("query") String query, @Query("display") int display,
                                            @Query("start") int start, @Query("sort") String sort);
}
