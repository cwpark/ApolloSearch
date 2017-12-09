package com.apollo.andorid.apollosearch.view.search;

import com.apollo.andorid.apollosearch.view.search.adapter.BlogItem;

import java.util.List;

/**
 * Created by chulwoo on 2017. 12. 10..
 */

public class LoadMoreUiModel {

    private List<BlogItem> blogs;
    private boolean canLoadMore;

    public LoadMoreUiModel(List<BlogItem> blogs, boolean canLoadMore) {
        this.blogs = blogs;
        this.canLoadMore = canLoadMore;
    }

    public List<BlogItem> getBlogs() {
        return blogs;
    }

    public boolean isCanLoadMore() {
        return canLoadMore;
    }
}
