package com.apollo.andorid.apollosearch.view.search.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.apollo.andorid.apollosearch.R;
import com.apollo.andorid.apollosearch.view.base.LoadMoreViewHolder;
import com.apollo.andorid.apollosearch.view.search.adapter.holder.BlogViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public class BlogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TYPE_BLOG = 0;
    private final int TYPE_LOAD_MORE = 1;

    private List<BlogItem> blogs = new ArrayList<>();

    private boolean loadMoreIndicatorVisible = false;

    public BlogAdapter() {
    }

    public void setBlogs(List<BlogItem> blogs) {
        this.blogs.clear();
        this.blogs.addAll(blogs);
        notifyDataSetChanged();
    }

    public void addBlogs(List<BlogItem> blogs) {
        int prevSize = this.blogs.size();
        this.blogs.addAll(blogs);
        notifyItemRangeInserted(prevSize, blogs.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_BLOG) {
            return new BlogViewHolder(inflater.inflate(R.layout.item_blog, parent, false));
        } else if (viewType == TYPE_LOAD_MORE) {
            return new LoadMoreViewHolder(inflater.inflate(R.layout.item_load_more, parent, false));
        }

        throw new IllegalArgumentException("invalid view type");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BlogViewHolder) {
            BlogItem blog = blogs.get(position);
            ((BlogViewHolder) holder).bindTo(blog);
        }
    }

    @Override
    public int getItemCount() {
        if (loadMoreIndicatorVisible) {
            return blogs.size() + 1;
        } else {
            return blogs.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position < blogs.size()) ? TYPE_BLOG : TYPE_LOAD_MORE;
    }

    public void setLoadMoreIndicatorVisibility(Boolean visible) {
        this.loadMoreIndicatorVisible = visible;
        if (visible) {
            notifyItemInserted(blogs.size());
        } else {
            notifyItemRemoved(blogs.size());
        }
    }
}
