package me.chulwoo.naver.blog.search.ui.search.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.chulwoo.naver.blog.search.R;
import me.chulwoo.naver.blog.search.domain.entity.Post;
import me.chulwoo.naver.blog.search.ui.base.LoadMoreViewHolder;
import me.chulwoo.naver.blog.search.ui.search.adapter.holder.PostViewHolder;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements PostAdapterContract.View {

    private final int TYPE_BLOG = 0;
    private final int TYPE_LOAD_MORE = 1;

    private List<Post> posts;

    private boolean loadMoreIndicatorVisible = false;
    private PostAdapterContract.Presenter presenter;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_BLOG) {
            View view = inflater.inflate(R.layout.item_post, parent, false);
            return new PostViewHolder(view, presenter);
        } else if (viewType == TYPE_LOAD_MORE) {
            View view = inflater.inflate(R.layout.item_load_more, parent, false);
            return new LoadMoreViewHolder(view);
        }

        throw new IllegalArgumentException("invalid view type");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PostViewHolder) {
            Post post = posts.get(position);
            ((PostViewHolder) holder).bindTo(post, position);
        }
    }

    @Override
    public int getItemCount() {
        if (loadMoreIndicatorVisible) {
            return posts.size() + 1;
        } else {
            return posts.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position < posts.size()) ? TYPE_BLOG : TYPE_LOAD_MORE;
    }

    public void setLoadMoreIndicatorVisibility(Boolean visible) {
        this.loadMoreIndicatorVisible = visible;
        if (visible) {
            notifyItemInserted(posts.size());
        } else {
            notifyItemRemoved(posts.size());
        }
    }

    @Override
    public void bind(PostAdapterContract.Presenter presenter, List<Post> posts) {
        this.presenter = presenter;
        this.posts = posts;
    }
}
