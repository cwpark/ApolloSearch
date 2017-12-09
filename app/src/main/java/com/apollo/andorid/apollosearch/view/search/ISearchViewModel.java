package com.apollo.andorid.apollosearch.view.search;

import com.apollo.andorid.apollosearch.data.Sort;
import com.apollo.andorid.apollosearch.view.base.ErrorMessageModel;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public interface ISearchViewModel {

    Completable search();

    Completable loadMore();

    Completable sort(Sort sort);

    Observable<SearchUiModel> getSearchUiModel();

    Observable<LoadMoreUiModel> getLoadMoreUiModel();

    Observable<Boolean> getSearchIndicatorVisibility();

    Observable<Boolean> getLoadMoreIndicatorVisibility();

    Observable<Integer> getSortLabelResource();

    Observable<Integer> getKeywordClearViewResource();

    Observable<ErrorMessageModel> getErrorMessage();

    void onKeywordChange(CharSequence keyword);

    int getSearchPageSize();
}
