package com.tigaomobile.lockinapp.lockscreen.presentation.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.tigaomobile.lockinapp.lockscreen.data.entity.auth.User;
import com.tigaomobile.lockinapp.lockscreen.presentation.R;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.auth.GoogleSignInService;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.auth.ISigninEventHandler;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.auth.SigninEvent;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.auth.SigninEventHandler;
import io.fabric.sdk.android.Fabric;

public class LoginActivity extends com.tigaomobile.lockinapp.lockscreen.presentation.view.auth.BaseActivity implements
        View.OnClickListener, ISigninEventHandler {

    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 9001;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    private String _token = "";
    private GoogleApiClient mGoogleApiClient;
    private TextView mStatusTextView;
    private TextView mDetailTextView;

    private GoogleSignInService gSignInSrv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_login);

        findViewById(R.id.sign_in_button).setOnClickListener(this);

        gSignInSrv = new GoogleSignInService(this);
        gSignInSrv.dispatcher().addEventListener(new SigninEventHandler(this));

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        gSignInSrv.checkSigninStatus();
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);


            //TODO: You need to send me the FirebaseUser end the token
            //getToken()

            User currentUser = gSignInSrv.buildUser(user);
//            SigninEvent signinEvent = new SigninEvent(currentUser);
        } else {
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.sign_in_button) {
            gSignInSrv.signIn();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        gSignInSrv.onActivityResult(requestCode, resultCode, data);
    }

    public String getToken(FirebaseUser currentUser) {
        currentUser.getToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            _token = task.getResult().getToken();
                        }
                    }
                });
        return _token;
    }

    public void onSigninChanged(SigninEvent event) {
        System.out.println("onSigninChanged ");
        User user = event.getUser();

        if(user != null) {
            Intent intent = getIntent();
            intent.putExtra("user", user.serialize());
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
