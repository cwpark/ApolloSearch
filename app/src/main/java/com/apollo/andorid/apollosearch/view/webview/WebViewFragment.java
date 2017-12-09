package com.apollo.andorid.apollosearch.view.webview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.apollo.andorid.apollosearch.R;
import com.apollo.andorid.apollosearch.view.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public class WebViewFragment extends BaseFragment {

    public static final String TAG_ALERT = "alert";
    public static final String TAG_CONFIRM = "confirm";

    private static final String ARG_URL = "url";

    @BindView(R.id.webview) WebView webView;

    private String url;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString(ARG_URL);
        } else if (savedInstanceState != null) {
            url = savedInstanceState.getString(ARG_URL);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ARG_URL, url);
    }

    public static WebViewFragment newInstance(String url) {

        Bundle args = new Bundle();
        args.putString(ARG_URL, url);

        WebViewFragment fragment = new WebViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_webview;
    }
}
