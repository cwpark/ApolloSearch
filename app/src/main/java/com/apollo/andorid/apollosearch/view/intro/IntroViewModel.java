package com.apollo.andorid.apollosearch.view.intro;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.apollo.andorid.apollosearch.R;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public class IntroViewModel implements IIntroViewModel {

    private IntroNavigator navigator;
    private String keyword;
    private BehaviorSubject<Integer> keywordClearViewResourceSubject;
    private PublishSubject<Integer> errorMessageSubject;

    public IntroViewModel(@NonNull IntroNavigator navigator) {
        this.navigator = navigator;
        this.keywordClearViewResourceSubject = BehaviorSubject.createDefault(0);
        this.errorMessageSubject = PublishSubject.create();
    }

    @Override
    public void onKeywordChange(@NonNull CharSequence keyword) {
        this.keyword = keyword.toString().trim();
        this.keyword = keyword.toString().trim();
        if (TextUtils.isEmpty(keyword)) {
            keywordClearViewResourceSubject.onNext(0);
        } else {
            keywordClearViewResourceSubject.onNext(R.drawable.ic_close_white_24dp);
        }
    }

    @Override
    public Observable<Integer> getKeywordClearViewResource() {
        return keywordClearViewResourceSubject;
    }

    @Override
    public Observable<Integer> getErrorMessage() {
        return errorMessageSubject;
    }

    @Override
    public void search() {
        if (TextUtils.isEmpty(keyword)) {
            errorMessageSubject.onNext(R.string.input_keyword);
        } else {
            navigator.openSearch(keyword);
        }
    }
}
