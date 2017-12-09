package com.apollo.andorid.apollosearch.view.search;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.apollo.andorid.apollosearch.R;
import com.apollo.andorid.apollosearch.data.Blog;
import com.apollo.andorid.apollosearch.data.SearchResult;
import com.apollo.andorid.apollosearch.data.Sort;
import com.apollo.andorid.apollosearch.data.source.BlogRepository;
import com.apollo.andorid.apollosearch.data.source.remote.exception.NaverApiError;
import com.apollo.andorid.apollosearch.data.source.remote.exception.RetrofitException;
import com.apollo.andorid.apollosearch.view.base.ErrorMessageModel;
import com.apollo.andorid.apollosearch.view.search.adapter.BlogItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public class SearchViewModel implements ISearchViewModel {

    private BlogRepository blogRepository;
    private SearchNavigator navigator;

    private PublishSubject<SearchUiModel> searchUiModelSubject;
    private PublishSubject<LoadMoreUiModel> loadMoreUiModelSubject;

    private BehaviorSubject<String> keywordSubject;
    private BehaviorSubject<Sort> sortSubject;

    private BehaviorSubject<Boolean> searchIndicatorSubject;
    private BehaviorSubject<Boolean> loadMoreIndicatorVisibilitySubject;

    private BehaviorSubject<Integer> keywordClearViewResourceSubject;
    private PublishSubject<ErrorMessageModel> errorMessageSubject;
    private Disposable currentSearchDisposable;
    private Disposable currentLoadMoreDisposable;

    public SearchViewModel(@NonNull BlogRepository blogRepository, @NonNull SearchNavigator navigator) {
        this.blogRepository = blogRepository;
        this.navigator = navigator;

        this.searchUiModelSubject = PublishSubject.create();
        this.loadMoreUiModelSubject = PublishSubject.create();

        this.keywordSubject = BehaviorSubject.createDefault("");
        this.sortSubject = BehaviorSubject.createDefault(Sort.SIMILLAR);

        this.searchIndicatorSubject = BehaviorSubject.createDefault(false);
        this.loadMoreIndicatorVisibilitySubject = BehaviorSubject.createDefault(false);
        this.keywordClearViewResourceSubject = BehaviorSubject.createDefault(R.drawable.ic_close_white_24dp);

        this.errorMessageSubject = PublishSubject.create();
    }

    @Override
    public Observable<SearchUiModel> getSearchUiModel() {
        return searchUiModelSubject;
    }

    @Override
    public Observable<LoadMoreUiModel> getLoadMoreUiModel() {
        return loadMoreUiModelSubject;
    }

    @Override
    public Completable search() {
        if (TextUtils.isEmpty(keywordSubject.getValue())) {
            return Completable.fromAction(() -> errorMessageSubject.onNext(new ErrorMessageModel(R.string.input_keyword)));
        } else {
            return Completable.create(emitter -> {
                if (currentSearchDisposable != null) {
                    currentSearchDisposable.dispose();
                }

                if (currentLoadMoreDisposable != null) {
                    currentLoadMoreDisposable.dispose();
                }

                currentSearchDisposable = blogRepository.search(keywordSubject.getValue(), sortSubject.getValue())
                        .map(SearchResult::getItems)
                        .flatMapObservable(Observable::fromIterable)
                        .map(this::createBlogItem).toList()
                        .map(this::createSearchUiModel)
                        .doOnSubscribe(__ -> searchIndicatorSubject.onNext(true))
                        .doOnSuccess(__ -> searchIndicatorSubject.onNext(false))
                        .doOnError(throwable -> {
                            handleError(throwable);
                            searchIndicatorSubject.onNext(false);
                        })
                        .subscribe(uiModel -> {
                            searchUiModelSubject.onNext(uiModel);
                            if (!emitter.isDisposed()) {
                                emitter.onComplete();
                            }
                        }, throwable -> {
                            if (!emitter.isDisposed()) {
                                emitter.onError(throwable);
                            }
                        });
            });
        }
    }

    @Override
    public Completable loadMore() {
        return Completable.create(emitter -> currentLoadMoreDisposable = getLoadMoreItems()
                .map(this::createLoadMoreUiModel)
                .doOnSubscribe(__ -> loadMoreIndicatorVisibilitySubject.onNext(true))
                .doOnSuccess(__ -> loadMoreIndicatorVisibilitySubject.onNext(false))
                .doOnError(throwable -> {
                    handleError(throwable);
                    loadMoreIndicatorVisibilitySubject.onNext(false);
                })
                .subscribe(items -> {
                    loadMoreUiModelSubject.onNext(items);
                    if (!emitter.isDisposed()) {
                        emitter.onComplete();
                    }
                }, throwable -> {
                    if (!emitter.isDisposed()) {
                        emitter.onError(throwable);
                    }
                }));
    }

    @Override
    public Completable sort(Sort sort) {
        sortSubject.onNext(sort);
        return search();
    }

    private SearchUiModel createSearchUiModel(List<BlogItem> blogItems) {
        return new SearchUiModel(blogItems);
    }

    private Maybe<SearchResult<Blog>> getLoadMoreItems() {
        return blogRepository.loadMore();
    }

    private LoadMoreUiModel createLoadMoreUiModel(SearchResult<Blog> searchResult) {
        List<BlogItem> blogItems = new ArrayList<>();
        for (Blog blog : searchResult.getItems()) {
            blogItems.add(createBlogItem(blog));
        }
        return new LoadMoreUiModel(blogItems, searchResult.hasNext());
    }

    private BlogItem createBlogItem(Blog blog) {
        return new BlogItem(blog,
                () -> navigator.openBlogPost(blog),
                () -> navigator.openBlog(blog));
    }

    @Override
    public Observable<Boolean> getSearchIndicatorVisibility() {
        return searchIndicatorSubject;
    }

    @Override
    public Observable<Boolean> getLoadMoreIndicatorVisibility() {
        return loadMoreIndicatorVisibilitySubject;
    }

    @Override
    public Observable<Integer> getKeywordClearViewResource() {
        return keywordClearViewResourceSubject;
    }

    @Override
    public Observable<Integer> getSortLabelResource() {
        return sortSubject.map(sort -> {
            switch (sort) {
                case DATE:
                    return R.string.sort_date;
                case SIMILLAR:
                default:
                    return R.string.sort_sim;
            }
        });
    }

    @Override
    public void onKeywordChange(CharSequence keyword) {
        keywordSubject.onNext(keyword.toString().trim());
        if (TextUtils.isEmpty(keyword)) {
            keywordClearViewResourceSubject.onNext(0);
        } else {
            keywordClearViewResourceSubject.onNext(R.drawable.ic_close_white_24dp);
        }
    }

    @Override
    public Observable<ErrorMessageModel> getErrorMessage() {
        return errorMessageSubject;
    }

    @Override
    public int getSearchPageSize() {
        return blogRepository.getSearchPageSize();
    }

    private void handleError(Throwable throwable) {
        if (throwable instanceof RetrofitException) {
            RetrofitException retrofitException = (RetrofitException) throwable;
            switch (retrofitException.getKind()) {
                case NETWORK:
                    errorMessageSubject.onNext(new ErrorMessageModel(R.string.network_error));
                    break;
                case HTTP:
                    try {
                        NaverApiError naverApiError = retrofitException.getErrorBodyAs(NaverApiError.class);
                        errorMessageSubject.onNext(new ErrorMessageModel(naverApiError.getErrorMessage()));
                    } catch (Exception e) {
                        errorMessageSubject.onNext(new ErrorMessageModel(R.string.search_error));
                    }
                    break;
                default:
                    errorMessageSubject.onNext(new ErrorMessageModel(R.string.search_error));
            }
        } else {
            errorMessageSubject.onNext(new ErrorMessageModel(R.string.search_error));
        }
    }
}
