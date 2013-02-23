package com.google.android.apps.plus.phone;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.menu;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.api.BirthdayData;
import com.google.android.apps.plus.api.CallToActionData;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAccountsData;
import com.google.android.apps.plus.content.PersonData;
import com.google.android.apps.plus.external.PlatformContractUtils;
import com.google.android.apps.plus.fragments.PostFragment;
import com.google.android.apps.plus.network.ApiaryApiInfo;
import com.google.android.apps.plus.util.ImageUtils;
import com.google.android.apps.plus.util.ImageUtils.InsertCameraPhotoDialogDisplayer;
import com.google.android.apps.plus.util.Property;
import java.util.ArrayList;

public class ShareActivity extends PostActivity
  implements ImageUtils.InsertCameraPhotoDialogDisplayer
{
  private DialogInterface.OnClickListener mDialogListener = new DialogListener((byte)0);
  private ApiaryApiInfo mInfo;
  private DialogInterface.OnClickListener mLocationDialogListener = new LocationDialogListener((byte)0);

  private void copyStringExtraToArgs(String paramString1, String paramString2, Bundle paramBundle)
  {
    String str = getIntent().getStringExtra(paramString1);
    if (str == null)
    {
      CharSequence localCharSequence = getIntent().getCharSequenceExtra(paramString1);
      if (localCharSequence != null)
        str = localCharSequence.toString();
    }
    if (str != null)
      paramBundle.putString(paramString2, str);
  }

  private boolean isThirdPartyPackageSecure()
  {
    Intent localIntent = getIntent();
    String str1 = getCallingPackage();
    boolean bool1 = localIntent.getBooleanExtra("from_signup", false);
    String str2 = localIntent.getStringExtra("calling_package");
    boolean bool2 = TextUtils.isEmpty(str1);
    boolean bool3 = false;
    if (!bool2)
    {
      boolean bool4 = TextUtils.isEmpty(str2);
      bool3 = false;
      if (!bool4)
      {
        boolean bool5 = str1.equals(getPackageName());
        bool3 = false;
        if (bool5)
        {
          bool3 = false;
          if (bool1)
            break label83;
        }
      }
    }
    while (true)
    {
      return bool3;
      label83: bool3 = true;
    }
  }

  protected final Bundle getPostFragmentArguments()
  {
    Bundle localBundle1 = super.getPostFragmentArguments();
    if (localBundle1 == null)
      localBundle1 = null;
    while (true)
    {
      return localBundle1;
      Intent localIntent = getIntent();
      copyStringExtraToArgs("com.google.android.apps.plus.CID", "cid", localBundle1);
      copyStringExtraToArgs("com.google.android.apps.plus.LOCATION_NAME", "location_name", localBundle1);
      copyStringExtraToArgs("com.google.android.apps.plus.EXTERNAL_ID", "external_id", localBundle1);
      copyStringExtraToArgs("com.google.android.apps.plus.FOOTER", "footer", localBundle1);
      copyStringExtraToArgs("com.google.android.apps.plus.LATITUDE", "latitude", localBundle1);
      copyStringExtraToArgs("com.google.android.apps.plus.LONGITUDE", "longitude", localBundle1);
      copyStringExtraToArgs("com.google.android.apps.plus.ADDRESS", "address", localBundle1);
      copyStringExtraToArgs("com.google.android.apps.plus.CONTENT_DEEP_LINK_ID", "content_deep_link_id", localBundle1);
      Bundle localBundle2 = localIntent.getBundleExtra("com.google.android.apps.plus.CONTENT_DEEP_LINK_METADATA");
      if (localBundle2 != null)
        localBundle1.putBundle("content_deep_link_metadata", localBundle2);
      if (getIntent().hasExtra("com.google.android.apps.plus.IS_FROM_PLUSONE"))
        localBundle1.putBoolean("is_from_plusone", getIntent().getBooleanExtra("com.google.android.apps.plus.IS_FROM_PLUSONE", false));
      copyStringExtraToArgs("android.intent.extra.TEXT", "android.intent.extra.TEXT", localBundle1);
      String str1 = localIntent.getDataString();
      if ((str1 != null) && ("com.google.android.apps.plus.SHARE_GOOGLE".equals(localIntent.getAction())))
        localBundle1.putString("url", str1);
      String str2 = localIntent.getAction();
      if ("com.google.android.apps.plus.GOOGLE_BIRTHDAY_POST".equals(str2))
      {
        if ((TextUtils.isEmpty(localIntent.getStringExtra("com.google.android.apps.plus.RECIPIENT_ID"))) || (TextUtils.isEmpty(localIntent.getStringExtra("com.google.android.apps.plus.RECIPIENT_NAME"))) || (localIntent.getIntExtra("com.google.android.apps.plus.BIRTHDAY_YEAR", 0) == 0))
        {
          localBundle1 = null;
        }
        else
        {
          localBundle1.putParcelable("birthday_data", new BirthdayData(localIntent.getStringExtra("com.google.android.apps.plus.RECIPIENT_ID"), localIntent.getStringExtra("com.google.android.apps.plus.RECIPIENT_NAME"), localIntent.getIntExtra("com.google.android.apps.plus.BIRTHDAY_YEAR", 0)));
          localBundle1.putParcelable("audience", new AudienceData(new PersonData(localIntent.getStringExtra("com.google.android.apps.plus.RECIPIENT_ID"), localIntent.getStringExtra("com.google.android.apps.plus.RECIPIENT_NAME"), null)));
        }
      }
      else
      {
        if (("com.google.android.apps.plus.GOOGLE_PLUS_SHARE".equals(str2)) || ("android.intent.action.SEND".equals(str2)))
        {
          copyStringExtraToArgs("com.google.android.apps.plus.CONTENT_DEEP_LINK_ID", "content_deep_link_id", localBundle1);
          copyStringExtraToArgs("com.google.android.apps.plus.CONTENT_URL", "url", localBundle1);
          if ((this.mAccount != null) && (this.mAccount.hasGaiaId()) && (TextUtils.equals(this.mAccount.getGaiaId(), localIntent.getStringExtra("com.google.android.apps.plus.SENDER_ID"))))
          {
            ArrayList localArrayList1 = localIntent.getStringArrayListExtra("com.google.android.apps.plus.RECIPIENT_IDS");
            ArrayList localArrayList2 = localIntent.getStringArrayListExtra("com.google.android.apps.plus.RECIPIENT_DISPLAY_NAMES");
            int i;
            if (localArrayList1 != null)
              i = localArrayList1.size();
            while ((localArrayList2 != null) && (i != 0))
            {
              int j = localArrayList2.size();
              if (i == j)
              {
                ArrayList localArrayList3 = new ArrayList(localArrayList1.size());
                int k = 0;
                while (true)
                  if (k < i)
                  {
                    PersonData localPersonData = new PersonData((String)localArrayList1.get(k), (String)localArrayList2.get(k), null);
                    localArrayList3.add(localPersonData);
                    k++;
                    continue;
                    i = 0;
                    break;
                  }
                AudienceData localAudienceData = new AudienceData(localArrayList3, null);
                localBundle1.putParcelable("audience", localAudienceData);
              }
            }
          }
        }
        if (localIntent.getBooleanExtra("com.google.android.apps.plus.GOOGLE_INTERACTIVE_POST", false))
        {
          String str3 = localIntent.getStringExtra("com.google.android.apps.plus.CONTENT_URL");
          String str4 = localIntent.getStringExtra("com.google.android.apps.plus.CONTENT_DEEP_LINK_ID");
          if ((TextUtils.isEmpty(str3)) && (TextUtils.isEmpty(str4)))
          {
            localBundle1 = null;
          }
          else if (!isThirdPartyPackageSecure())
          {
            localBundle1 = null;
          }
          else
          {
            CallToActionData localCallToActionData = CallToActionData.fromExtras(localIntent.getBundleExtra("com.google.android.apps.plus.CALL_TO_ACTION"));
            if (localCallToActionData == null)
            {
              localBundle1 = null;
            }
            else
            {
              localBundle1.putParcelable("call_to_action", localCallToActionData);
              if (TextUtils.isEmpty(localIntent.getStringExtra("com.google.android.apps.plus.SENDER_ID")))
                localBundle1 = null;
            }
          }
        }
        else
        {
          localBundle1.putSerializable("api_info", this.mInfo);
        }
      }
    }
  }

  protected final CharSequence getTitleButton3Text$9aa72f6()
  {
    return getResources().getText(R.string.post_share_button_text);
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.SHARE;
  }

  protected final int getViewId()
  {
    return R.layout.share_activity;
  }

  public final void invalidateMenu()
  {
    createTitlebarButtons(R.menu.share_menu);
    if (Build.VERSION.SDK_INT >= 11)
      invalidateOptionsMenu();
  }

  public void onCreate(Bundle paramBundle)
  {
    Intent localIntent;
    ApiaryApiInfo localApiaryApiInfo2;
    label96: label228: PackageManager localPackageManager1;
    String str2;
    String str3;
    if (paramBundle == null)
    {
      localIntent = getIntent();
      if ("com.google.android.apps.plus.SHARE_GOOGLE".equals(localIntent.getAction()))
      {
        String str6 = localIntent.getStringExtra("com.google.android.apps.plus.API_KEY");
        String str7 = localIntent.getStringExtra("com.google.android.apps.plus.CLIENT_ID");
        String str8 = localIntent.getStringExtra("com.google.android.apps.plus.VERSION");
        String str9 = Property.PLUS_CLIENTID.get();
        PackageManager localPackageManager2 = getPackageManager();
        String str10 = localIntent.getStringExtra("calling_package");
        boolean bool1 = isThirdPartyPackageSecure();
        localApiaryApiInfo2 = null;
        if (!bool1);
        while (true)
        {
          this.mInfo = localApiaryApiInfo2;
          if (this.mInfo == null)
          {
            PlatformContractUtils.getCallingPackageAnalytics(this.mInfo);
            recordUserAction(getAnalyticsInfo$7d6d37aa(), OzActions.PLATFORM_ERROR_SHARE);
            setResult(0);
            finish();
          }
          super.onCreate(paramBundle);
          showTitlebar(true);
          findViewById(R.id.frame_container).setOnClickListener(new View.OnClickListener()
          {
            public final void onClick(View paramAnonymousView)
            {
              ShareActivity.this.mFragment.onDiscard(true);
            }
          });
          findViewById(R.id.post_container).setOnClickListener(new View.OnClickListener()
          {
            public final void onClick(View paramAnonymousView)
            {
            }
          });
          if ((this.mAccount == null) || (TextUtils.isEmpty(this.mAccount.getDisplayName())))
            break;
          setTitlebarTitle(this.mAccount.getDisplayName());
          String str1 = this.mAccount.getName();
          if (!TextUtils.isEmpty(str1))
            setTitlebarSubtitle(str1);
          return;
          if (localPackageManager2.checkSignatures(getPackageName(), str10) == 0)
            if (("com.android.vending".equals(str10)) || ("com.google.android.music".equals(str10)))
              str9 = "659910861946.apps.googleusercontent.com";
          do
          {
            do
            {
              ApiaryApiInfo localApiaryApiInfo3 = new ApiaryApiInfo(str6, str7, str10, PlatformContractUtils.getCertificate(str10, localPackageManager2), str8);
              localApiaryApiInfo2 = new ApiaryApiInfo(null, str9, str10, PlatformContractUtils.getCertificate(str10, localPackageManager2), str8, localApiaryApiInfo3);
              break;
              boolean bool3 = TextUtils.isEmpty(str6);
              localApiaryApiInfo2 = null;
              if (bool3)
                break;
            }
            while (!TextUtils.isEmpty(str7));
            localApiaryApiInfo2 = null;
            break;
            boolean bool2 = TextUtils.isEmpty(str6);
            localApiaryApiInfo2 = null;
            if (bool2)
              break;
          }
          while (!TextUtils.isEmpty(str7));
          localApiaryApiInfo2 = null;
        }
      }
      localPackageManager1 = getPackageManager();
      str2 = Property.PLUS_CLIENTID.get();
      str3 = localIntent.getStringExtra("android.support.v4.app.EXTRA_CALLING_PACKAGE");
      if (TextUtils.isEmpty(str3))
        str3 = localIntent.getStringExtra("calling_package");
      if (!TextUtils.isEmpty(str3))
        break label531;
    }
    label531: for (String str4 = "com.google.android.apps.social"; ; str4 = str3)
    {
      String str5 = localIntent.getStringExtra("com.google.android.apps.plus.VERSION");
      ApiaryApiInfo localApiaryApiInfo1 = new ApiaryApiInfo(null, str2, str4, PlatformContractUtils.getCertificate(str4, localPackageManager1), str5);
      localApiaryApiInfo2 = new ApiaryApiInfo(null, str2, getPackageName(), PlatformContractUtils.getCertificate(getPackageName(), localPackageManager1), "", localApiaryApiInfo1);
      break;
      this.mInfo = ((ApiaryApiInfo)paramBundle.getSerializable("ShareActivity.mInfo"));
      break label96;
      setTitlebarTitle(getString(R.string.app_name));
      break label228;
    }
  }

  public Dialog onCreateDialog(int paramInt, Bundle paramBundle)
  {
    Object localObject;
    switch (paramInt)
    {
    default:
      localObject = null;
    case 21305:
    case 12763:
    case 28199:
    case 22689:
    case 16542:
    case 29341608:
    case 2131361854:
    case 30875012:
    }
    while (true)
    {
      return localObject;
      AlertDialog.Builder localBuilder6 = new AlertDialog.Builder(this);
      localBuilder6.setMessage(R.string.share_incorrect_account).setNeutralButton(17039370, this.mDialogListener).setCancelable(false);
      localObject = localBuilder6.create();
      continue;
      AlertDialog.Builder localBuilder5 = new AlertDialog.Builder(this);
      localBuilder5.setMessage(R.string.share_connection_error).setPositiveButton(17039370, this.mDialogListener).setCancelable(true);
      localObject = localBuilder5.create();
      continue;
      AlertDialog.Builder localBuilder4 = new AlertDialog.Builder(this);
      localBuilder4.setMessage(R.string.share_preview_error).setPositiveButton(17039370, this.mDialogListener).setCancelable(true);
      localObject = localBuilder4.create();
      continue;
      AlertDialog.Builder localBuilder3 = new AlertDialog.Builder(this);
      localBuilder3.setMessage(R.string.share_preview_post_error).setNeutralButton(17039370, this.mDialogListener).setCancelable(false);
      localObject = localBuilder3.create();
      continue;
      localObject = new ProgressDialog(this);
      ((ProgressDialog)localObject).setMessage(getString(R.string.post_operation_pending));
      ((ProgressDialog)localObject).setProgressStyle(0);
      ((ProgressDialog)localObject).setCancelable(false);
      continue;
      AlertDialog.Builder localBuilder2 = new AlertDialog.Builder(this);
      localBuilder2.setMessage(R.string.location_provider_disabled);
      localBuilder2.setPositiveButton(R.string.yes, this.mLocationDialogListener);
      localBuilder2.setNegativeButton(R.string.no, this.mLocationDialogListener);
      localObject = localBuilder2.create();
      continue;
      localObject = ImageUtils.createInsertCameraPhotoDialog(this);
      continue;
      AlertDialog.Builder localBuilder1 = new AlertDialog.Builder(this);
      localBuilder1.setTitle(R.string.post_location_dialog_title);
      localBuilder1.setMessage(R.string.sharebox_location_dialog_message);
      localBuilder1.setNeutralButton(17039370, this.mLocationDialogListener);
      localBuilder1.setCancelable(false);
      localObject = localBuilder1.create();
    }
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(R.menu.share_menu, paramMenu);
    return true;
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    boolean bool = true;
    int i = paramMenuItem.getItemId();
    if ((i == 16908332) || (i == R.id.menu_discard))
      this.mFragment.onDiscard(bool);
    while (true)
    {
      return bool;
      if (i == R.id.menu_post)
        this.mFragment.post();
      else
        bool = false;
    }
  }

  public boolean onPrepareOptionsMenu(Menu paramMenu)
  {
    paramMenu.findItem(R.id.menu_post).setVisible(false);
    return true;
  }

  protected final void onPrepareTitlebarButtons(Menu paramMenu)
  {
    int i = 0;
    if (i < paramMenu.size())
    {
      MenuItem localMenuItem = paramMenu.getItem(i);
      if (localMenuItem.getItemId() == R.id.menu_post);
      for (boolean bool = true; ; bool = false)
      {
        localMenuItem.setVisible(bool);
        i++;
        break;
      }
    }
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mAccount != null)
      paramBundle.putParcelable("account", this.mAccount);
    if (this.mInfo != null)
      paramBundle.putSerializable("ShareActivity.mInfo", this.mInfo);
  }

  protected final void onTitlebarLabelClick()
  {
    finish();
  }

  protected final void recordLaunchEvent()
  {
    Bundle localBundle;
    if (isFromThirdPartyApp(getIntent()))
    {
      localBundle = new Bundle();
      localBundle.putBoolean("extra_platform_event", true);
    }
    while (true)
    {
      PlatformContractUtils.getCallingPackageAnalytics(this.mInfo);
      recordUserAction(getAnalyticsInfo$7d6d37aa(), OzActions.PLATFORM_OPEN_SHAREBOX, localBundle);
      return;
      localBundle = null;
    }
  }

  protected final void showTitlebar(boolean paramBoolean1, boolean paramBoolean2)
  {
    super.showTitlebar(paramBoolean1, false);
    findViewById(R.id.title_layout).setPadding(getResources().getDimensionPixelOffset(R.dimen.share_title_padding_left), 0, 0, 0);
  }

  private final class DialogListener
    implements DialogInterface.OnClickListener
  {
    private DialogListener()
    {
    }

    public final void onClick(DialogInterface paramDialogInterface, int paramInt)
    {
      switch (paramInt)
      {
      default:
        paramDialogInterface.dismiss();
      case -3:
      }
      while (true)
      {
        return;
        paramDialogInterface.dismiss();
        ShareActivity.this.finish();
      }
    }
  }

  private final class LocationDialogListener
    implements DialogInterface.OnClickListener
  {
    private LocationDialogListener()
    {
    }

    public final void onClick(DialogInterface paramDialogInterface, int paramInt)
    {
      switch (paramInt)
      {
      default:
      case -1:
      case -2:
      case -3:
      }
      while (true)
      {
        return;
        Intent localIntent = Intents.getLocationSettingActivityIntent();
        ShareActivity.this.startActivity(localIntent);
        continue;
        ShareActivity.this.mFragment.setLocationChecked(false);
        continue;
        EsAccountsData.saveLocationDialogSeenPreference(ShareActivity.this, ShareActivity.this.mAccount, true);
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.ShareActivity
 * JD-Core Version:    0.6.2
 */