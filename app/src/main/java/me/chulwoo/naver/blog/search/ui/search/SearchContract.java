package me.chulwoo.naver.blog.search.ui.search;

import me.chulwoo.naver.blog.search.domain.entity.Sort;
import me.chulwoo.naver.blog.search.ui.base.mvp.MvpPresenter;
import me.chulwoo.naver.blog.search.ui.base.mvp.MvpView;
import me.chulwoo.naver.blog.search.ui.search.adapter.PostAdapterContract;

public interface SearchContract {

    interface View extends MvpView {

        void showSort(Sort currentSort);

        void showKeyword(String keyword);

        void showPostsView();

        void showEmptyView();

        void setLoadMoreEnable(boolean enable);

        void showSearchIndicator();

        void hideSearchIndicator();

        void showLoadMoreIndicator();

        void hideLoadMoreIndicator();

        void hideKeyboard();

        void showKeywordClearView();

        void hideKeywordClearView();

        void showError(Throwable e);
    }

    interface Presenter extends MvpPresenter<View> {

        void init(PostAdapterContract.View adapterView);

        void loadMore();

        void onChangeSort(Sort sort);

        String getKeyword();

        void search();

        void onKeywordChange(CharSequence keyword);

        void onClearClick();
    }
}
