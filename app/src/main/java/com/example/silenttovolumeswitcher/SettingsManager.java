package com.example.silenttovolumeswitcher;

import android.content.SharedPreferences;
import android.content.Context;

public class SettingsManager {

    private static final String PREFS_NAME = "SilentSwitcherPrefs";
    private static final String KEY_TIME_DELAY = "timeDelay";
    public static final long default_delay  = 10 * 1000;

    public final SharedPreferences sharedPreferences;

    public SettingsManager(Context context){
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void setTimeDelay(long timeInMillis) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(KEY_TIME_DELAY, timeInMillis);
        editor.apply();
    }

    public long getTimeDelay() {
        return sharedPreferences.getLong(KEY_TIME_DELAY, default_delay);
    }

}
