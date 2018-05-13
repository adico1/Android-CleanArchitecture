package com.tigaomobile.lockinapp.lockscreen.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import com.tigaomobile.lockinapp.lockscreen.presentation.R;
import com.tigaomobile.lockinapp.lockscreen.presentation.model.ThemeModel;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.Config;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.ScreenSliderPagerView;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.WebAppInterface;

/**
 * Created by adi on 22/03/2018.
 */

public class ScreenSlidePageFragment extends BaseAppFragment implements ScreenSliderPagerView {

    String url = "";
    private NavListener listener;
    private Config config;

    @Override public void setConfig(Config config) {
        this.config = config;
    }

    @Override public void renderTheme(ThemeModel themeModel) {

    }

    @Override public void viewTheme(ThemeModel themeModel) {

    }

    @Override public void showLoading() {

    }

    @Override public void hideLoading() {

    }

    @Override public void showRetry() {

    }

    @Override public void hideRetry() {

    }

    @Override public void showError(String message) {

    }

    @Override public Context context() {
        return null;
    }

    public interface NavListener {
        // These methods are the different events and
        // need to pass relevant arguments related to the event triggered
        void onPrev();
        void onNext();
    }

    // Assign the listener implementing events interface that will receive the events
    public void setCustomObjectListener(NavListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);

        ImageButton uiPrevBtn = rootView.findViewById(R.id.uiPrevBtn);
        uiPrevBtn.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v) {
                listener.onPrev();
            }
        });

        ImageButton uiNextBtn = rootView.findViewById(R.id.uiNextBtn);
        uiNextBtn.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v) {
                listener.onNext();
            }
        });

        Bundle args = getArguments();
        String url = args.getString("url");
        if(url == null || url.isEmpty() ){
            url = "http://www.byoml.com/blog/mobile";
        }

        WebView webView = rootView.findViewById(R.id.webview);
        //webView.loadUrl("http://www.dev.adicococo.com/bla2.html");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebAppInterface(rootView.getContext()), "Android");
        webView.loadUrl(url + "?v=" + Math.random());

        return rootView;
    }
}
