package com.apollo.andorid.apollosearch.data.source;

import com.apollo.andorid.apollosearch.data.Blog;
import com.apollo.andorid.apollosearch.data.SearchResult;
import com.apollo.andorid.apollosearch.data.Sort;
import com.apollo.andorid.apollosearch.data.source.remote.BlogRemoteSource;

import io.reactivex.Maybe;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public class BlogRepository {

    private static final int DEFAULT_PAGE_SIZE = 10;

    private int searchPageSize = DEFAULT_PAGE_SIZE;
    private SearchOption searchOption;
    private boolean searching;

    public static BlogRepository getInstance() {
        return LazyHolder.INSTANCE;
    }

    private BlogRemoteSource blogRemoteSource;

    private BlogRepository() {
        this.blogRemoteSource = new BlogRemoteSource();
    }

    /**
     * 다음 검색부터 적용 됨.
     *
     * @param size
     */
    public void setSearchPageSize(int size) {
        this.searchPageSize = size;
    }

    public int getSearchPageSize() {
        return searchPageSize;
    }

    public Maybe<SearchResult<Blog>> search(String keyword, Sort sort) {
        searchOption = new SearchOption(keyword, searchPageSize, sort);
        return blogRemoteSource.search(searchOption.getKeyword(), searchOption.getDisplay(),
                searchOption.getStart(), searchOption.getSort())
                .doOnSubscribe(__ -> searching = true)
                .doOnError(__ -> searching = false)
                .doOnSuccess(__ -> {
                    searching = false;
                    searchOption.nextPage();
                });
    }

    public Maybe<SearchResult<Blog>> loadMore() {
        if (searching || searchOption == null) {
            return Maybe.empty();
        }

        return blogRemoteSource.search(searchOption.getKeyword(), searchOption.getDisplay(),
                searchOption.getStart(), searchOption.getSort())
                .doOnSuccess(__ -> searchOption.nextPage());
    }

    private static class LazyHolder {
        private static final BlogRepository INSTANCE = new BlogRepository();
    }

    private class SearchOption {
        private int page = 1;

        private String keyword;
        private int display;
        private Sort sort;
        private int start;

        public SearchOption(String keyword, int display, Sort sort) {
            this.keyword = keyword;
            this.display = display;
            this.start = 1;
            this.sort = sort;
        }

        public void nextPage() {
            page++;
            start = ((page - 1) * display) + 1;
        }

        public String getKeyword() {
            return keyword;
        }

        public int getDisplay() {
            return display;
        }

        public int getStart() {
            return start;
        }

        public Sort getSort() {
            return sort;
        }
    }
}
