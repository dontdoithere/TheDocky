package ca.group6.it.thedocky.ui.settings;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import ca.group6.it.thedocky.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}