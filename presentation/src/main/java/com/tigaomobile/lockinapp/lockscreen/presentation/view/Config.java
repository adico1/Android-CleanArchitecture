package com.tigaomobile.lockinapp.lockscreen.presentation.view;

/*
  Created by adi on 08/03/2018.
  Description:
  Config repository - Static (json file) + dynamic (SharedPreferences) for application settings
 */

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import com.tigaomobile.lockinapp.lockscreen.presentation.R;
import com.tigaomobile.lockinapp.lockscreen.data.entity.auth.User;
import dagger.Module;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.inject.Inject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
@Module
public class Config {
    private static final String TAG = "Config";
    private static final String USER_PREFS_KEY = "userObject";
    private static final String PREFS_NAME = "appPref";
    private final Properties properties = new Properties();
    private SharedPreferences preferencesReader = null;

    private JSONArray lockViewAction;
    private JSONArray webView;
    private JSONObject controllerBtn;
    private String menuLocation;
    private String menuStyle;

    private void initConstructor(Context _context) {
        //Resources resources = context.getResources();
        preferencesReader = _context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String configJsonStr = loadJSONFromAsset(_context);

        try {
            initConfig(configJsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Inject public Config(Context _context) {
        initConstructor(_context);
    }

    private void initConfig(String configJsonStr) throws Resources.NotFoundException, IOException,
        JSONException {

        //InputStream rawResource = resources.openRawResource(R.raw.config);
        //properties.load(rawResource);

        String isInstalled = readSharedPref("isInstalled");
        properties.setProperty("isInstalled", isInstalled);
        properties.setProperty("user", readSharedPref(USER_PREFS_KEY));
        properties.setProperty("userPath", readSharedPref("userPath"));
        properties.setProperty("uuid", readSharedPref("uuid"));

        JSONObject obj = new JSONObject(configJsonStr);

        JSONObject appInfo = obj.getJSONObject("appInfo");
        JSONObject residentAppInfo = obj.getJSONObject("residentAppInfo");

        JSONObject views = obj.getJSONObject("views");

        webView = views.getJSONArray("webView");

        JSONObject buttonGroup = views.getJSONObject("lockView").getJSONObject("buttonGroup");
        lockViewAction = buttonGroup.getJSONArray("actions");
        controllerBtn = buttonGroup.getJSONObject("controlBtn");
        menuLocation =  buttonGroup.getString("location");
        menuStyle = buttonGroup.getString("style");

        properties.setProperty("pkgName", appInfo.getString("pkgName"));
        properties.setProperty("appName", appInfo.getString("appName"));
        properties.setProperty("firstTimeUrl", residentAppInfo.getString("firstTimeUrl"));
    }

//    public static String getConfigValue(Context context, String name) {
//        Resources resources = context.getResources();
//
//        try {
//            InputStream rawResource = resources.openRawResource(R.raw.config);
//            Properties properties = new Properties();
//            properties.load(rawResource);
//
//            return properties.getProperty(name);
//        } catch (Resources.NotFoundException e) {
//            // Log.e(TAG, "Unable to find the config file: " + e.getMessage());
//        } catch (IOException e) {
//            // Log.e(TAG, "Failed to open config file.");
//        }
//
//        return null;
//    }

    public String get(String key) {
        return properties.getProperty(key);
    }

    public void set(String key, String value) {
        synchronized (this) {
            properties.setProperty(key, value);

            SharedPreferences.Editor editor = preferencesReader.edit();
            editor.putString(key, value);
            editor.commit();
        }
    }

    public boolean isUnlockChallenge() {
        String unlockChallengeUrl = this.properties.getProperty("unlockChallengeUrl");
        return unlockChallengeUrl != null && !unlockChallengeUrl.equals("");
    }

    public String getPkgName() {
        return this.properties.getProperty("pkgName");
    }

    public String getAppName() {
        return this.properties.getProperty("appName");
    }

    public boolean isInstalled() {
        String sIsInstalled = this.properties.getProperty("isInstalled");
        return !(sIsInstalled == null || sIsInstalled.equals("")) && sIsInstalled.equals("true");

    }

    private void saveSharedPref(String serializedData) {
        // Save the serialized data into a shared preference
        SharedPreferences.Editor editor = preferencesReader.edit();
        editor.putString(USER_PREFS_KEY, serializedData);
        editor.commit();
    }

    public User getUser() {
        // Create a new object from the serialized data with the same state
        return User.create(this.properties.getProperty("user"));
    }

    public void setUser(User user) {
        // Save user to config
        saveSharedPref(user.serialize());
    }

    private String readSharedPref(String prefKey) {
        // Read the shared preference value
        return preferencesReader.getString(prefKey, "");
    }
    private String loadJSONFromAsset(Application context) {
        InputStream in = context.getResources().openRawResource(R.raw.config);
        return loadJSONFromAsset(in);
    }

    private String loadJSONFromAsset(Context context) {
        InputStream in = context.getResources().openRawResource(R.raw.config);
        return loadJSONFromAsset(in);
    }

    private String loadJSONFromAsset(InputStream in) {

        try {
            int count;
            byte[] bytes = new byte[32768];
            StringBuilder builder = new StringBuilder();
            while ( (count = in.read(bytes,0, 32768)) > 0) {
                builder.append(new String(bytes, 0, count));
            }

            in.close();
            return builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public String getwebView(int currentPage) {
        try {
            return ((JSONObject)webView.get(currentPage)).getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONArray getwebView() {
        return webView;
    }

    public JSONArray getLockViewActions() {
        return lockViewAction;
    }

    public boolean isExternalApp() {
        return !get("firstTimeUrl").isEmpty();
    }

    public JSONObject getLockViewCtrlBtn() {
        return controllerBtn;
    }

    public String getMenuLocation() {
        return menuLocation;
    }

    public String getMenuStyle() {
        return menuStyle;
    }

//    public void savePropertiesFile(Context context) {
//        Properties prop = new Properties();
//        String propertiesPath = context.getFilesDir().getPath() + "/properties.properties";
//        try {
//            FileOutputStream out = new FileOutputStream(propertiesPath);
//            prop.setProperty("HomeVersion", "0");
//            prop.setProperty("DatePlaySquare", "0");
//            prop.setProperty("CustomerID", "0");
//            prop.setProperty("DeviceToken", "0");
//            prop.setProperty("CurrentVersionMobile", "0");
//            prop.setProperty("Domain", "Megazy");
//            prop.setProperty("DownloadNewVersion", "0");
//            prop.store(out, null);
//            out.close();
//        } catch (IOException e) {
//            System.err.println("Failed to open app.properties file");
//            e.printStackTrace();
//        }
//    }
}