package me.chulwoo.naver.blog.search.ui.intro;

import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;
import butterknife.OnTouch;
import me.chulwoo.naver.blog.search.R;
import me.chulwoo.naver.blog.search.ui.base.mvp.MvpFragment;
import me.chulwoo.naver.blog.search.ui.exception.EmptyKeywordException;
import me.chulwoo.naver.blog.search.util.DLog;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public class IntroFragment extends MvpFragment<IntroContract.Presenter> implements IntroContract.View {

    @BindView(R.id.keyword)
    EditText keywordView;

    @Inject
    public IntroFragment() {

    }

    @Override
    public void showKeyword(String keyword) {
        keywordView.setText(keyword);
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
    public void showError(Throwable e) {
        if (e instanceof EmptyKeywordException) {
            showToast(R.string.input_keyword);
        } else {
            DLog.e(e);
        }
    }

    private void showToast(int res) {
        Toast.makeText(getActivity(), res, Toast.LENGTH_SHORT).show();
    }

    @OnTouch(R.id.keyword)
    public boolean handleKeywordTouch(MotionEvent event) {
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
        presenter.onKeywordChange(charSequence);
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
        return R.layout.fragment_search_intro;
    }
}
