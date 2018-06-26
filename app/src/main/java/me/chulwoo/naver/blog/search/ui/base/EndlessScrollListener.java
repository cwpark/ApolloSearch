package me.chulwoo.naver.blog.search.ui.base;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {

    private LinearLayoutManager layoutManager;

    private int pageSize;

    private boolean loading = false;
    private boolean canLoadMore = true;

    public EndlessScrollListener(LinearLayoutManager layoutManager, int pageSize) {
        this.layoutManager = layoutManager;
        this.pageSize = pageSize;
    }

    @Override
    public void onScrolled(final RecyclerView view, int dx, int dy) {
        super.onScrolled(view, dx, dy);

        if (dy < 0) {
            return;
        }

        int totalItemCount = layoutManager.getItemCount();
        if (totalItemCount < pageSize) {
            return;
        }

        int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
        if (canLoadMore && !loading && totalItemCount <= (lastVisibleItem + pageSize)) {
            onLoadMore();
            loading = true;
        }
    }

    public abstract void onLoadMore();

    public void setCanLoadMore(boolean canLoadMore) {
        this.canLoadMore = canLoadMore;
    }

    public void completeLoadMore() {
        loading = false;
    }
}