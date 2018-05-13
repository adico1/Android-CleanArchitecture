package com.tigaomobile.lockinapp.lockscreen.presentation.view.auth;

import com.tigaomobile.lockinapp.lockscreen.presentation.view.events.IEventListener;

public class SigninEventHandler implements IEventListener<SigninEvent> {

    private final ISigninEventHandler _owner;

    public SigninEventHandler(ISigninEventHandler owner) {
        this._owner = owner;
    }
    public void handleEvent(SigninEvent event) {
        this._owner.onSigninChanged(event);
    }
}
