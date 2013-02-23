package com.google.android.apps.plus.settings;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.util.Log;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.R.xml;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.realtimechat.RealTimeChatService;
import com.google.android.apps.plus.realtimechat.RealTimeChatServiceListener;
import com.google.android.apps.plus.realtimechat.RealTimeChatServiceResult;
import com.google.android.apps.plus.util.EsLog;
import com.google.wireless.realtimechat.proto.Client.BunchServerResponse;
import com.google.wireless.realtimechat.proto.Client.SetAclsResponse;
import com.google.wireless.realtimechat.proto.Data.ResponseStatus;

public class MessengerSettingsActivity extends BaseSettingsActivity
{
  private Integer mAclSummaryToSet;
  private String mAclValueToSet;
  private String mCurrentBackend;
  private Integer mRequestId;
  private final RealTimeChatServiceListener mServiceListener = new RealTimeChatServiceListener()
  {
    public final void onResponseReceived$1587694a(int paramAnonymousInt, RealTimeChatServiceResult paramAnonymousRealTimeChatServiceResult)
    {
      if ((MessengerSettingsActivity.this.mRequestId != null) && (paramAnonymousInt == MessengerSettingsActivity.this.mRequestId.intValue()))
      {
        if (paramAnonymousRealTimeChatServiceResult.getErrorCode() != 1)
          break label44;
        MessengerSettingsActivity.this.processSetAclResult(paramAnonymousRealTimeChatServiceResult.getCommand());
      }
      while (true)
      {
        return;
        label44: if (EsLog.isLoggable("MessengerSettings", 4))
          Log.i("MessengerSettings", "Error setting acl " + paramAnonymousRealTimeChatServiceResult.getErrorCode());
        MessengerSettingsActivity.this.dismissDialog(1);
        MessengerSettingsActivity.this.showDialog(2);
      }
    }
  };
  private Runnable mTimeoutRunnable;

  private void processSetAclResult(Client.BunchServerResponse paramBunchServerResponse)
  {
    this.mRequestId = null;
    if (paramBunchServerResponse.hasSetAclsResponse())
    {
      Client.SetAclsResponse localSetAclsResponse = paramBunchServerResponse.getSetAclsResponse();
      if ((localSetAclsResponse != null) && (localSetAclsResponse.hasStatus()) && (localSetAclsResponse.getStatus() == Data.ResponseStatus.OK))
      {
        ListPreference localListPreference = (ListPreference)findPreference(getString(R.string.realtimechat_acl_key));
        localListPreference.setValue(this.mAclValueToSet);
        localListPreference.setSummary(this.mAclSummaryToSet.intValue());
        SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        localEditor.putString(getString(R.string.realtimechat_acl_key), this.mAclValueToSet);
        localEditor.commit();
        dismissDialog(1);
        this.mHandler.removeCallbacks(this.mTimeoutRunnable);
      }
    }
    while (true)
    {
      return;
      dismissDialog(1);
      showDialog(2);
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
    {
      if (paramBundle.containsKey("request_id"))
        this.mRequestId = Integer.valueOf(paramBundle.getInt("request_id"));
      if (paramBundle.containsKey("acl_value"))
        this.mAclValueToSet = paramBundle.getString("acl_value");
      if (paramBundle.containsKey("acl_summary_string_id"))
        this.mAclSummaryToSet = Integer.valueOf(paramBundle.getInt("acl_summary_string_id"));
    }
    addPreferencesFromResource(R.xml.realtimechat_preferences);
    if (RealTimeChatService.debuggable())
    {
      addPreferencesFromResource(R.xml.realtimechat_development_preferences);
      findPreference(getString(R.string.realtimechat_backend_key)).setOnPreferenceChangeListener(new BackendPreferenceChangeListener((byte)0));
      this.mCurrentBackend = PreferenceManager.getDefaultSharedPreferences(this).getString(getString(R.string.realtimechat_backend_key), getString(R.string.debug_realtimechat_default_backend));
    }
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    String str1 = getString(R.string.realtimechat_acl_key);
    String str2 = localSharedPreferences.getString(str1, getString(R.string.realtimechat_default_acl_key));
    int i = -1;
    if (str2.equals(getString(R.string.key_acl_setting_anyone)))
      i = R.string.realtimechat_acl_subtitle_anyone;
    while (true)
    {
      Preference localPreference1 = findPreference(str1);
      localPreference1.setOnPreferenceChangeListener(new AclPreferenceChangeListener());
      if (i != -1)
        localPreference1.setSummary(i);
      String str3 = getString(R.string.realtimechat_ringtone_setting_key);
      Preference localPreference2 = findPreference(str3);
      String str4 = getString(R.string.notifications_preference_ringtone_default_value);
      String str5 = getRingtoneName(null, str3, str4);
      localPreference2.setOnPreferenceChangeListener(new BaseSettingsActivity.RingtonePreferenceChangeListener(this, str3, str4));
      if (str5 != null)
        localPreference2.setSummary(str5);
      return;
      if (str2.equals(getString(R.string.key_acl_setting_my_circles)))
        i = R.string.realtimechat_acl_subtitle_my_circles;
      else if (str2.equals(getString(R.string.key_acl_setting_extended_circles)))
        i = R.string.realtimechat_acl_subtitle_extended_circles;
    }
  }

  public Dialog onCreateDialog(int paramInt, Bundle paramBundle)
  {
    Object localObject;
    if (paramInt == 1)
    {
      localObject = new ProgressDialog(this);
      ((ProgressDialog)localObject).setTitle(getString(R.string.realtimechat_acl_update_pending_title));
      ((ProgressDialog)localObject).setMessage(getString(R.string.realtimechat_acl_update_pending));
      ((ProgressDialog)localObject).setCancelable(false);
      ((ProgressDialog)localObject).setCanceledOnTouchOutside(false);
    }
    while (true)
    {
      return localObject;
      localObject = null;
      if (paramInt == 2)
      {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
        localBuilder.setTitle(getString(R.string.realtimechat_acl_update_failed_title));
        localBuilder.setMessage(getString(R.string.realtimechat_acl_update_failed));
        localBuilder.setPositiveButton(17039370, null);
        localObject = localBuilder.create();
      }
    }
  }

  public void onPause()
  {
    super.onPause();
    this.mHandler.removeCallbacks(this.mTimeoutRunnable);
    RealTimeChatService.unregisterListener(this.mServiceListener);
  }

  public void onResume()
  {
    super.onResume();
    RealTimeChatService.registerListener(this.mServiceListener);
    if (this.mRequestId != null)
    {
      if (RealTimeChatService.isRequestPending(this.mRequestId.intValue()))
        break label75;
      RealTimeChatServiceResult localRealTimeChatServiceResult = RealTimeChatService.removeResult(this.mRequestId.intValue());
      if ((localRealTimeChatServiceResult == null) || (localRealTimeChatServiceResult.getCommand() == null))
        break label62;
      processSetAclResult(localRealTimeChatServiceResult.getCommand());
    }
    while (true)
    {
      return;
      label62: dismissDialog(1);
      showDialog(2);
      continue;
      label75: this.mHandler.postDelayed(this.mTimeoutRunnable, 10000L);
    }
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mRequestId != null)
      paramBundle.putInt("request_id", this.mRequestId.intValue());
    if (this.mAclValueToSet != null)
      paramBundle.putString("acl_value", this.mAclValueToSet);
    if (this.mAclSummaryToSet != null)
      paramBundle.putInt("acl_summary_string_id", this.mAclSummaryToSet.intValue());
  }

