package com.example.aschaal.movieandroid;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.example.aschaal.movieandroid.R;

/**
 * Created by aschaal on 02/11/2016.
 */

public class SettingsActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);
    }
}
