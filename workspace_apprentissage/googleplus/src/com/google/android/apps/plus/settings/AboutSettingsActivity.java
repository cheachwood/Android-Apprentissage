package com.google.android.apps.plus.settings;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.R.xml;
import com.google.android.apps.plus.analytics.EsAnalytics;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.util.EsLog;

public class AboutSettingsActivity extends BaseSettingsActivity
  implements DialogInterface.OnClickListener
{
  private static final Uri PRIVACY = Uri.parse("http://m.google.com/app/plus/serviceurl?type=privacy");
  private static final Uri TERMS = Uri.parse("http://m.google.com/app/plus/serviceurl?type=tos");
  private static String sLicenseKey;
  private static String sNetworkStatsKey;
  private static String sNetworkTransactionsKey;
  private static String sPrivacyKey;
  private static String sRemoveAccountKey;
  private static String sTermsKey;

  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    switch (paramInt)
    {
    default:
    case -1:
    }
    while (true)
    {
      return;
      Intent localIntent = Intents.getHostNavigationActivityIntent(this, getAccount());
      localIntent.putExtra("sign_out", true);
      localIntent.setFlags(67108864);
      startActivity(localIntent);
      finish();
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (sLicenseKey == null)
    {
      sLicenseKey = getString(R.string.license_preference_key);
      sPrivacyKey = getString(R.string.privacy_policy_preference_key);
      sTermsKey = getString(R.string.terms_of_service_preference_key);
      sRemoveAccountKey = getString(R.string.remove_account_preference_key);
      sNetworkTransactionsKey = getString(R.string.network_transactions_preference_key);
      sNetworkStatsKey = getString(R.string.network_stats_preference_key);
    }
    if (EsLog.ENABLE_DOGFOOD_FEATURES)
      addPreferencesFromResource(R.xml.network_stats_preferences);
    addPreferencesFromResource(R.xml.about_preferences);
    try
    {
      PackageInfo localPackageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
      findPreference("build_version").setSummary(localPackageInfo.versionName);
      final EsAccount localEsAccount = getAccount();
      if (EsLog.ENABLE_DOGFOOD_FEATURES)
      {
        findPreference(sNetworkTransactionsKey).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
        {
          public final boolean onPreferenceClick(Preference paramAnonymousPreference)
          {
            Intent localIntent = Intents.getNetworkRequestsIntent(AboutSettingsActivity.this, localEsAccount);
            AboutSettingsActivity.this.startActivity(localIntent);
            return true;
          }
        });
        findPreference(sNetworkStatsKey).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
        {
          public final boolean onPreferenceClick(Preference paramAnonymousPreference)
          {
            Intent localIntent = Intents.getNetworkStatisticsIntent(AboutSettingsActivity.this, localEsAccount);
            AboutSettingsActivity.this.startActivity(localIntent);
            return true;
          }
        });
      }
      findPreference(sLicenseKey).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
      {
        public final boolean onPreferenceClick(Preference paramAnonymousPreference)
        {
          Intent localIntent = Intents.getLicenseActivityIntent(AboutSettingsActivity.this);
          AboutSettingsActivity.this.startActivity(localIntent);
          return true;
        }
      });
      findPreference(sPrivacyKey).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
      {
        public final boolean onPreferenceClick(Preference paramAnonymousPreference)
        {
          AboutSettingsActivity.this.startExternalActivity(new Intent("android.intent.action.VIEW", AboutSettingsActivity.PRIVACY));
          return true;
        }
      });
      findPreference(sTermsKey).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
      {
        public final boolean onPreferenceClick(Preference paramAnonymousPreference)
        {
          EsAccount localEsAccount = AboutSettingsActivity.this.getAccount();
          if (localEsAccount != null)
          {
            Context localContext = AboutSettingsActivity.this.getBaseContext();
            OzViews localOzViews = OzViews.getViewForLogging(localContext);
            EsAnalytics.recordActionEvent(localContext, localEsAccount, OzActions.SETTINGS_TOS, localOzViews);
          }
          AboutSettingsActivity.this.startExternalActivity(new Intent("android.intent.action.VIEW", AboutSettingsActivity.TERMS));
          return true;
        }
      });
      findPreference(sRemoveAccountKey).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
      {
        public final boolean onPreferenceClick(Preference paramAnonymousPreference)
        {
          AboutSettingsActivity.this.showDialog(0);
          return true;
        }
      });
      return;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      while (true)
        findPreference("build_version").setSummary("?");
    }
  }

  public Dialog onCreateDialog(int paramInt, Bundle paramBundle)
  {
    switch (paramInt)
    {
    default:
    case 0:
    }
    AlertDialog.Builder localBuilder;
    for (Object localObject = null; ; localObject = localBuilder.create())
    {
      return localObject;
      localBuilder = new AlertDialog.Builder(this);
      localBuilder.setTitle(R.string.preferences_remove_account_title);
      localBuilder.setMessage(R.string.preferences_remove_account_dialog_message);
      localBuilder.setPositiveButton(R.string.ok, this);
      localBuilder.setNegativeButton(R.string.cancel, this);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.settings.AboutSettingsActivity
 * JD-Core Version:    0.6.2
 */