package me.chulwoo.naver.blog.search.ui.search;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;
import butterknife.OnTouch;
import me.chulwoo.naver.blog.search.R;
import me.chulwoo.naver.blog.search.domain.entity.Sort;
import me.chulwoo.naver.blog.search.ui.base.EndlessScrollListener;
import me.chulwoo.naver.blog.search.ui.base.mvp.MvpFragment;
import me.chulwoo.naver.blog.search.ui.exception.EmptyKeywordException;
import me.chulwoo.naver.blog.search.ui.search.adapter.PostAdapter;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public class SearchFragment extends MvpFragment<SearchContract.Presenter> implements SearchContract.View {

    @BindView(R.id.sort)
    TextView sortView;
    @BindView(R.id.keyword)
    TextView keywordView;
    @BindView(R.id.blogs)
    RecyclerView blogsView;
    @BindView(R.id.empty)
    TextView emptyView;
    @BindView(R.id.searchIndicator)
    View searchIndicator;

    private EndlessScrollListener endlessScrollListener;
    private PostAdapter postAdapter;

    @Inject
    public SearchFragment() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        postAdapter = new PostAdapter();
        blogsView.setHasFixedSize(true);
        blogsView.setAdapter(postAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        blogsView.setLayoutManager(layoutManager);
        endlessScrollListener = new SearchLoadMoreListener(layoutManager, 20);
        blogsView.addOnScrollListener(endlessScrollListener);

        presenter.init(postAdapter);
    }

    @Override
    public void showSort(Sort sort) {
        switch (sort) {
            case SIMILLAR:
                sortView.setText(R.string.sort_sim);
                break;
            case DATE:
                sortView.setText(R.string.sort_date);
                break;
        }
    }

    @Override
    public void showKeyword(String keyword) {
        keywordView.setText(keyword);
    }

    @Override
    public void showPostsView() {
        blogsView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
    }

    public void showEmptyView() {
        blogsView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setLoadMoreEnable(boolean enable) {
        endlessScrollListener.setCanLoadMore(enable);
    }

    @Override
    public void showSearchIndicator() {
        searchIndicator.setVisibility(View.VISIBLE);
        blogsView.setVisibility(View.GONE);
    }

    @Override
    public void hideSearchIndicator() {
        searchIndicator.setVisibility(View.GONE);
        blogsView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoadMoreIndicator() {
        postAdapter.setLoadMoreIndicatorVisibility(true);
    }

    @Override
    public void hideLoadMoreIndicator() {
        endlessScrollListener.completeLoadMore();
        postAdapter.setLoadMoreIndicatorVisibility(false);
    }

    @Override
    public void showKeywordClearView() {
        keywordView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_close_white_24dp, 0);
    }

    @Override
    public void hideKeywordClearView() {
        keywordView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }

    @Override
    public void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(
                Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            View view = getActivity().getCurrentFocus();
            if (view != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Override
    public void showError(Throwable e) {
        if (e instanceof IOException) {
            showToast(R.string.network_error);
        } else if (e instanceof EmptyKeywordException) {
            showToast(R.string.input_keyword);
        } else {
            showToast(R.string.search_error);
        }
    }

    public void showToast(int resId) {
        Toast.makeText(getActivity(), resId, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.sort)
    public void onSortClick() {
        PopupMenu popup = new PopupMenu(getContext(), sortView);
        popup.getMenuInflater().inflate(R.menu.sort, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            Sort sort;
            switch (item.getItemId()) {
                case R.id.sortSim:
                    sort = Sort.SIMILLAR;
                    break;
                case R.id.sortDate:
                    sort = Sort.DATE;
                    break;
                default:
                    throw new IllegalArgumentException();
            }
            presenter.onChangeSort(sort);
            return true;
        });

        popup.show();
    }

    @OnTouch(R.id.keyword)
    public boolean onKeywordTouch(MotionEvent event) {
        final int DRAWABLE_RIGHT = 2;

        if (event.getAction() == MotionEvent.ACTION_UP) {
            Drawable rightDrawable = keywordView.getCompoundDrawables()[DRAWABLE_RIGHT];
            if (rightDrawable != null && event.getRawX() >= (keywordView.getRight() - rightDrawable.getBounds().width())) {
                presenter.onClearClick();
                return true;
            }
        }
        return false;
    }

    @OnTextChanged(R.id.keyword)
    void onTextChanged(CharSequence charSequence) {
        presenter.onKeywordChange(charSequence.toString().trim());
    }

    @OnEditorAction(R.id.keyword)
    boolean onEditorAction(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            presenter.search();
        }

        return false;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_search;
    }

    private class SearchLoadMoreListener extends EndlessScrollListener {

        SearchLoadMoreListener(LinearLayoutManager layoutManager, int pageSize) {
            super(layoutManager, pageSize);
        }

        @Override
        public void onLoadMore() {
            presenter.loadMore();
        }
    }
}
