package com.tigaomobile.lockinapp.lockscreen.presentation.view.auth;

import com.tigaomobile.lockinapp.lockscreen.data.entity.auth.User;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.events.IEventData;

public class SigninEvent implements IEventData {

    private User _user;

    public SigninEvent() {
    }

    public SigninEvent(User user) {
        this._user = user;
    }

    public User getUser() {
        return _user;
    }

    public void setUser(User user) {
        this._user = user;
    }
}
