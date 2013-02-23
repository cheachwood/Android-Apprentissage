package com.google.android.apps.plus.settings;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.apps.plus.R.bool;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.menu;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.R.xml;
import com.google.android.apps.plus.analytics.EsAnalytics;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAccountsData;
import com.google.android.apps.plus.iu.InstantUploadFacade;
import com.google.android.apps.plus.phone.EsAsyncTaskLoader;
import com.google.android.apps.plus.service.AndroidContactsSync;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.Hangout;
import com.google.android.apps.plus.service.Hangout.SupportStatus;
import com.google.android.apps.plus.util.AccountsUtil;
import com.google.android.apps.plus.util.AndroidUtils;
import com.google.android.apps.plus.util.HelpUrl;

public class SettingsActivity extends BaseSettingsActivity
{
  private static String sContactsStatsSyncKey;
  private static String sContactsSyncKey;
  private static String sHangoutKey;
  private static String sHangoutOnOffKey;
  private static String sInstantUploadKey;
  private static String sMessengerKey;
  private static String sMessengerOnOffKey;
  private static String sNotificationsKey;
  private static String sNotificationsOnOffKey;

  private void setOnOffLabel(LabelPreference paramLabelPreference, boolean paramBoolean)
  {
    Resources localResources = getResources();
    if (paramBoolean)
    {
      paramLabelPreference.setLabel(getString(R.string.preference_on));
      paramLabelPreference.setLabelColor(localResources.getColor(R.color.preference_label_on));
    }
    while (true)
    {
      return;
      paramLabelPreference.setLabel(getString(R.string.preference_off));
      paramLabelPreference.setLabelColor(localResources.getColor(R.color.preference_label_off));
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setTitle(getString(R.string.home_menu_settings));
    Object localObject1 = getIntent().getParcelableExtra("account");
    if (localObject1 == null)
    {
      if ((Build.VERSION.SDK_INT >= 14) && ("android.intent.action.MANAGE_NETWORK_USAGE".equals(getIntent().getAction())))
        localObject1 = EsAccountsData.getActiveAccount(getApplicationContext());
      if (localObject1 == null)
      {
        finish();
        return;
      }
      getIntent().putExtra("account", (EsAccount)localObject1);
    }
    for (Object localObject2 = localObject1; ; localObject2 = localObject1)
    {
      if (sNotificationsKey == null)
      {
        sNotificationsKey = getString(R.string.communication_preference_notifications_key);
        sMessengerKey = getString(R.string.communication_preference_messenger_key);
        sHangoutKey = getString(R.string.communication_preference_hangout_key);
        sInstantUploadKey = getString(R.string.photo_preference_instant_upload_key);
        sContactsSyncKey = getString(R.string.contacts_sync_preference_key);
        sContactsStatsSyncKey = getString(R.string.contacts_stats_sync_preference_key);
        sNotificationsOnOffKey = getString(R.string.notifications_preference_enabled_key);
        sMessengerOnOffKey = getString(R.string.realtimechat_notify_setting_key);
        sHangoutOnOffKey = getString(R.string.hangout_notify_setting_key);
      }
      EsAccount localEsAccount;
      if ((localObject2 instanceof EsAccount))
        localEsAccount = (EsAccount)localObject2;
      while (true)
        if (localEsAccount.isPlusPage())
        {
          addPreferencesFromResource(R.xml.main_preferences_plus_page);
          putAccountExtra(getPreferenceScreen(), localEsAccount);
          break;
          if ((localObject2 instanceof Account))
          {
            localEsAccount = EsService.getActiveAccount(this);
            if (localEsAccount == null)
            {
              Toast.makeText(this, getString(R.string.not_signed_in), 1).show();
              finish();
              break;
            }
            getIntent().putExtra("account", localEsAccount);
            continue;
          }
          finish();
          break;
        }
      addPreferencesFromResource(R.xml.main_preferences);
      addPreferencesFromResource(R.xml.contacts_sync_preferences);
      if (AndroidContactsSync.isAndroidSyncSupported(this))
      {
        CheckBoxPreference localCheckBoxPreference2 = (CheckBoxPreference)findPreference(sContactsSyncKey);
        localCheckBoxPreference2.setChecked(EsAccountsData.isContactsSyncEnabled(this, localEsAccount));
        localCheckBoxPreference2.setOnPreferenceChangeListener(new ContactsSyncPreferenceChangeListener(localEsAccount));
      }
      CheckBoxPreference localCheckBoxPreference1 = (CheckBoxPreference)findPreference(sContactsStatsSyncKey);
      Resources localResources = getResources();
      if (AndroidUtils.hasTelephony(this));
      for (int i = R.string.contacts_stats_sync_preference_enabled_phone_summary; ; i = R.string.contacts_stats_sync_preference_enabled_tablet_summary)
      {
        localCheckBoxPreference1.setSummary(localResources.getString(i));
        localCheckBoxPreference1.setChecked(EsAccountsData.isContactsStatsSyncEnabled(this, localEsAccount));
        localCheckBoxPreference1.setOnPreferenceChangeListener(new ContactsStatsSyncPreferenceChangeListener(localEsAccount));
        break;
      }
    }
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(R.menu.preferences_menu, paramMenu);
    return true;
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    if (paramMenuItem.getItemId() == R.id.menu_help)
      startExternalActivity(new Intent("android.intent.action.VIEW", HelpUrl.getHelpUrl(this, getResources().getString(R.string.url_param_help_settings))));
    for (boolean bool = true; ; bool = super.onOptionsItemSelected(paramMenuItem))
      return bool;
  }

  public void onResume()
  {
    super.onResume();
    boolean bool2;
    LabelPreference localLabelPreference4;
    LabelPreference localLabelPreference3;
    if (!getAccount().isPlusPage())
    {
      boolean bool1 = ContentResolver.getSyncAutomatically(AccountsUtil.newAccount(getAccount().getName()), "com.google.android.apps.plus.iu.EsGoogleIuProvider");
      bool2 = ContentResolver.getMasterSyncAutomatically();
      localLabelPreference4 = (LabelPreference)findPreference(sInstantUploadKey);
      if ((bool2) && (bool1))
      {
        localLabelPreference4.setSummary(null);
        localLabelPreference4.setOnPreferenceClickListener(null);
        new InstantUploadSettingsLoader(this).startLoading();
      }
    }
    else
    {
      SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
      Resources localResources = getResources();
      LabelPreference localLabelPreference1 = (LabelPreference)findPreference(sNotificationsKey);
      if (localLabelPreference1 != null)
        setOnOffLabel(localLabelPreference1, localSharedPreferences.getBoolean(sNotificationsOnOffKey, localResources.getBoolean(R.bool.notifications_preference_enabled_default_value)));
      LabelPreference localLabelPreference2 = (LabelPreference)findPreference(sMessengerKey);
      if (localLabelPreference2 != null)
        setOnOffLabel(localLabelPreference2, localSharedPreferences.getBoolean(sMessengerOnOffKey, localResources.getBoolean(R.bool.realtimechat_notify_setting_default_value)));
      localLabelPreference3 = (LabelPreference)findPreference(sHangoutKey);
      if (localLabelPreference3 != null)
      {
        EsAccount localEsAccount = getAccount();
        if ((localEsAccount == null) || (Hangout.getSupportedStatus(this, localEsAccount) != Hangout.SupportStatus.SUPPORTED))
          break label300;
        setOnOffLabel(localLabelPreference3, localSharedPreferences.getBoolean(sHangoutOnOffKey, localResources.getBoolean(R.bool.hangout_notify_setting_default_value)));
      }
    }
    while (true)
    {
      return;
      if (bool2)
      {
        String str = getString(R.string.es_google_iu_provider);
        localLabelPreference4.setSummary(getString(R.string.photo_sync_disabled_summary, new Object[] { str }));
      }
      while (true)
      {
        localLabelPreference4.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
        {
          public final boolean onPreferenceClick(Preference paramAnonymousPreference)
          {
            SettingsActivity.this.startActivity(new Intent("android.settings.SYNC_SETTINGS"));
            return true;
          }
        });
        setOnOffLabel(localLabelPreference4, false);
        break;
        localLabelPreference4.setSummary(R.string.master_sync_disabled_summary);
      }
      label300: PreferenceScreen localPreferenceScreen = getPreferenceScreen();
      if (localPreferenceScreen != null)
        localPreferenceScreen.removePreference(localLabelPreference3);
    }
  }

