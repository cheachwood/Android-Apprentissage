package com.google.android.apps.plus.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.R.xml;

public class HangoutSettingsActivity extends BaseSettingsActivity
{
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    addPreferencesFromResource(R.xml.hangout_preferences);
    String str1 = getString(R.string.hangout_ringtone_setting_key);
    String str2 = getString(R.string.hangout_ringtone_setting_default_value);
    String str3 = PreferenceManager.getDefaultSharedPreferences(this).getString(str1, str2);
    Preference localPreference = findPreference(str1);
    String str4 = getRingtoneName(null, str1, str3);
    localPreference.setOnPreferenceChangeListener(new BaseSettingsActivity.RingtonePreferenceChangeListener(this, str1, str3));
    if (str4 != null)
      localPreference.setSummary(str4);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.settings.HangoutSettingsActivity
 * JD-Core Version:    0.6.2
 */