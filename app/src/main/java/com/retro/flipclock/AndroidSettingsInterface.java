package com.retro.flipclock;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.webkit.JavascriptInterface;

public class AndroidSettingsInterface {
    private Context mContext;

    public AndroidSettingsInterface(Context context) {
        this.mContext = context;
    }

    /**
     * Reads a setting from Android SharedPreferences.
     * Called synchronously from React Web.
     */
    @JavascriptInterface
    public String getSetting(String key, String defaultValue) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        // We store everything as strings to make bridging seamless and avoid type conversion bugs
        return prefs.getString(key, defaultValue);
    }

    /**
     * Writes a setting into Android SharedPreferences.
     * Called asynchronously/synchronously from React Web.
     */
    @JavascriptInterface
    public void setSetting(String key, String value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        prefs.edit().putString(key, value).apply();
    }
}
