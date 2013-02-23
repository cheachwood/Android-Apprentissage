package com.google.android.apps.plus.phone;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.EsAnalytics;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAccountsData;
import com.google.android.apps.plus.external.PlatformContractUtils;
import com.google.android.apps.plus.fragments.EsFragmentActivity;
import com.google.android.apps.plus.fragments.PlusOneFragment;
import com.google.android.apps.plus.network.ApiaryApiInfo;
import com.google.android.apps.plus.util.Property;

public class PlusOneActivity extends EsFragmentActivity
  implements DialogInterface.OnClickListener
{
  private EsAccount mAccount;
  private ApiaryApiInfo mApiInfo;
  private PlusOneFragment mFragment;
  private boolean mInsert;

  private void recordErrorAndFinish()
  {
    EsAccount localEsAccount = this.mAccount;
    PlatformContractUtils.getCallingPackageAnalytics(this.mApiInfo);
    EsAnalytics.recordEvent(this, localEsAccount, getAnalyticsInfo$7d6d37aa(), OzActions.PLATFORM_ERROR_PLUSONE);
    finish();
  }

  private void recordExitedAction()
  {
    if (this.mInsert)
      recordUserAction(OzActions.PLATFORM_PLUSONE_CONFIRMED);
    while (true)
    {
      return;
      recordUserAction(OzActions.PLATFORM_UNDO_PLUSONE_CANCELED);
    }
  }

  protected final EsAccount getAccount()
  {
    return this.mAccount;
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.PLUSONE;
  }

  public void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    ProgressBar localProgressBar = (ProgressBar)findViewById(R.id.progress_spinner);
    this.mFragment.setProgressBar(localProgressBar);
  }

  public void onBackPressed()
  {
    recordExitedAction();
    super.onBackPressed();
  }

  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    switch (paramInt)
    {
    default:
    case -3:
    }
    while (true)
    {
      return;
      setResult(0);
      finish();
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.plus_one_activity);
    showTitlebar(false);
    setTitlebarTitle(getString(R.string.app_name));
    Intent localIntent = getIntent();
    Bundle localBundle = new Bundle();
    this.mAccount = EsAccountsData.getActiveAccount(this);
    if (SignOnActivity.finishIfNoAccount(this, this.mAccount));
    String str10;
    String str11;
    String str12;
    while (true)
    {
      return;
      localBundle.putParcelable("PlusOneFragment#mAccount", this.mAccount);
      String str1 = getCallingPackage();
      boolean bool1 = localIntent.getBooleanExtra("from_signup", false);
      String str2 = localIntent.getStringExtra("calling_package");
      String str3 = Property.PLUS_CLIENTID.get();
      String str4 = getPackageName();
      PackageManager localPackageManager = getPackageManager();
      String str5 = PlatformContractUtils.getCertificate(str2, localPackageManager);
      String str6 = PlatformContractUtils.getCertificate(str4, localPackageManager);
      String str7 = localIntent.getStringExtra("com.google.circles.platform.intent.extra.APIKEY");
      String str8 = localIntent.getStringExtra("com.google.circles.platform.intent.extra.CLIENTID");
      String str9 = localIntent.getStringExtra("com.google.circles.platform.intent.extra.APIVERSION");
      this.mApiInfo = new ApiaryApiInfo(null, str3, str4, str6, str9, new ApiaryApiInfo(str7, str8, str2, str5, str9));
      if (paramBundle == null)
      {
        PlatformContractUtils.getCallingPackageAnalytics(this.mApiInfo);
        recordUserAction(getAnalyticsInfo$7d6d37aa(), OzActions.PLATFORM_CLICKED_PLUSONE);
      }
      if ((!bool1) || (!getPackageName().equals(str1)) || (TextUtils.isEmpty(str2)))
      {
        recordErrorAndFinish();
      }
      else
      {
        ApiaryApiInfo localApiaryApiInfo = this.mApiInfo.getSourceInfo();
        if ((localApiaryApiInfo == null) || (TextUtils.isEmpty(localApiaryApiInfo.getApiKey())) || (TextUtils.isEmpty(localApiaryApiInfo.getCertificate())) || (TextUtils.isEmpty(localApiaryApiInfo.getClientId())) || (TextUtils.isEmpty(localApiaryApiInfo.getSdkVersion())) || (TextUtils.isEmpty(localApiaryApiInfo.getPackageName())))
        {
          recordErrorAndFinish();
        }
        else
        {
          localBundle.putSerializable("PlusOneFragment#mApiaryApiInfo", this.mApiInfo);
          str10 = localIntent.getStringExtra("com.google.circles.platform.intent.extra.TOKEN");
          str11 = localIntent.getStringExtra("com.google.circles.platform.intent.extra.ENTITY");
          str12 = localIntent.getStringExtra("com.google.circles.platform.intent.extra.ACTION");
          if ((!TextUtils.isEmpty(str10)) && (!TextUtils.isEmpty(str11)) && (!TextUtils.isEmpty(str12)))
            break;
          recordErrorAndFinish();
        }
      }
    }
    if (!"delete".equals(str12));
    for (boolean bool2 = true; ; bool2 = false)
    {
      this.mInsert = bool2;
      localBundle.putString("PlusOneFragment#mToken", str10);
      localBundle.putString("PlusOneFragment#mUrl", str11);
      localBundle.putBoolean("PlusOneFragment#mInsert", this.mInsert);
      this.mFragment = ((PlusOneFragment)getSupportFragmentManager().findFragmentByTag("PlusOneActivity#Fragment"));
      if (this.mFragment == null)
      {
        this.mFragment = new PlusOneFragment();
        this.mFragment.setArguments(localBundle);
        FragmentTransaction localFragmentTransaction = getSupportFragmentManager().beginTransaction();
        localFragmentTransaction.add(R.id.plusone_container, this.mFragment, "PlusOneActivity#Fragment");
        localFragmentTransaction.commit();
      }
      findViewById(R.id.frame_container).setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          PlusOneActivity.this.recordExitedAction();
          PlusOneActivity localPlusOneActivity = PlusOneActivity.this;
          if (PlusOneActivity.this.mInsert);
          for (int i = -1; ; i = 0)
          {
            localPlusOneActivity.setResult(i);
            PlusOneActivity.this.finish();
            return;
          }
        }
      });
      findViewById(R.id.plusone_container).setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
        }
      });
      break;
    }
  }

  public Dialog onCreateDialog(int paramInt, Bundle paramBundle)
  {
    switch (paramInt)
    {
    default:
    case 1:
    }
    AlertDialog.Builder localBuilder;
    for (Object localObject = null; ; localObject = localBuilder.create())
    {
      return localObject;
      localBuilder = new AlertDialog.Builder(this);
      localBuilder.setMessage(R.string.plusone_post_error).setNeutralButton(17039370, this).setCancelable(false);
    }
  }

  protected void onResume()
  {
    super.onResume();
    if (SignOnActivity.finishIfNoAccount(this, this.mAccount));
  }

  protected final void showTitlebar(boolean paramBoolean1, boolean paramBoolean2)
  {
    super.showTitlebar(paramBoolean1, paramBoolean2);
    findViewById(R.id.title_layout).setPadding(getResources().getDimensionPixelOffset(R.dimen.plus_one_title_padding_left), 0, 0, 0);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.PlusOneActivity
 * JD-Core Version:    0.6.2
 */