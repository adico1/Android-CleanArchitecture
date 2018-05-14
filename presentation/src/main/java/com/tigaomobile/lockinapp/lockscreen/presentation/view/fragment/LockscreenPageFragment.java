package com.tigaomobile.lockinapp.lockscreen.presentation.view.fragment;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.tigaomobile.lockinapp.lockscreen.presentation.R;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.Config;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.WebAppInterface;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.actions.IAction;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.component.buttongroup.ButtonGroup;
import javax.inject.Inject;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by adi on 22/03/2018.
 */

public class LockscreenPageFragment extends BaseAppFragment {

    String url = "";
    private ViewGroup rootView;
    private Config config;

    @Override
    public void setConfig(Config config) {
        this.config = config;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(
                R.layout.activity_fullscreen, container, false);

//        CircleMenu cm = (CircleMenu) rootView.findViewById(R.id.c_menu);
//        cm.addMenuItem("one",1);
//        cm.addMenuItem("two",2);
//        cm.addMenuItem("three",3);
//        cm.addMenuItem("ten",10);
//        cm.addMenuItem("oh oh",156);
//        cm.addMenuItem("exit",134);
//        cm.setListener(new CircleMenu.IMenuListener() {
//            @Override
//            public void onMenuClick(CircleMenu.MenuCircle item) {
//                Toast.makeText(getActivity(),item.text+" "+item.id,Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onMenuSelect(CircleMenu.MenuCircle item) {
//                Toast.makeText(getActivity(),item.text+" "+item.id,Toast.LENGTH_LONG).show();
//            }
//        });

        init2();

        return rootView;
    }

    // helper function to execute an action based on its name
    private void doAction(IAction doNext) {
        // if unlock flow should include external app unlock permission/challenge
        if(config.isUnlockChallenge()){
            getBaseProject().unlockAppAnd(doNext);
            return;
        }

        // unlock flow doesn't require externalAppUnlock permission
        // execute do next action ( which includes a deviceUnlock call )
        doNext.run();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService( CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void init2() {
        final LockscreenPageFragment context = this;
        ButtonGroup mButtonView = rootView.findViewById(R.id.buttonGroup);
        mButtonView.setConfig(this.config);

        String url = "http://www.dev.adicococo.com/lockscreen/main.html";

//        Bundle args = getArguments();
//        if(args!=null) {
//            String t_url = args.getString("url");
//            if(t_url == null || t_url.isEmpty() ){
//                url = t_url;
//            }
//        }

        //WebView webView = rootView.findViewById(R.id.fullscreen_content);
        //WebSettings webSettings = webView.getSettings();
        //webSettings.setJavaScriptEnabled(true);
        //webView.addJavascriptInterface(new WebAppInterface(getBaseProject()), "Android");
        //
        //webView.getSettings().setAppCacheMaxSize( 5 * 1024 * 1024 ); // 5MB
        //webView.getSettings().setAppCachePath( getActivity().getApplicationContext().getCacheDir().getAbsolutePath() );
        //webView.getSettings().setAllowFileAccess( true );
        //webView.getSettings().setAppCacheEnabled( true );
        //webView.getSettings().setCacheMode( WebSettings.LOAD_DEFAULT ); // load online by default
        //
        //if ( !isNetworkAvailable() ) { // loading offline
        //    webView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
        //}
        //
        //webView.loadUrl(url); // + "?v=" + Math.random());

        // Step 4 - Setup the listener for this object
        mButtonView.setCustomObjectListener(new ButtonGroup.ButtonGroupListener() {
            @Override
            public void onCommand(IAction command) { doAction(command);}

//            @Override
//            public void onPhone() {
//                doAction("Phone");
//            }
//            @Override
//            public void onUnlock() { doAction("");}
//            @Override
//            public void onWhatsapp() {
//                doAction("Whatsapp");
//            }
//            @Override
//            public void onChrome() {
//                doAction("Chrome");
//            }
//            @Override
//            public void onFB() {
//                doAction("Facebook");
//            }
        });
    }
}
