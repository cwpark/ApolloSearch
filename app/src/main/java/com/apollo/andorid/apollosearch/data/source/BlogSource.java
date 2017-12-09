package com.apollo.andorid.apollosearch.data.source;

import com.apollo.andorid.apollosearch.data.Blog;
import com.apollo.andorid.apollosearch.data.SearchResult;
import com.apollo.andorid.apollosearch.data.Sort;

import io.reactivex.Maybe;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public interface BlogSource {

    Maybe<SearchResult<Blog>> search(String keyword, int display, int start, Sort sort);
}
