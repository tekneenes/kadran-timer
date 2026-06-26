package com.retro.flipclock;

import android.annotation.SuppressLint;
import android.service.dreams.DreamService;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

public class RetroClockSaver extends DreamService {

    private WebView mWebView;

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        // Exit screensaver on user interaction
        setInteractive(false);

        // Fullscreen mode
        setFullscreen(true);

        // Create root layout
        FrameLayout layout = new FrameLayout(this);
        layout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        mWebView = new WebView(this);
        mWebView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        setupWebView();

        layout.addView(mWebView);
        setContentView(layout);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupWebView() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        
        // Media playback options
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setMediaPlaybackRequiresUserGesture(false);

        // Inject bridge interface
        mWebView.addJavascriptInterface(new AndroidSettingsInterface(this), "AndroidSettings");

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
    }

    @Override
    public void onDreamingStarted() {
        super.onDreamingStarted();
        mWebView.loadUrl("file:///android_asset/index.html");
    }

    @Override
    public void onDreamingStopped() {
        super.onDreamingStopped();
        mWebView.loadUrl("about:blank");
    }

    @Override
    public void onDetachedFromWindow() {
        if (mWebView != null) {
            mWebView.destroy();
            mWebView = null;
        }
        super.onDetachedFromWindow();
    }
}
