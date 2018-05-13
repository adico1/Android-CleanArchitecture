package com.tigaomobile.lockinapp.lockscreen.presentation.view;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;
import com.google.firebase.database.FirebaseDatabase;
import java.io.IOException;
import org.json.JSONException;

/**
 * Created by adi on 29/03/2018.
 * Description:
 */

public class MApplication extends Application {
    /** Instance of the current application. */
    private static MApplication instance;
    private Config config = null;
    /**
     * Constructor.
     */
    public MApplication() {
        instance = this;
    }

    /**
     * Gets the application context.
     *
     * @return the application context
     */
    private static Context getContext() {
        if (instance == null) {
            instance = new MApplication();
        }
        return instance;
    }

    /**
     * Gets the application config.
     *
     * @return the application context
     */
    public static Config getConfig() {
        if (instance == null) {
            instance = new MApplication();
        }

        if( instance.config != null ) {
            return instance.config;
        }

        instance.config = new Config(instance);
        return instance.config;
    }

    /**
     * display toast message
     *
     * @param data - msg
     */
    public static void showToast(String data) {
        Toast.makeText(getContext(), data,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
