package com.soundllytest;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ResultUrlActivity extends Activity {
    private WebView mWebView = null;
    private String mHomeUrl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.result_uri_activity);

        mHomeUrl = getIntent().getStringExtra("url");

        mWebView = (WebView)findViewById(R.id.web_view);

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);

        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(mHomeUrl);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                    return true;
                }
                break;

            default:
                break;
        }

        return super.onKeyDown(keyCode, event);
    }
}
