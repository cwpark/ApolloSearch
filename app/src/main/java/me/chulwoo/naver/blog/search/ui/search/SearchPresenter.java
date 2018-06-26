package me.chulwoo.naver.blog.search.ui.search;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import me.chulwoo.naver.blog.search.SchedulerProvider;
import me.chulwoo.naver.blog.search.di.qualifier.extra.Keyword;
import me.chulwoo.naver.blog.search.domain.entity.PageInfo;
import me.chulwoo.naver.blog.search.domain.entity.Post;
import me.chulwoo.naver.blog.search.domain.entity.Sort;
import me.chulwoo.naver.blog.search.domain.interactor.search.SearchBlogPosts;
import me.chulwoo.naver.blog.search.navigator.WebNavigator;
import me.chulwoo.naver.blog.search.ui.base.mvp.BasePresenter;
import me.chulwoo.naver.blog.search.ui.exception.EmptyKeywordException;
import me.chulwoo.naver.blog.search.ui.search.adapter.PostAdapterContract;

public class SearchPresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter,
        PostAdapterContract.Presenter {

    private PostAdapterContract.View adapterView;

    private Sort currentSort = Sort.SIMILLAR;
    private Sort previousSort = currentSort;

    private SearchBlogPosts searchBlogPosts;
    private SchedulerProvider schedulers;
    private WebNavigator navigator;
    private String currentKeyword;

    private Disposable currentSearchTask;
    private Disposable currentLoadMoreTask;
    private int currentPage = 1;

    private final List<Post> posts;

    @Inject
    public SearchPresenter(SearchBlogPosts searchBlogPosts, SchedulerProvider schedulers,
                           WebNavigator webNavigator, @Keyword String keyword) {
        this.searchBlogPosts = searchBlogPosts;
        this.schedulers = schedulers;
        this.navigator = webNavigator;
        this.currentKeyword = keyword;
        this.posts = new ArrayList<>();
    }

    @Override
    public void init(PostAdapterContract.View adapterView) {
        this.adapterView = adapterView; // i don't know inject SearchPresenter instance each Contract.Presenter in ActivityScope...
        adapterView.bind(this, posts);
        view.showSort(currentSort);
        view.showKeyword(currentKeyword);
        search();
    }

    @Override
    public void onPostClick(int position) {
        Post post = posts.get(position);
        navigator.open(post.getPostUrl());
    }

    @Override
    public void onBloggerNameClick(int position) {
        Post post = posts.get(position);
        navigator.open(post.getBlogUrl());
    }

    @Override
    public void search() {
        currentPage = 1;
        if (TextUtils.isEmpty(currentKeyword)) {
            view.showError(new EmptyKeywordException());
        } else {
            cancelSearch();
            cancelLoadMore();
            currentSearchTask =
                    searchBlogPosts.execute(new SearchBlogPosts.Params(currentKeyword, currentSort, currentPage))
                            .subscribeOn(schedulers.io())
                            .observeOn(schedulers.ui())
                            .doOnSubscribe(__ -> {
                                view.hideKeyboard();
                                view.showSearchIndicator();
                            })
                            .doFinally(view::hideSearchIndicator)
                            .subscribe(this::onSuccessSearchPosts, view::showError);
            compositeDisposable.add(currentSearchTask);
        }
    }

    private void onSuccessSearchPosts(PageInfo<Post> pageInfo) {
        posts.clear();
        posts.addAll(pageInfo.getItems());
        adapterView.notifyDataSetChanged();

        if (pageInfo.isEmpty()) {
            view.showEmptyView();
        } else {
            view.showPostsView();
        }
    }

    @Override
    public void loadMore() {
        if (isSearching() && isLoadingMore()) {
            return;
        }
        currentLoadMoreTask =
                searchBlogPosts.execute(new SearchBlogPosts.Params(currentKeyword, currentSort, currentPage + 1))
                        .subscribeOn(schedulers.io())
                        .observeOn(schedulers.ui())
                        .doOnSubscribe(__ -> view.showLoadMoreIndicator())
                        .doOnSuccess(__ -> currentPage++)
                        .doFinally(view::hideLoadMoreIndicator)
                        .subscribe(this::onSuccessLoadMorePosts, view::showError);
        compositeDisposable.add(currentLoadMoreTask);
    }

    private boolean isSearching() {
        return currentSearchTask != null && !currentSearchTask.isDisposed();
    }

    private boolean isLoadingMore() {
        return currentLoadMoreTask != null && !currentLoadMoreTask.isDisposed();
    }

    private void onSuccessLoadMorePosts(PageInfo<Post> pageInfo) {
        int prevSize = posts.size();
        posts.addAll(pageInfo.getItems());
        adapterView.notifyItemRangeInserted(prevSize, pageInfo.getItems().size());
        view.setLoadMoreEnable(pageInfo.hasNext());
    }

    @Override
    public void onChangeSort(Sort sort) {
        previousSort = currentSort;
        currentSort = sort;
        search();
    }

    @Override
    public String getKeyword() {
        return currentKeyword;
    }

    @Override
    public void onKeywordChange(CharSequence keyword) {
        currentKeyword = keyword.toString().trim();
        if (TextUtils.isEmpty(currentKeyword)) {
            view.hideKeywordClearView();
        } else {
            view.showKeywordClearView();
        }
    }

    @Override
    public void onClearClick() {
        onKeywordChange("");
        view.showKeyword(currentKeyword);
    }

    private void cancelSearch() {
        if (isSearching()) {
            currentSearchTask.dispose();
            currentSort = previousSort;
        }
    }

    private void cancelLoadMore() {
        if (isLoadingMore()) {
            currentLoadMoreTask.dispose();
            currentPage--;
        }
    }
}