  private final class ContactsStatsSyncPreferenceChangeListener
    implements Preference.OnPreferenceChangeListener
  {
    private final EsAccount mAccount;

    ContactsStatsSyncPreferenceChangeListener(EsAccount arg2)
    {
      Object localObject;
      this.mAccount = localObject;
    }

    public final boolean onPreferenceChange(Preference paramPreference, Object paramObject)
    {
      boolean bool = ((Boolean)paramObject).booleanValue();
      SettingsActivity localSettingsActivity = SettingsActivity.this;
      EsAccountsData.saveContactsStatsSyncPreference(localSettingsActivity, this.mAccount, bool);
      EsService.saveLastContactedTimestamp(localSettingsActivity, this.mAccount, -1L);
      EsAnalytics.recordImproveSuggestionsPreferenceChange(localSettingsActivity, this.mAccount, bool, OzViews.GENERAL_SETTINGS);
      if (bool)
        EsService.disableWipeoutStats(localSettingsActivity, this.mAccount);
      while (true)
      {
        return true;
        EsService.enableAndPerformWipeoutStats(localSettingsActivity, this.mAccount);
      }
    }
  }

  private final class ContactsSyncPreferenceChangeListener
    implements Preference.OnPreferenceChangeListener
  {
    private final EsAccount mAccount;

    ContactsSyncPreferenceChangeListener(EsAccount arg2)
    {
      Object localObject;
      this.mAccount = localObject;
    }

    public final boolean onPreferenceChange(Preference paramPreference, Object paramObject)
    {
      boolean bool = ((Boolean)paramObject).booleanValue();
      if (EsAccountsData.isContactsSyncEnabled(SettingsActivity.this, this.mAccount) != bool)
      {
        EsAccountsData.saveContactsSyncPreference(SettingsActivity.this, this.mAccount, bool);
        AndroidContactsSync.requestSync(SettingsActivity.this, true);
      }
      return true;
    }
  }

