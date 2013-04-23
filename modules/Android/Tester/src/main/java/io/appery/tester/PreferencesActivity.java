package io.appery.tester;

import io.appery.tester.preferences.ButtonPreference;
import io.appery.tester.utils.Constants;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

public class PreferencesActivity extends PreferenceActivity implements OnPreferenceChangeListener {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        initPreference(Constants.PREFERENCES.BASE_URL);
        initPreference(Constants.PREFERENCES.USERNAME);
    }

    private void initPreference(String name) {
        Preference preference = findPreference(name);

        if (preference instanceof EditTextPreference) {
            preference.setOnPreferenceChangeListener(this);

            EditTextPreference p = (EditTextPreference) preference;
            preference.setSummary(p.getText());
        } else if (preference instanceof ButtonPreference) {
            preference.setOnPreferenceChangeListener(this);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (Constants.PREFERENCES.USERNAME.equals(preference.getKey())) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            if (prefs != null) {
                try {
                    Editor editor = prefs.edit();
                    editor.putString(Constants.PREFERENCES.USERNAME, null);
                    editor.putString(Constants.PREFERENCES.PASSWORD, null);
                    editor.commit();
                } catch (Exception e) {
                    Log.e(PreferencesActivity.class.getName(), "Set preference error");
                }
            }
            return true;
        }
        preference.setSummary(String.valueOf(newValue));
        return true;
    }
}
