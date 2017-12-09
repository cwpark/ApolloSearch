package com.apollo.andorid.apollosearch.view.intro;

import io.reactivex.Observable;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public interface IIntroViewModel {

    void onKeywordChange(CharSequence keyword);

    void search();

    Observable<Integer> getKeywordClearViewResource();

    Observable<Integer> getErrorMessage();
}