  private final class InstantUploadSettingsLoader extends EsAsyncTaskLoader<Object>
  {
    public InstantUploadSettingsLoader(Context arg2)
    {
      super();
    }

    public final Object esLoadInBackground()
    {
      final int i = 1;
      ContentResolver localContentResolver = getContext().getContentResolver();
      Uri localUri = InstantUploadFacade.SETTINGS_URI;
      String[] arrayOfString = new String[i];
      arrayOfString[0] = "auto_upload_enabled";
      Cursor localCursor = localContentResolver.query(localUri, arrayOfString, null, null, null);
      if (localCursor != null);
      try
      {
        if (localCursor.moveToFirst())
        {
          if (localCursor.getInt(0) == i)
            SettingsActivity.this.mHandler.post(new Runnable()
            {
              public final void run()
              {
                LabelPreference localLabelPreference = (LabelPreference)SettingsActivity.this.findPreference(SettingsActivity.sInstantUploadKey);
                SettingsActivity.this.setOnOffLabel(localLabelPreference, i);
              }
            });
        }
        else
          return null;
        int j = 0;
      }
      finally
      {
        localCursor.close();
      }
    }

    protected final void onStartLoading()
    {
      forceLoad();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.settings.SettingsActivity
 * JD-Core Version:    0.6.2
 */