package com.google.android.apps.plus.settings;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.widget.Toast;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.R.xml;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.NotificationSetting;
import com.google.android.apps.plus.content.NotificationSettingsCategory;
import com.google.android.apps.plus.content.NotificationSettingsData;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.android.apps.plus.service.ServiceResult;
import java.util.ArrayList;

public class NotificationSettingsActivity extends BaseSettingsActivity
{
  private Integer mGetNotificationsRequestId;
  private NotificationSettingsData mNotificationSettings;
  private final EsServiceListener mServiceListener = new EsServiceListener()
  {
    public final void onChangeNotificationsRequestComplete$6a63df5(EsAccount paramAnonymousEsAccount, ServiceResult paramAnonymousServiceResult)
    {
      if ((paramAnonymousEsAccount.equals(NotificationSettingsActivity.this.getAccount())) && (paramAnonymousServiceResult.hasError()))
        Toast.makeText(NotificationSettingsActivity.this, R.string.notification_settings_save_failed, 0).show();
    }

    public final void onGetNotificationSettings$434dcfc8(int paramAnonymousInt, EsAccount paramAnonymousEsAccount, NotificationSettingsData paramAnonymousNotificationSettingsData)
    {
      if ((paramAnonymousEsAccount.equals(NotificationSettingsActivity.this.getAccount())) && (NotificationSettingsActivity.this.mGetNotificationsRequestId != null) && (NotificationSettingsActivity.this.mGetNotificationsRequestId.equals(Integer.valueOf(paramAnonymousInt))))
      {
        NotificationSettingsActivity.access$002(NotificationSettingsActivity.this, null);
        NotificationSettingsActivity.access$102(NotificationSettingsActivity.this, paramAnonymousNotificationSettingsData);
        NotificationSettingsActivity.this.setupPreferences();
        NotificationSettingsActivity.this.dismissDialog(2131361855);
      }
    }
  };

