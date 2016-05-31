package com.alexlowe.choices;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Created by Keyes on 5/29/2016.
 */
public class ChoiceSaver {
    public static final String PREFS_NAME = "ChoicesPrefsFile";
    public static final String KEY_MASTER = "master";


    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    public ChoiceSaver(Context context) {
        mSharedPreferences = context.getSharedPreferences(PREFS_NAME, 0);
        mEditor = mSharedPreferences.edit();
    }

    private static MasterList retrieveSaved(String name, SharedPreferences sp) {
        Gson gson = new Gson();
        String ChoiceStr = sp.getString(name, "No data");

        return gson.fromJson(ChoiceStr, MasterList.class);
    }

    public void saveMasterList() {
        Gson gson = new Gson();
        String masterList = gson.toJson(MasterList.gMasterList);

        mEditor.putString("master", masterList);
        mEditor.apply();
    }

    public void pullData() {

        if (mSharedPreferences.contains(KEY_MASTER)) {
            MasterList ml = retrieveSaved("master", mSharedPreferences);
            MasterList.gMasterList.setMasterList(ml.getMasterList());
        }

    }

    public void clearPrefs() {
        mEditor.clear();
        mEditor.apply();
    }
}
