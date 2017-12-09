package com.apollo.andorid.apollosearch.view.search;

import com.apollo.andorid.apollosearch.view.search.adapter.BlogItem;

import java.util.List;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public class SearchUiModel {

    private List<BlogItem> blogs;
    private boolean blogsVisible;
    private boolean emptyResultVisible;

    public SearchUiModel(List<BlogItem> blogs) {
        this.blogs = blogs;
        this.blogsVisible = !blogs.isEmpty();
        this.emptyResultVisible = blogs.isEmpty();
    }

    public List<BlogItem> getBlogs() {
        return blogs;
    }

    public boolean isBlogsVisible() {
        return blogsVisible;
    }

    public boolean isEmptyResultVisible() {
        return emptyResultVisible;
    }
}
