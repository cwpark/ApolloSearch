package com.apollo.andorid.apollosearch.view.intro;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import com.apollo.andorid.apollosearch.R;
import com.apollo.andorid.apollosearch.util.DLog;
import com.apollo.andorid.apollosearch.view.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;
import butterknife.OnTouch;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public class IntroFragment extends BaseFragment {

    @BindView(R.id.keyword) EditText keywordView;

    private IIntroViewModel viewModel;

    public String getKeyword() {
        return keywordView.getText().toString().trim();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        viewModel = new IntroViewModel(new IntroNavigator(getActivity()));
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        bind(viewModel.getErrorMessage()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showToast, this::handleError));

        bind(viewModel.getKeywordClearViewResource()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setKeywordClearViewResource, this::handleError));
    }

    private void handleError(Throwable throwable) {
        DLog.e(throwable);
    }

    private void showToast(int res) {
        Toast.makeText(getActivity(), res, Toast.LENGTH_SHORT).show();
    }

    private void setKeywordClearViewResource(@DrawableRes int drawableId) {
        keywordView.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawableId, 0);
    }

    @OnTouch(R.id.keyword)
    public boolean handleKeywordTouch(MotionEvent event) {
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

    @OnTextChanged(R.id.keyword)
    void onTextChanged(CharSequence charSequence) {
        viewModel.onKeywordChange(charSequence);
    }

    @OnEditorAction(R.id.keyword)
    boolean onEditorAction(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            viewModel.search();
        }

        return false;
    }

    public static IntroFragment newInstance() {

        Bundle args = new Bundle();

        IntroFragment fragment = new IntroFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_intro;
    }
}