  private void setupPreferences()
  {
    PreferenceScreen localPreferenceScreen1 = getPreferenceScreen();
    if (localPreferenceScreen1 != null)
      localPreferenceScreen1.removeAll();
    addPreferencesFromResource(R.xml.notifications_preferences);
    PreferenceScreen localPreferenceScreen2 = getPreferenceScreen();
    CheckBoxPreference localCheckBoxPreference1 = (CheckBoxPreference)findPreference(getString(R.string.notifications_preference_enabled_key));
    final NotificationSettingsData localNotificationSettingsData;
    int i;
    int j;
    if (getAccount() != null)
    {
      localCheckBoxPreference1.setEnabled(true);
      hookMasterSwitch(null, localCheckBoxPreference1);
      localCheckBoxPreference1.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
      {
        public final boolean onPreferenceChange(Preference paramAnonymousPreference, Object paramAnonymousObject)
        {
          NotificationSettingsActivity localNotificationSettingsActivity = NotificationSettingsActivity.this;
          if (paramAnonymousObject == Boolean.TRUE);
          for (boolean bool = true; ; bool = false)
          {
            localNotificationSettingsActivity.updatedEnabledStates(bool);
            return true;
          }
        }
      });
      String str1 = getString(R.string.notifications_preference_ringtone_key);
      Preference localPreference1 = findPreference(str1);
      String str2 = getString(R.string.notifications_preference_ringtone_default_value);
      String str3 = getRingtoneName(null, str1, str2);
      BaseSettingsActivity.RingtonePreferenceChangeListener localRingtonePreferenceChangeListener = new BaseSettingsActivity.RingtonePreferenceChangeListener(this, str1, str2);
      localPreference1.setOnPreferenceChangeListener(localRingtonePreferenceChangeListener);
      if (str3 != null)
        localPreference1.setSummary(str3);
      if (this.mNotificationSettings != null)
      {
        localNotificationSettingsData = this.mNotificationSettings;
        i = 0;
        j = localNotificationSettingsData.getCategoriesCount();
      }
    }
    else
    {
      while (true)
      {
        if (i >= j)
          break label420;
        NotificationSettingsCategory localNotificationSettingsCategory = localNotificationSettingsData.getCategory(i);
        PreferenceCategory localPreferenceCategory2 = new PreferenceCategory(this);
        localPreferenceCategory2.setTitle(localNotificationSettingsCategory.getDescription());
        localPreferenceCategory2.setOrder(1000 * (i + 2));
        localPreferenceScreen2.addPreference(localPreferenceCategory2);
        int k = 0;
        int m = localNotificationSettingsCategory.getSettingsCount();
        while (true)
          if (k < m)
          {
            final NotificationSetting localNotificationSetting = localNotificationSettingsCategory.getSetting(k);
            CheckBoxPreference localCheckBoxPreference2 = new CheckBoxPreference(this);
            localCheckBoxPreference2.setLayoutResource(R.layout.label_preference);
            localCheckBoxPreference2.setTitle(localNotificationSetting.getDescription());
            localCheckBoxPreference2.setChecked(localNotificationSetting.isEnabled());
            Preference.OnPreferenceChangeListener local3 = new Preference.OnPreferenceChangeListener()
            {
              public final boolean onPreferenceChange(Preference paramAnonymousPreference, Object paramAnonymousObject)
              {
                localNotificationSetting.setEnabled(((Boolean)paramAnonymousObject).booleanValue());
                ArrayList localArrayList1 = new ArrayList(1);
                localArrayList1.add(new NotificationSetting(localNotificationSetting));
                ArrayList localArrayList2 = new ArrayList(1);
                localArrayList2.add(new NotificationSettingsCategory(null, localArrayList1));
                NotificationSettingsData localNotificationSettingsData = new NotificationSettingsData(localNotificationSettingsData.getEmailAddress(), localNotificationSettingsData.getMobileNotificationType(), localArrayList2);
                EsService.changeNotificationSettings(NotificationSettingsActivity.this, NotificationSettingsActivity.this.getAccount(), localNotificationSettingsData);
                return true;
              }
            };
            localCheckBoxPreference2.setOnPreferenceChangeListener(local3);
            localPreferenceCategory2.addPreference(localCheckBoxPreference2);
            k++;
            continue;
            localCheckBoxPreference1.setEnabled(false);
            break;
          }
        i++;
      }
    }
    PreferenceCategory localPreferenceCategory1 = new PreferenceCategory(this);
    localPreferenceCategory1.setTitle(getString(R.string.notifications_preference_no_network_category));
    localPreferenceCategory1.setOrder(2000);
    localPreferenceScreen2.addPreference(localPreferenceCategory1);
    Preference localPreference2 = new Preference(this);
    localPreference2.setLayoutResource(R.layout.label_preference);
    localPreference2.setTitle(getString(R.string.notifications_preference_no_network_alert));
    localPreference2.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public final boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        NotificationSettingsActivity.access$002(NotificationSettingsActivity.this, EsService.getNotificationSettings(NotificationSettingsActivity.this.getBaseContext(), NotificationSettingsActivity.this.getAccount()));
        NotificationSettingsActivity.this.showDialog(2131361855);
        return true;
      }
    });
    localPreferenceCategory1.addPreference(localPreference2);
    label420: updatedEnabledStates(localCheckBoxPreference1.isChecked());
  }

  private void updatedEnabledStates(boolean paramBoolean)
  {
    PreferenceScreen localPreferenceScreen = getPreferenceScreen();
    int i = 0;
    int j = localPreferenceScreen.getPreferenceCount();
    while (i < j)
    {
      localPreferenceScreen.getPreference(i).setEnabled(paramBoolean);
      i++;
    }
    Preference localPreference = findPreference(getString(R.string.notifications_preference_enabled_key));
    if (localPreference != null)
      localPreference.setEnabled(true);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
    {
      if (paramBundle.containsKey("pending_request_id"))
        this.mGetNotificationsRequestId = Integer.valueOf(paramBundle.getInt("pending_request_id"));
      if (paramBundle.containsKey("notification_settings"))
        this.mNotificationSettings = ((NotificationSettingsData)paramBundle.getParcelable("notification_settings"));
    }
  }

  public Dialog onCreateDialog(int paramInt, Bundle paramBundle)
  {
    Object localObject;
    switch (paramInt)
    {
    default:
      localObject = null;
    case 2131361855:
    }
    while (true)
    {
      return localObject;
      localObject = new ProgressDialog(this);
      ((ProgressDialog)localObject).setProgressStyle(0);
      ((ProgressDialog)localObject).setMessage(getString(R.string.loading));
      ((ProgressDialog)localObject).setCancelable(false);
    }
  }

  public void onPause()
  {
    super.onPause();
    EsService.unregisterListener(this.mServiceListener);
  }

  public void onResume()
  {
    super.onResume();
    EsService.registerListener(this.mServiceListener);
    EsAccount localEsAccount = getAccount();
    if (this.mGetNotificationsRequestId == null)
      if (this.mNotificationSettings == null)
      {
        this.mGetNotificationsRequestId = EsService.getNotificationSettings(this, localEsAccount);
        showDialog(2131361855);
      }
    while (true)
    {
      return;
      setupPreferences();
      continue;
      if (!EsService.isRequestPending(this.mGetNotificationsRequestId.intValue()))
      {
        ServiceResult localServiceResult = EsService.removeResult(this.mGetNotificationsRequestId.intValue());
        if ((localServiceResult != null) && (localServiceResult.hasError()))
        {
          this.mGetNotificationsRequestId = null;
          this.mNotificationSettings = null;
          setupPreferences();
          dismissDialog(2131361855);
        }
        else
        {
          this.mGetNotificationsRequestId = EsService.getNotificationSettings(this, localEsAccount);
        }
      }
    }
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mGetNotificationsRequestId != null)
      paramBundle.putInt("pending_request_id", this.mGetNotificationsRequestId.intValue());
    if (this.mNotificationSettings != null)
      paramBundle.putParcelable("notification_settings", this.mNotificationSettings);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.settings.NotificationSettingsActivity
 * JD-Core Version:    0.6.2
 */