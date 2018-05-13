package com.tigaomobile.lockinapp.lockscreen.presentation.view.activity;

import android.content.Intent;
import android.os.Bundle;
import com.crashlytics.android.Crashlytics;
import com.tigaomobile.lockinapp.lockscreen.presentation.R;
import io.fabric.sdk.android.Fabric;

public class UnlockChallengeActivity extends ContentBaseActivity {
    private String TAG = "UnlockChallengeActivity";
//    private String doNext = "";

    @Override
    protected void getIntentParams() {
        super.getIntentParams();

//        Intent intentExtras = getIntent();
//        Bundle extraBundle = intentExtras.getExtras();
//
//        if (extraBundle != null && extraBundle.containsKey("doNext")) {
//            this.doNext = extraBundle.getString("doNext");
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_unlock_challange);
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
    }

    public void setAppUnlocked() {
        returnResult();
    }

    private void returnResult() {
        Intent intent = getIntent();
        intent.putExtra("result", "ok");
        setResult(RESULT_OK, intent);
        finish();
    }
}
