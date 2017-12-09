package com.apollo.andorid.apollosearch.view.search;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.apollo.andorid.apollosearch.R;
import com.apollo.andorid.apollosearch.data.Sort;
import com.apollo.andorid.apollosearch.data.source.BlogRepository;
import com.apollo.andorid.apollosearch.util.DLog;
import com.apollo.andorid.apollosearch.view.base.BaseFragment;
import com.apollo.andorid.apollosearch.view.base.EndlessScrollListener;
import com.apollo.andorid.apollosearch.view.search.adapter.BlogAdapter;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;
import butterknife.OnTouch;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public class SearchFragment extends BaseFragment {

    private static final String ARG_KEYWORD = "keyword";

    @BindView(R.id.sort) TextView sortView;
    @BindView(R.id.keyword) TextView keywordView;
    @BindView(R.id.blogs) RecyclerView blogsView;
    @BindView(R.id.empty) TextView emptyView;
    @BindView(R.id.searchIndicator) View searchIndicator;

    private EndlessScrollListener endlessScrollListener;
    private BlogAdapter blogAdapter;
    private ISearchViewModel viewModel;
    private boolean isFirstResume = true;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        blogAdapter = new BlogAdapter();
        blogsView.setHasFixedSize(true);
        blogsView.setAdapter(blogAdapter);
        blogsView.setLayoutManager(layoutManager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new SearchViewModel(BlogRepository.getInstance(), new SearchNavigator(getActivity()));
        endlessScrollListener = new SearchLoadMoreListener(
                (LinearLayoutManager) blogsView.getLayoutManager(),
                viewModel.getSearchPageSize());
        blogsView.addOnScrollListener(endlessScrollListener);
        String keyword = "";
        if (savedInstanceState != null) {
            keyword = savedInstanceState.getString(ARG_KEYWORD);
        } else if (getArguments() != null) {
            keyword = getArguments().getString(ARG_KEYWORD);
        }
        keywordView.setText(keyword);
    }

    @Override
    public void onResume() {
        super.onResume();

        bind(viewModel.getSearchUiModel()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateUi, this::handleError));

        bind(viewModel.getLoadMoreUiModel()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateUi, this::handleError));

        bind(viewModel.getErrorMessage()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .map(errorMessageModel -> errorMessageModel.getErrorMessage(getActivity()))
                .subscribe(this::showToast, this::handleError));

        bind(viewModel.getSearchIndicatorVisibility()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setSearchIndicatorVisibility, this::handleError));

        bind(viewModel.getLoadMoreIndicatorVisibility()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setLoadMoreIndicatorVisibility, this::handleError));

        bind(viewModel.getKeywordClearViewResource()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showKeywordClearIcon, this::handleError));

        bind(viewModel.getSortLabelResource()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showSort, this::handleError));

        if (isFirstResume) {
            isFirstResume = false;
            search();
        }
    }

    private void setLoadMoreIndicatorVisibility(boolean visible) {
        blogAdapter.setLoadMoreIndicatorVisibility(visible);
    }

    private void handleError(Throwable throwable) {
        // do nothing yet
        // upexcepted error -> report crash analytics systems
        DLog.e(throwable);
    }

    private void showSort(@StringRes int resourceId) {
        sortView.setText(resourceId);
    }

    private void updateUi(SearchUiModel model) {
        int blogsVisible = model.isBlogsVisible() ? View.VISIBLE : View.GONE;
        blogsView.setVisibility(blogsVisible);

        int emptyViewVisible = model.isEmptyResultVisible() ? View.VISIBLE : View.GONE;
        emptyView.setVisibility(emptyViewVisible);

        if (model.isBlogsVisible()) {
            blogAdapter.setBlogs(model.getBlogs());
        }
    }

    private void updateUi(LoadMoreUiModel model) {
        blogAdapter.addBlogs(model.getBlogs());
        endlessScrollListener.setCanLoadMore(model.isCanLoadMore());
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private void setSearchIndicatorVisibility(boolean visible) {
        if (visible) {
            searchIndicator.setVisibility(View.VISIBLE);
            blogsView.setVisibility(View.GONE);
        } else {
            searchIndicator.setVisibility(View.GONE);
            blogsView.setVisibility(View.VISIBLE);
        }
    }

    private void showKeywordClearIcon(@DrawableRes int drawableId) {
        keywordView.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawableId, 0);
    }

    @OnClick(R.id.sort)
    public void onSortClick() {
        PopupMenu popup = new PopupMenu(getContext(), sortView);
        popup.getMenuInflater().inflate(R.menu.sort, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.sortSim:
                    sort(Sort.SIMILLAR);
                    break;
                case R.id.sortDate:
                    sort(Sort.DATE);
                    break;
            }
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
                onClearClick();
                return true;
            }
        }
        return false;
    }

    private void onClearClick() {
        keywordView.setText("");
    }

    public void search() {
        hideKeyboard();
        bind(viewModel.search()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                }, this::handleError));
    }

    public void sort(Sort sort) {
        bind(viewModel.sort(sort)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                }, this::handleError));
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(
                Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            View view = getActivity().getCurrentFocus();
            if (view != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @OnTextChanged(R.id.keyword)
    void onTextChanged(CharSequence charSequence) {
        viewModel.onKeywordChange(charSequence);
    }

    @OnEditorAction(R.id.keyword)
    boolean onEditorAction(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            search();
        }

        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putCharSequence(ARG_KEYWORD, keywordView.getText());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_search;
    }

    public static SearchFragment newInstance(String keyword) {

        Bundle args = new Bundle();
        args.putString(ARG_KEYWORD, keyword);

        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private class SearchLoadMoreListener extends EndlessScrollListener {

        SearchLoadMoreListener(LinearLayoutManager layoutManager, int pageSize) {
            super(layoutManager, pageSize);
        }

        @Override
        public void onLoadMore() {
            bind(viewModel.loadMore()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::completeLoadMore, SearchFragment.this::handleError));
        }
    }
}
