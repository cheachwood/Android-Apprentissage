package com.google.android.apps.plus.phone;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;
import com.google.android.apps.plus.R.plurals;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.EsAnalytics;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.AccountSettingsData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.fragments.AlertFragmentDialog;
import com.google.android.apps.plus.fragments.AlertFragmentDialog.AlertDialogListener;
import com.google.android.apps.plus.fragments.ProgressFragmentDialog;
import com.google.android.apps.plus.oob.OutOfBoxResponseParcelable;
import com.google.android.apps.plus.realtimechat.RealTimeChatService;
import com.google.android.apps.plus.service.EsService;
import com.google.api.services.plusi.model.MobileOutOfBoxResponse;

public final class SignOnManager
  implements AlertFragmentDialog.AlertDialogListener
{
  private EsAccount mAccount;
  private final FragmentActivity mActivity;
  private final FragmentManager mFragmentManager;
  private Intent mIntent;
  private boolean mIsResumed;
  private EsAccount mOobAccount;
  private EsAccount mResultAccount;

  public SignOnManager(FragmentActivity paramFragmentActivity)
  {
    this.mActivity = paramFragmentActivity;
    this.mFragmentManager = this.mActivity.getSupportFragmentManager();
  }

  private void doSignOut(boolean paramBoolean)
  {
    OzActions localOzActions = OzActions.SETTINGS_SIGNOUT;
    if (this.mAccount != null)
    {
      OzViews localOzViews = OzViews.getViewForLogging(this.mActivity);
      EsAnalytics.recordActionEvent(this.mActivity, this.mAccount, localOzActions, localOzViews);
    }
    EsService.removeAccount(this.mActivity, this.mAccount);
    if (this.mAccount != null)
      RealTimeChatService.allowDisconnect(this.mActivity, this.mAccount);
    this.mAccount = null;
    if (!paramBoolean)
    {
      Intent localIntent = (Intent)this.mIntent.getParcelableExtra("intent");
      this.mActivity.startActivity(Intents.getAccountsActivityIntent(this.mActivity, localIntent));
      this.mActivity.finish();
    }
  }

  // ERROR //
  private int getPendingInstantUploadCount()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 25	com/google/android/apps/plus/phone/SignOnManager:mActivity	Landroid/support/v4/app/FragmentActivity;
    //   4: invokevirtual 100	android/support/v4/app/FragmentActivity:getContentResolver	()Landroid/content/ContentResolver;
    //   7: astore_1
    //   8: aload_1
    //   9: getstatic 106	com/google/android/apps/plus/iu/InstantUploadFacade:INSTANT_UPLOAD_URI	Landroid/net/Uri;
    //   12: aconst_null
    //   13: aconst_null
    //   14: aconst_null
    //   15: aconst_null
    //   16: invokevirtual 112	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   19: astore_2
    //   20: iconst_0
    //   21: istore_3
    //   22: aload_2
    //   23: ifnull +30 -> 53
    //   26: aload_2
    //   27: invokeinterface 118 1 0
    //   32: istore 13
    //   34: iconst_0
    //   35: istore_3
    //   36: iload 13
    //   38: ifeq +15 -> 53
    //   41: aload_2
    //   42: iconst_0
    //   43: invokeinterface 122 2 0
    //   48: istore 14
    //   50: iload 14
    //   52: istore_3
    //   53: aload_2
    //   54: ifnull +9 -> 63
    //   57: aload_2
    //   58: invokeinterface 125 1 0
    //   63: getstatic 128	com/google/android/apps/plus/iu/InstantUploadFacade:UPLOAD_ALL_URI	Landroid/net/Uri;
    //   66: invokevirtual 134	android/net/Uri:buildUpon	()Landroid/net/Uri$Builder;
    //   69: astore 4
    //   71: aload 4
    //   73: ldc 136
    //   75: aload_0
    //   76: getfield 49	com/google/android/apps/plus/phone/SignOnManager:mAccount	Lcom/google/android/apps/plus/content/EsAccount;
    //   79: invokevirtual 142	com/google/android/apps/plus/content/EsAccount:getName	()Ljava/lang/String;
    //   82: invokevirtual 148	android/net/Uri$Builder:appendQueryParameter	(Ljava/lang/String;Ljava/lang/String;)Landroid/net/Uri$Builder;
    //   85: pop
    //   86: aload_1
    //   87: aload 4
    //   89: invokevirtual 152	android/net/Uri$Builder:build	()Landroid/net/Uri;
    //   92: aconst_null
    //   93: aconst_null
    //   94: aconst_null
    //   95: aconst_null
    //   96: invokevirtual 112	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   99: astore 6
    //   101: iconst_0
    //   102: istore 7
    //   104: iconst_0
    //   105: istore 8
    //   107: aload 6
    //   109: ifnull +47 -> 156
    //   112: aload 6
    //   114: invokeinterface 118 1 0
    //   119: istore 10
    //   121: iconst_0
    //   122: istore 7
    //   124: iconst_0
    //   125: istore 8
    //   127: iload 10
    //   129: ifeq +27 -> 156
    //   132: aload 6
    //   134: iconst_1
    //   135: invokeinterface 122 2 0
    //   140: istore 8
    //   142: aload 6
    //   144: iconst_2
    //   145: invokeinterface 122 2 0
    //   150: istore 11
    //   152: iload 11
    //   154: istore 7
    //   156: aload 6
    //   158: ifnull +10 -> 168
    //   161: aload 6
    //   163: invokeinterface 125 1 0
    //   168: iload_3
    //   169: iload 7
    //   171: iload 8
    //   173: isub
    //   174: iadd
    //   175: ireturn
    //   176: astore 12
    //   178: aload_2
    //   179: ifnull +9 -> 188
    //   182: aload_2
    //   183: invokeinterface 125 1 0
    //   188: aload 12
    //   190: athrow
    //   191: astore 9
    //   193: aload 6
    //   195: ifnull +10 -> 205
    //   198: aload 6
    //   200: invokeinterface 125 1 0
    //   205: aload 9
    //   207: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   26	50	176	finally
    //   112	152	191	finally
  }

  private void setAccount(EsAccount paramEsAccount)
  {
    if ((this.mIsResumed) && (this.mAccount != null) && (this.mAccount != paramEsAccount))
      RealTimeChatService.allowDisconnect(this.mActivity, this.mAccount);
    this.mAccount = paramEsAccount;
  }

  private void switchAccounts()
  {
    if (this.mAccount != null)
    {
      RealTimeChatService.allowDisconnect(this.mActivity, this.mAccount);
      EsService.removeAccount(this.mActivity, this.mAccount);
      this.mAccount = null;
    }
    Intent localIntent = (Intent)this.mIntent.getParcelableExtra("intent");
    this.mActivity.startActivity(Intents.getAccountsActivityIntent(this.mActivity, localIntent));
    this.mActivity.finish();
  }

  protected final void continueSignOut(int paramInt, boolean paramBoolean)
  {
    DialogFragment localDialogFragment = (DialogFragment)this.mFragmentManager.findFragmentByTag("SignOnManager.progress_dialog");
    if (localDialogFragment != null);
    try
    {
      localDialogFragment.dismiss();
      label21: if ((!paramBoolean) && (paramInt > 0))
      {
        Resources localResources = this.mActivity.getResources();
        int i = R.plurals.sign_out_message;
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(paramInt);
        String str = localResources.getQuantityString(i, paramInt, arrayOfObject);
        AlertFragmentDialog localAlertFragmentDialog = AlertFragmentDialog.newInstance(this.mActivity.getString(R.string.sign_out_title), str, this.mActivity.getString(R.string.ok), this.mActivity.getString(R.string.cancel));
        localAlertFragmentDialog.setListener(this);
        Bundle localBundle = localAlertFragmentDialog.getArguments();
        if (localBundle == null)
          localBundle = new Bundle();
        localBundle.putBoolean("downgrade_account", paramBoolean);
        localAlertFragmentDialog.setArguments(localBundle);
        localAlertFragmentDialog.show(this.mFragmentManager, "SignOnManager.confirm_signoff");
      }
      while (true)
      {
        return;
        doSignOut(paramBoolean);
      }
    }
    catch (IllegalStateException localIllegalStateException)
    {
      break label21;
    }
  }

  public final EsAccount getAccount()
  {
    return this.mAccount;
  }

  public final boolean isSignedIn()
  {
    if (this.mAccount != null);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean onActivityResult$6eb84b56(int paramInt1, int paramInt2)
  {
    boolean bool;
    switch (paramInt1)
    {
    default:
      bool = false;
      return bool;
    case 1023:
    }
    this.mOobAccount = null;
    if (paramInt2 == -1)
    {
      Intent localIntent = (Intent)this.mIntent.getParcelableExtra("intent");
      if (localIntent != null)
      {
        this.mActivity.startActivity(localIntent);
        this.mActivity.finish();
      }
    }
    while (true)
    {
      bool = true;
      break;
      this.mResultAccount = EsService.getActiveAccount(this.mActivity);
      continue;
      switchAccounts();
    }
  }

  public final void onCreate(Bundle paramBundle, Intent paramIntent)
  {
    this.mIntent = paramIntent;
    EsAccount localEsAccount = EsService.getActiveAccount(this.mActivity);
    if (localEsAccount == null)
      switchAccounts();
    while (true)
    {
      return;
      if ((paramBundle == null) && (this.mIntent.getBooleanExtra("run_oob", false)))
      {
        Intent localIntent1 = (Intent)this.mIntent.getParcelableExtra("intent");
        OutOfBoxResponseParcelable localOutOfBoxResponseParcelable = (OutOfBoxResponseParcelable)this.mIntent.getParcelableExtra("network_oob");
        AccountSettingsData localAccountSettingsData = (AccountSettingsData)this.mIntent.getParcelableExtra("plus_pages");
        FragmentActivity localFragmentActivity = this.mActivity;
        if (localOutOfBoxResponseParcelable != null);
        for (MobileOutOfBoxResponse localMobileOutOfBoxResponse = localOutOfBoxResponseParcelable.getResponse(); ; localMobileOutOfBoxResponse = null)
        {
          Intent localIntent2 = Intents.getOobIntent(localFragmentActivity, localEsAccount, localMobileOutOfBoxResponse, localAccountSettingsData, null);
          if (localIntent2 == null)
            break label146;
          this.mOobAccount = localEsAccount;
          this.mActivity.startActivityForResult(localIntent2, 1023);
          break;
        }
        label146: setAccount(localEsAccount);
        if (localIntent1 != null)
        {
          this.mActivity.startActivity(localIntent1);
          this.mActivity.finish();
        }
      }
      else if (!localEsAccount.hasGaiaId())
      {
        switchAccounts();
      }
      else
      {
        setAccount(localEsAccount);
        if ((localEsAccount.isPlusPage()) && (paramBundle == null))
        {
          Resources localResources = this.mActivity.getResources();
          int i = R.string.plus_page_reminder;
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = localEsAccount.getDisplayName();
          String str = localResources.getString(i, arrayOfObject);
          Toast.makeText(this.mActivity, str, 1).show();
        }
      }
    }
  }

  public final void onDialogCanceled$20f9a4b7(String paramString)
  {
  }

  public final void onDialogListClick$12e92030(int paramInt, Bundle paramBundle)
  {
  }

  public final void onDialogNegativeClick$20f9a4b7(String paramString)
  {
  }

  public final void onDialogPositiveClick(Bundle paramBundle, String paramString)
  {
    if (paramBundle != null)
      doSignOut(paramBundle.getBoolean("downgrade_account", false));
    while (true)
    {
      return;
      doSignOut(false);
    }
  }

  public final void onPause()
  {
    this.mIsResumed = false;
  }

  public final boolean onResume()
  {
    AlertFragmentDialog localAlertFragmentDialog = (AlertFragmentDialog)this.mFragmentManager.findFragmentByTag("SignOnManager.confirm_signoff");
    if (localAlertFragmentDialog != null)
      localAlertFragmentDialog.setListener(this);
    boolean bool;
    if (this.mResultAccount != null)
    {
      setAccount(this.mResultAccount);
      this.mResultAccount = null;
      bool = true;
      if (this.mAccount != null)
        break label77;
      if (this.mOobAccount == null)
        break label70;
      this.mIsResumed = true;
    }
    while (true)
    {
      return bool;
      bool = false;
      break;
      label70: switchAccounts();
      continue;
      label77: if (!this.mAccount.equals(EsService.getActiveAccount(this.mActivity)))
      {
        switchAccounts();
      }
      else
      {
        this.mIsResumed = true;
        if (!this.mAccount.isPlusPage())
          RealTimeChatService.initiateConnection(this.mActivity, this.mAccount);
      }
    }
  }

  public final void signOut(final boolean paramBoolean)
  {
    ProgressFragmentDialog.newInstance(null, this.mActivity.getString(R.string.sign_out_pending)).show(this.mFragmentManager, "SignOnManager.progress_dialog");
    new AsyncTask()
    {
    }
    .execute(new Void[0]);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.SignOnManager
 * JD-Core Version:    0.6.2
 */