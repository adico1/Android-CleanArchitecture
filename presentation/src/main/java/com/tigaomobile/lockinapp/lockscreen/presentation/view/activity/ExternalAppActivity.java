package com.tigaomobile.lockinapp.lockscreen.presentation.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import com.crashlytics.android.Crashlytics;
import com.tigaomobile.lockinapp.lockscreen.data.entity.auth.User;
import com.tigaomobile.lockinapp.lockscreen.presentation.R;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.Config;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.WebAppInterface;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.utils.DeviceUUIDFactory;
import io.fabric.sdk.android.Fabric;
import java.io.IOException;
import java.util.UUID;
import org.json.JSONException;

/***
 * Loads a splash screen and in the background initialize the application
 * There are some parameters in play which changes the flow:
 * - onInstall
 * - onNormalFlow
 * - isExternalApp
 *
 * externalApp flow diffrentiate from regular flow by:
 *   // External App Flow
 *      // - pass parameters to external app
 *      // - wait for getUser call (onTimeout activate lockscreen)
 *      // - send requestLogin to external app
 *      // - wait for appReady (onTimeout activate lockscreen)
 *      // - open lockscreen
 */
public class ExternalAppActivity extends RTDAOActivity {

    private static final String TAG = ExternalAppActivity.class.getSimpleName();

    private static final int LOGIN_REQUEST = 1;

    private WebView webview = null;
    private ProgressBar pbar = null;
    private Config config = null;
    private UUID uuid = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        Log.i(TAG, "onCreate");

        setContentView(R.layout.activity_splash_screen);

        config = new Config(this);
        finish();
    }

    @Override
    protected void onServiceBounded() {
        super.onServiceBounded();
        Log.i(TAG, "Service is bonded successfully!");
        initialize();
    }

    private boolean isExtrnalApp() {
        Log.i(TAG, "isExternalApp");

        return config.isExternalApp();
    }

    private boolean isInitialized = false;
    private void initialize() {
        Log.i(TAG, "initialize");

        if(isInitialized) {
            return;
        }

        isInitialized = true;

        if (isInstall()) {
            execInstallFlow();
        }

        execNormalFlow();
    }

    private void execInstallFlow() {
        Log.i(TAG, "execInstallFlow");

        generateUUID();
        config.set("userPath", getUserPath());
        registerUser();
        config.set("isInstalled", "true");
        config.set("uuid", uuid.toString());
    }

    private void execNormalFlow() {
        Log.i(TAG, "execNormalFlow");
        String strUUID = config.get("uuid");

        uuid = UUID.fromString(strUUID);

        Crashlytics.log("uuid: " + strUUID);

        mService.get().init(getUserPath());
        if(isExtrnalApp()) {
            // External App Flow
            // - openFirstTimeUrl
            // - pass parameters to external app
            // - wait for getUser call (onTimeout activate lockscreen)
            // - send requestLogin to external app
            // - wait for appReady (onTimeout activate lockscreen)
            // - open lockscreen

            // The following command starts the external app flow
            openFirstTimeUrl();
            return;
        }

        // if not external app then open the lockscreen
        openLockscreenActivity();
    }

    // Open firstTimeUrl interface with external app
    // See WebViewClient on PageFinishedLoading for the continue of this flow
    private void openFirstTimeUrl() {
        Log.i(TAG, "openFirstTimeUrl");
        webview = findViewById(R.id.webView1);
        pbar = findViewById(R.id.progressBar1);
        webview.setWebViewClient(new WebViewClient(this));
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.addJavascriptInterface(new WebAppInterface(this), "Android");
        webview.setBackgroundColor(Color.TRANSPARENT);
        String firstTimeUrl = config.get("firstTimeUrl");
        Log.i(TAG, "url:" + firstTimeUrl);
        webview.loadUrl(firstTimeUrl + "?v=" + Math.random());
    }

    private void openLockscreenActivity() {
        Log.i(TAG, "openLockscreenActivity");

        if(pbar!=null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //stuff that updates ui
                        pbar.setVisibility(View.GONE);
                }
            });
        }

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                /* Create an Intent that will start the Menu-Activity. */


                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        thread.start();
    }

    private boolean isInstall() {
        Log.i(TAG, "isInstall");

        return !config.isInstalled();
    }

    private void generateUUID() {
        Log.i(TAG, "generateUUID");
        uuid = (new DeviceUUIDFactory(this)).getDeviceUuid();
    }

    private String getUserPath() {
        Log.i(TAG, "getUserPath");
        return config.getPkgName() + "/" +
                config.getAppName() + "/" +
                uuid.toString();
    }

    private void registerUser() {
        Log.i(TAG, "registerUser");
        // TODO: if signup fails due to no network or such, can continue to lockscreen app
        // and try to signup later ...
        mService.get().signup(config.get("userPath"));
    }

    /***
     * External app flow complete
     * Open lockscreen activty
     */
    public void onExternalAppInitialized() {
        Log.i(TAG, "onExternalAppInitialized");
        openLockscreenActivity();
    }

    private void sendUserToExternallApp(User user) {
        Log.i(TAG, "sendUserToExternallApp");
        String jsonParam = user.serialize();
        jsonParam = jsonParam.replaceAll("\"","\\\"");

        String script = "requestLogin('" + jsonParam + "');";
        runScript(script);
        // TODO: createTimeoutTimer(callback);
    }

    /***
     * if external application
     * Send the user to the external application
     * (and wait for appInitComplete from externalApp)
     *
     * otherwise
     * open lockscreen activity
     *
     * @param userData - user object
     */
    private void externalAppOrLockscreen(User userData) {
        Log.i(TAG, "externalAppOrLockscreen");
        // if external app exists send info to external app and wait for callback
        if(isExtrnalApp()) {
            sendUserToExternallApp(userData);
            // TODO: createTimeoutTimer(callback);
            return;
        }

        // no external app - process to lockscreen
        openLockscreenActivity();
    }

    /***
     * A call of getUser from external app is mapped to requestLogin
     * if user exists, and user valid ->
     *  send the user to the external app
     *  (now we wait for appInit call from external app - see onExternalAppInitialized
     *
     * Otherwise user not exists open google login activity
     * This flow continues on onActivityResult
     */
    public void requestLogin() {
        Log.i(TAG, "requestLogin");
        User user = config.getUser();
        if(user==null) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivityForResult(loginIntent, LOGIN_REQUEST);
            return;
        }

        // TODO: if need to refresh token