  final class AclPreferenceChangeListener
    implements Preference.OnPreferenceChangeListener
  {
    AclPreferenceChangeListener()
    {
    }

    public final boolean onPreferenceChange(Preference paramPreference, Object paramObject)
    {
      String str;
      int i;
      int j;
      if ((paramObject instanceof String))
      {
        str = (String)paramObject;
        i = -1;
        if (!str.equals(MessengerSettingsActivity.this.getString(R.string.key_acl_setting_anyone)))
          break label182;
        i = 1;
        j = R.string.realtimechat_acl_subtitle_anyone;
        if (i == -1)
          break label245;
        MessengerSettingsActivity.access$002(MessengerSettingsActivity.this, str);
        MessengerSettingsActivity.access$102(MessengerSettingsActivity.this, Integer.valueOf(j));
        if (EsLog.isLoggable("MessengerSettings", 3))
          Log.d("MessengerSettings", "Changing acl to " + paramObject);
        EsAccount localEsAccount = MessengerSettingsActivity.this.getAccount();
        MessengerSettingsActivity.access$202(MessengerSettingsActivity.this, Integer.valueOf(RealTimeChatService.setAcl(MessengerSettingsActivity.this, localEsAccount, i)));
        MessengerSettingsActivity.this.showDialog(1, null);
        MessengerSettingsActivity.access$302(MessengerSettingsActivity.this, new MessengerSettingsActivity.TimeoutRunnable(MessengerSettingsActivity.this));
        MessengerSettingsActivity.this.mHandler.postDelayed(MessengerSettingsActivity.this.mTimeoutRunnable, 10000L);
      }
      while (true)
      {
        return false;
        label182: if (str.equals(MessengerSettingsActivity.this.getString(R.string.key_acl_setting_my_circles)))
        {
          i = 3;
          j = R.string.realtimechat_acl_subtitle_my_circles;
          break;
        }
        boolean bool = str.equals(MessengerSettingsActivity.this.getString(R.string.key_acl_setting_extended_circles));
        j = 0;
        if (!bool)
          break;
        i = 2;
        j = R.string.realtimechat_acl_subtitle_extended_circles;
        break;
        label245: if (EsLog.isLoggable("MessengerSettings", 5))
          Log.w("MessengerSettings", "Invalid ACL value (" + str + ")");
      }
    }
  }

  private final class BackendPreferenceChangeListener
    implements Preference.OnPreferenceChangeListener
  {
    private BackendPreferenceChangeListener()
    {
    }

    public final boolean onPreferenceChange(Preference paramPreference, Object paramObject)
    {
      if (((paramObject instanceof String)) && (!MessengerSettingsActivity.this.mCurrentBackend.equals(paramObject)))
      {
        EsAccount localEsAccount = MessengerSettingsActivity.this.getAccount();
        RealTimeChatService.logOut(MessengerSettingsActivity.this, localEsAccount);
      }
      return true;
    }
  }

  final class TimeoutRunnable
    implements Runnable
  {
    TimeoutRunnable()
    {
    }

    public final void run()
    {
      MessengerSettingsActivity.this.dismissDialog(1);
      MessengerSettingsActivity.this.showDialog(2);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.settings.MessengerSettingsActivity
 * JD-Core Version:    0.6.2
 */