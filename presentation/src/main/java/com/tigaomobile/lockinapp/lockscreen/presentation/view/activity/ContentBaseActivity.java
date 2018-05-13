package com.tigaomobile.lockinapp.lockscreen.presentation.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.tigaomobile.lockinapp.lockscreen.presentation.R;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.WebAppInterface;

public abstract class ContentBaseActivity extends BaseActivity {
    private static String TAG = ContentBaseActivity.class.getSimpleName();

    private String url = "";

    protected void getIntentParams() {
        Intent intentExtras = getIntent();
        Bundle extraBundle = intentExtras.getExtras();

        if(extraBundle==null) {
            return;
        }

        if(extraBundle.isEmpty()) {
            // Log.e(TAG, "Missing parameters for intent init");
            return;
        }

        if(!extraBundle.containsKey("url")) {
            // Log.e(TAG, "Missing parameters URL for intent init");
            return;
        }

        this.url = extraBundle.getString("url");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Log.i(TAG, "onCreate::url: " + this.url);

        getIntentParams();

        WebView webView = findViewById(R.id.webview);
        //webView.loadUrl("http://www.dev.adicococo.com/bla2.html");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        webView.loadUrl(url + "?v=" + Math.random());
    }

    public void handleGesture(String direction) {
        // Log.i(TAG,"handleGesture");

        switch(direction) {
            case "left":
                int page = -1;
                if(page ==0) {
                    returnResult("unlock");
                } else {
                    returnResult(direction);
                }
                break;
            case "right":
                returnResult(direction);
                break;
            default:
        }
    }

    private void returnResult(String result) {
        Intent intent = getIntent();
        intent.putExtra("action", result);
        setResult(RESULT_OK, intent);
        finish();
    }
}
