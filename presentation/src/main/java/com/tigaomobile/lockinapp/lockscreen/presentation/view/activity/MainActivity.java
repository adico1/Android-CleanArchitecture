package com.tigaomobile.lockinapp.lockscreen.presentation.view.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import com.tigaomobile.lockinapp.lockscreen.presentation.R;

/**
 * Main application screen. This is the app entry point.
 */
public class MainActivity extends BaseActivity {

  private static final String TAG = MainActivity.class.getSimpleName();

  public static Intent getCallingIntent(Context context) {
    Intent callingIntent = new Intent(context, MainActivity.class);
    return callingIntent;
  }

  @BindView(R.id.dummy_button) Button dummy_button;
  @BindView(R.id.to_background_button) Button to_background_button;
  @BindView(R.id.to_home_button) Button to_home_button;
  @BindView(R.id.fullscreen_content) WebView webView;

  @Override protected void onResume() {
    super.onResume();

  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    loadWebView();
  }

  /**
   * Goes to the user list screen.
   */
  @OnTouch(R.id.dummy_button)
  boolean exit1() {
    finish();
    return true;
  }
  //void navigateToUserList() {
  //  this.navigator.navigateToUserList(this);
  //}

  /**
   * Goes to the user list screen.
   */
  @OnTouch(R.id.to_home_button)
  boolean exit3() {
    Intent startMain = new Intent(Intent.ACTION_MAIN);
    startMain.addCategory(Intent.CATEGORY_HOME);
    startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(startMain);

    return true;
  }

  /**
   * Goes to the user list screen.
   */
  @OnTouch(R.id.to_background_button)
  boolean exit2() {
    boolean success = moveTaskToBack (true);
    Log.i(TAG, "move to back result: " + success);
    return true;
  }

  private boolean isNetworkAvailable() {
    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService( CONNECTIVITY_SERVICE );
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
  }
  private void loadWebView() {
    final String url = "http://www.dev.adicococo.com/lockscreen/main.html";

    //        Bundle args = getArguments();
    //        if(args!=null) {
    //            String t_url = args.getString("url");
    //            if(t_url == null || t_url.isEmpty() ){
    //                url = t_url;
    //            }
    //        }

    WebSettings webSettings = webView.getSettings();
    webSettings.setJavaScriptEnabled(true);
    //webView.addJavascriptInterface(new WebAppInterface(getBaseProject()), "Android");

    webView.getSettings().setAppCacheMaxSize( 5 * 1024 * 1024 ); // 5MB
    webView.getSettings().setAppCachePath( getApplicationContext().getCacheDir().getAbsolutePath() );
    webView.getSettings().setAllowFileAccess( true );
    webView.getSettings().setAppCacheEnabled( true );
    webView.getSettings().setCacheMode( WebSettings.LOAD_DEFAULT ); // load online by default

    if ( !isNetworkAvailable() ) { // loading offline
      //webView.setVisibility(View.VISIBLE);
      webView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
    }

    webView.loadUrl(url + "?v=" + Math.random());


    webView.setWebViewClient(new WebViewClient() {
      boolean loadingFinished = true;
      boolean tWebViewVisibility = true;
      boolean redirect = false;

      @SuppressWarnings("deprecation")
      @Override
      public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        // Handle the error
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
          Log.i(TAG, "onReceivedError: " + errorCode + " "  + description);
        }

        if(failingUrl.toString().contains(url)) {
          tWebViewVisibility = false;
        }
      }

      @TargetApi(Build.VERSION_CODES.M)
      @Override
      public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
        // Redirect to deprecated method, so you can use it in all SDK versions
        onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
      }

      @TargetApi(Build.VERSION_CODES.LOLLIPOP)
      @Override
      public void onReceivedHttpError(WebView view, WebResourceRequest req, WebResourceResponse rerr) {
        onReceivedError(view, rerr.getStatusCode(), rerr.getReasonPhrase(), req.getUrl().toString());
      }

      @Override
      public boolean shouldOverrideUrlLoading(
          WebView view, WebResourceRequest request) {
        Log.i(TAG, "shouldOverrideUrlLoading: ");

        if (!loadingFinished) {
          redirect = true;
        }

        loadingFinished = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
          webView.loadUrl(request.getUrl().toString());
        }
        return true;
      }

      @Override
      public void onPageStarted(
          WebView view, String url, Bitmap favicon) {
        Log.i(TAG, "onPageStarted: " + url);

        super.onPageStarted(view, url, favicon);
        loadingFinished = false;
        //SHOW LOADING IF IT ISNT ALREADY VISIBLE
      }

      @Override
      public void onPageFinished(WebView view, String url) {
        Log.i(TAG, "onPageFinished: " + url);

        if (!redirect) {
          loadingFinished = true;
        }

        if (loadingFinished && !redirect) {
          //HIDE LOADING IT HAS FINISHED
          webView.setVisibility(tWebViewVisibility ? View.VISIBLE : View.INVISIBLE);
        } else {
          redirect = false;
        }
      }
    });
  }
}
