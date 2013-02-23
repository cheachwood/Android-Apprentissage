package com.google.android.apps.plus.settings;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceGroup;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.InstrumentedActivity;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;

public class BaseSettingsActivity extends PreferenceActivity
{
  private static final OzViews VIEW = OzViews.GENERAL_SETTINGS;
  protected Handler mHandler;
  protected Switch mMasterSwitch;
  protected boolean mViewNavigationRecorded;

  public void finish()
  {
    EsAccount localEsAccount = getAccount();
    if (localEsAccount != null)
      InstrumentedActivity.recordReverseViewNavigation(this, localEsAccount, VIEW, null);
    super.finish();
  }

  protected final EsAccount getAccount()
  {
    Parcelable localParcelable = getIntent().getParcelableExtra("account");
    EsAccount localEsAccount;
    if (localParcelable == null)
      localEsAccount = null;
    while (true)
    {
      return localEsAccount;
      if ((localParcelable instanceof EsAccount))
        localEsAccount = (EsAccount)localParcelable;
      else
        localEsAccount = null;
    }
  }

  protected final Switch getMasterSwitch()
  {
    return this.mMasterSwitch;
  }

  protected final String getRingtoneName(String paramString1, String paramString2, String paramString3)
  {
    if (paramString1 == null)
      paramString1 = PreferenceManager.getDefaultSharedPreferences(this).getString(paramString2, paramString3);
    String str;
    if ((paramString1 == null) || (paramString1.length() == 0))
      str = getString(R.string.realtimechat_settings_silent_ringtone);
    while (true)
    {
      return str;
      Ringtone localRingtone = RingtoneManager.getRingtone(this, Uri.parse(paramString1));
      if (localRingtone == null)
        str = null;
      else
        str = localRingtone.getTitle(this);
    }
  }

  protected final void hookMasterSwitch(PreferenceCategory paramPreferenceCategory, final CheckBoxPreference paramCheckBoxPreference)
  {
    if ((Build.VERSION.SDK_INT >= 14) && (paramCheckBoxPreference != null) && ((onIsHidingHeaders()) || (!onIsMultiPane())))
    {
      this.mMasterSwitch = new Switch(this);
      ActionBar localActionBar = getActionBar();
      int i = getResources().getDimensionPixelSize(R.dimen.action_bar_switch_padding);
      this.mMasterSwitch.setPadding(0, 0, i, 0);
      localActionBar.setDisplayOptions(16, 16);
      localActionBar.setCustomView(this.mMasterSwitch, new ActionBar.LayoutParams(-2, -2, 21));
      this.mMasterSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
      {
        public final void onCheckedChanged(CompoundButton paramAnonymousCompoundButton, boolean paramAnonymousBoolean)
        {
          Preference.OnPreferenceChangeListener localOnPreferenceChangeListener = paramCheckBoxPreference.getOnPreferenceChangeListener();
          if ((localOnPreferenceChangeListener == null) || (localOnPreferenceChangeListener.onPreferenceChange(paramCheckBoxPreference, Boolean.valueOf(paramAnonymousBoolean))))
            paramCheckBoxPreference.setChecked(paramAnonymousBoolean);
        }
      });
      this.mMasterSwitch.setChecked(paramCheckBoxPreference.isChecked());
      getPreferenceScreen().removePreference(paramCheckBoxPreference);
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    boolean bool = true;
    super.onCreate(paramBundle);
    this.mHandler = new Handler(getMainLooper());
    if (Build.VERSION.SDK_INT >= 11)
      getActionBar().setDisplayHomeAsUpEnabled(bool);
    if (paramBundle != null);
    while (true)
    {
      this.mViewNavigationRecorded = bool;
      return;
      bool = false;
    }
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default:
    case 16908332:
    }
    for (boolean bool = false; ; bool = true)
    {
      return bool;
      finish();
    }
  }

  public void onResume()
  {
    super.onResume();
    EsAccount localEsAccount = getAccount();
    if ((!this.mViewNavigationRecorded) && (localEsAccount != null))
    {
      InstrumentedActivity.recordViewNavigation(this, localEsAccount, VIEW);
      this.mViewNavigationRecorded = true;
    }
  }

  protected final void putAccountExtra(PreferenceGroup paramPreferenceGroup, EsAccount paramEsAccount)
  {
    int i = 0;
    int j = paramPreferenceGroup.getPreferenceCount();
    if (i < j)
    {
      Preference localPreference = paramPreferenceGroup.getPreference(i);
      Intent localIntent = localPreference.getIntent();
      if (localIntent != null)
      {
        ComponentName localComponentName = localIntent.getComponent();
        if ((localComponentName == null) || (!TextUtils.equals(getPackageName(), localComponentName.getPackageName())))
          break label101;
      }
      label101: for (int k = 1; ; k = 0)
      {
        if (k != 0)
          localIntent.putExtra("account", paramEsAccount);
        if ((localPreference instanceof PreferenceGroup))
          putAccountExtra((PreferenceGroup)localPreference, paramEsAccount);
        i++;
        break;
      }
    }
  }

  public final void startExternalActivity(Intent paramIntent)
  {
    paramIntent.addFlags(524288);
    startActivity(paramIntent);
  }

  protected final class RingtonePreferenceChangeListener
    implements Preference.OnPreferenceChangeListener
  {
    private String mDefaultPath;
    private String mKey;

    public RingtonePreferenceChangeListener(String paramString1, String arg3)
    {
      this.mKey = paramString1;
      Object localObject;
      this.mDefaultPath = localObject;
    }

    public final boolean onPreferenceChange(Preference paramPreference, Object paramObject)
    {
      String str = BaseSettingsActivity.this.getRingtoneName((String)paramObject, this.mKey, this.mDefaultPath);
      if (str != null)
        paramPreference.setSummary(str);
      return true;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.settings.BaseSettingsActivity
 * JD-Core Version:    0.6.2
 */