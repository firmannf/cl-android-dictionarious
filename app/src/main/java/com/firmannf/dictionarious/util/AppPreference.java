package com.firmannf.dictionarious.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.firmannf.dictionarious.R;

/**
 * Created by codelabs on 23/07/18.
 */

public class AppPreference {

    private SharedPreferences prefs;
    private Context context;

    public AppPreference(Context context) {
        prefs = context.getSharedPreferences(AppConstant.KEY_APP_PREFERENCE, Context.MODE_PRIVATE);
        this.context = context;
    }

    public void setFirstRun(Boolean isFirstRun) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(AppConstant.KEY_APP_PREFERENCE_FIRST_RUN, isFirstRun);
        editor.apply();
    }

    public Boolean getFirstRun() {
        return prefs.getBoolean(AppConstant.KEY_APP_PREFERENCE_FIRST_RUN, true);
    }
}
