package me.chulwoo.naver.blog.search.ui.webview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import javax.inject.Inject;

import butterknife.BindView;
import me.chulwoo.naver.blog.search.R;
import me.chulwoo.naver.blog.search.di.qualifier.extra.Url;
import me.chulwoo.naver.blog.search.ui.base.BaseFragment;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public class WebViewFragment extends BaseFragment {

    public static final String TAG_ALERT = "alert";
    public static final String TAG_CONFIRM = "confirm";

    @BindView(R.id.webview)
    WebView webView;

    @Url
    @Inject
    String url;

    @Inject
    public WebViewFragment() {

    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                JsAlertDialog.newInstance(message, false)
                        .setOnConfirmAction(result::confirm)
                        .show(getFragmentManager(), TAG_ALERT);
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                JsAlertDialog.newInstance(message, true)
                        .setOnConfirmAction(result::confirm)
                        .setOnCancelmAction(result::cancel)
                        .show(getFragmentManager(), TAG_CONFIRM);
                return true;
            }
        });
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_webview;
    }
}
