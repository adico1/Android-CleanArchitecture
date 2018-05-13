package com.tigaomobile.lockinapp.lockscreen.data.entity.auth;

import android.net.Uri;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

public class User{

    private String token;
    private String FBToken = "";

    public User(FirebaseUser user) {
        String uid = user.getUid();
        String providerId = user.getProviderId();
        String displayName = user.getDisplayName();
        Uri photoUrl = user.getPhotoUrl();
        if(photoUrl!=null) {
            String imageUrl = photoUrl.toString();
        }
        String email = user.getEmail();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String idToken) {
        token = idToken;
    }

    public String getFBToken() {
        return this.FBToken;
    }

    public void setFBToken(String FBToken) {
        this.FBToken = FBToken;
    }

    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    static public User create(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, User.class);
    }
}