//        if(!user.isValid()) {
//            refreshUserToken();
//        }

        externalAppOrLockscreen(user);
    }

    /**
     * User logged using google oAuth and we received the user object after login
     * currently this happends only by external applications with login
     * save the user in config and
     * send the user to the external application and wait for appInitComplete
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult");

        if(resultCode == RESULT_OK){
            if(requestCode == LOGIN_REQUEST && data !=null) {
                String strMessage = data.getStringExtra("keyName");
                Log.i(TAG, "onActivityResult: message >>" + strMessage);
            }
        }


        // Make sure the request was successful
        if (resultCode == RESULT_OK) {
            // Check which request we're responding to
            if (requestCode == LOGIN_REQUEST && data !=null) {

                User userData = User.create(data.getStringExtra("user"));
                config.setUser(userData);

                externalAppOrLockscreen(userData);
            }
        }
    }

    public void extAppErr() {
        Log.i(TAG, "extAppErr");
        openLockscreenActivity();
    }

    public void onJsReady() {
        Log.i(TAG, "onJsReady");

        // TODO Auto-generated method stub

        String pkgName = config.get("pkgName");
        String appName = config.get("appName");
        String uuid = config.get("uuid");

        String script = "initApp(\"" + pkgName + "\", \"" + appName + "\", \"" + uuid + "\");";
        runScript(script);
    }

    private void runScript(final String script) {
        webview.post(new Runnable() {
            @Override
            public void run() {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    Log.i(TAG, "executing: " + script);
                    webview.evaluateJavascript(script, null);
                } else {
                    Log.i(TAG, "executing2: " + script);
                    webview.loadUrl("javascript:" + script);
                }
            }
        });
    }

    public class WebViewClient extends android.webkit.WebViewClient
    {
        ExternalAppActivity activity = null;

        public WebViewClient(ExternalAppActivity _activity) {
            activity = _activity;
        }
//        @Override
//        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//
//            // TODO Auto-generated method stub
//            super.onPageStarted(view, url, favicon);
//        }

//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//
//            // TODO Auto-generated method stub
//            view.loadUrl(url);
//
//            return true;
//        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Log.i(TAG, "Oh no! " + description);
        }
    }
}
