package com.tigaomobile.lockinapp.lockscreen.presentation.view.auth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.GoogleAuthProvider;
import com.tigaomobile.lockinapp.lockscreen.data.entity.auth.User;
import com.tigaomobile.lockinapp.lockscreen.presentation.R;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.events.EventDispatcher;

//import EventDispatcher;

public class GoogleSignInService implements
        GoogleApiClient.OnConnectionFailedListener {

    private boolean CHECK_USER_SIGN_IN = false;
    private static final String TAG = GoogleSignInService.class.getSimpleName();
    private static final int RC_SIGN_IN = 9001;

    private final FirebaseAuth _auth;
    private final GoogleApiClient _googleApiClient;
    private String _token = "";
    private User _user = null;
    private FirebaseUser currentUser;
    private final EventDispatcher<SigninEvent, SigninEventHandler> _dispatcher;
    private final com.tigaomobile.lockinapp.lockscreen.presentation.view.auth.BaseActivity _context;

    public GoogleSignInService(com.tigaomobile.lockinapp.lockscreen.presentation.view.auth.BaseActivity context) {

        // create an instance of an eventhandler
        this._dispatcher = new EventDispatcher<>();
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getResources().getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        _googleApiClient = new GoogleApiClient.Builder(context)
                .enableAutoManage(context /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // initialize auth
        _auth = FirebaseAuth.getInstance();
        this._context = context;

    }

    public EventDispatcher<SigninEvent, SigninEventHandler> dispatcher() {
        return this._dispatcher;
    }


    public void setSomeEventListener () {
        if(!CHECK_USER_SIGN_IN) {
            onUserUpdated(null);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // TODO: if (resultCode == RESULT_OK) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                _token = account != null ? account.getIdToken() : null;
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                onUserUpdated(null);
            }
        }
    }

    // auth with google
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        // Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        _context.showProgressDialog();

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        _auth.signInWithCredential(credential)
            .addOnCompleteListener(_context, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        // Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser currentUser = _auth.getCurrentUser();
                        onUserUpdated(currentUser);
                    } else {
                        // If sign in fails, display a message to the user.
                        // Log.w(TAG, "signInWithCredential:failure", task.getException());
                        Toast.makeText(_context, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        onUserUpdated(null);
                    }
                    _context.hideProgressDialog();
                }
            });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        // Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(_context, "Connection Failed.", Toast.LENGTH_SHORT).show();
    }

    public User getUser() {
        return _user;
    }

    public void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(_googleApiClient);
        _context.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void signOut() {
        // Firebase sign out
        _auth.signOut();

        // Google sign out
        Auth.GoogleSignInApi.signOut(_googleApiClient).setResultCallback(
            new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    onUserUpdated(null);
                }
            });
    }

    public void revokeAccess() {
        // Firebase sign out
        _auth.signOut();

        // Google revoke access
        Auth.GoogleSignInApi.revokeAccess(_googleApiClient).setResultCallback(
            new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                onUserUpdated(null);
            }
        });
    }

    public boolean getCheckUserSignIn() {
        return CHECK_USER_SIGN_IN;
    }

    public void checkSigninStatus() {
        currentUser = _auth.getCurrentUser();
        if(currentUser != null) {
            CHECK_USER_SIGN_IN = true;
            currentUser.getToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            _token = task.getResult().getToken();
                        }
                        onUserUpdated(currentUser);
                    }
                });
        }
        else {
            onUserUpdated(null);
        }
    }

    public User buildUser(FirebaseUser cUser) {
        User user = new User(cUser);
        user.setToken(_token);
//        String FBToken = FirebaseInstanceId.getInstance().getToken();
//        user.setFBToken(FBToken);
        return user;
    }

    private void onUserUpdated(FirebaseUser fbUser) {
        _context.hideProgressDialog();
        _user = fbUser != null ? buildUser(fbUser): null;

        SigninEvent signinEvent = new SigninEvent(_user);
        _dispatcher.notify(signinEvent);
    }



}
