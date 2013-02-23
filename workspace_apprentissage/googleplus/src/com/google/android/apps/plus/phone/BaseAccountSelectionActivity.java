package com.google.android.apps.plus.phone;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.TextView;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.api.OzServerException;
import com.google.android.apps.plus.content.AccountSettingsData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAccountsData;
import com.google.android.apps.plus.fragments.AccountsListFragment;
import com.google.android.apps.plus.fragments.EsFragmentActivity;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.android.apps.plus.service.ServiceResult;
import com.google.api.services.plusi.model.MobileOutOfBoxResponse;

public abstract class BaseAccountSelectionActivity extends EsFragmentActivity
  implements DialogInterface.OnCancelListener, DialogInterface.OnClickListener
{
  private AccountsAdder mAccountsAdder = new AccountsAdder()
  {
    public final void addAccount(String paramAnonymousString)
    {
      BaseAccountSelectionActivity.access$102(BaseAccountSelectionActivity.this, Integer.valueOf(EsService.addAccount(BaseAccountSelectionActivity.this, new EsAccount(paramAnonymousString, null, null, false, false, -1), BaseAccountSelectionActivity.this.getUpgradeOrigin())));
      BaseAccountSelectionActivity.this.showDialog(10);
    }
  };
  private AccountsListFragment mAccountsListFragment;
  private Integer mAddAccountPendingRequestId;
  private final EsServiceListener mServiceListener = new ServiceListener((byte)0);
  private boolean mShowOnAttach;
  private Integer mUpdateAccountIdPendingRequestId;

  private void chooseAccountManually()
  {
    Bundle localBundle;
    if (Build.VERSION.SDK_INT >= 14)
    {
      localBundle = new Bundle();
      localBundle.putBoolean("allowSkip", false);
      localBundle.putCharSequence("introMessage", getString(R.string.create_account_prompt));
    }
    for (Intent localIntent = AccountManager.newChooseAccountIntent(null, null, new String[] { "com.google" }, true, null, "webupdates", null, localBundle); ; localIntent = null)
    {
      startActivityForResult(localIntent, 0);
      return;
    }
  }

  private void handleError(ServiceResult paramServiceResult)
  {
    Exception localException = paramServiceResult.getException();
    Bundle localBundle = new Bundle();
    if ((localException instanceof OzServerException))
      switch (((OzServerException)localException).getErrorCode())
      {
      default:
        localBundle.putString("error_title", getString(R.string.signup_title_no_connection));
        localBundle.putString("error_message", getString(R.string.signup_error_network));
        showDialog(0, localBundle);
      case 10:
      case 1:
      case 12:
      case 14:
      case 15:
      }
    while (true)
    {
      return;
      localBundle.putString("error_message", getString(R.string.signup_required_update_available));
      showDialog(1, localBundle);
      continue;
      localBundle.putString("error_message", getString(R.string.signup_authentication_error));
      showDialog(2, localBundle);
      continue;
      localBundle.putString("error_message", getString(R.string.signup_profile_error));
      showDialog(4, localBundle);
      continue;
      localBundle.putString("error_title", getString(R.string.signup_title_mobile_not_available));
      localBundle.putString("error_message", getString(R.string.signup_text_mobile_not_available));
      showDialog(3, localBundle);
      continue;
      localBundle.putString("error_title", getString(R.string.signup_title_no_connection));
      localBundle.putString("error_message", getString(R.string.signup_error_network));
      showDialog(0, localBundle);
    }
  }

  private void handleResponse(int paramInt, EsAccount paramEsAccount)
  {
    if (paramEsAccount == null);
    while (true)
    {
      return;
      onAccountSet(EsService.removeIncompleteOutOfBoxResponse(paramInt), paramEsAccount, EsService.removeAccountSettingsResponse(paramInt));
    }
  }

  protected final EsAccount getAccount()
  {
    return null;
  }

  protected String getUpgradeOrigin()
  {
    return "DEFAULT";
  }

  public OzViews getViewForLogging()
  {
    return OzViews.UNKNOWN;
  }

  public final void handleUpgradeFailure()
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("error_title", getString(R.string.signup_title_no_connection));
    localBundle.putString("error_message", getString(R.string.signup_error_network));
    showDialog(5, localBundle);
  }

  public final void handleUpgradeSuccess(EsAccount paramEsAccount)
  {
    Intent localIntent = (Intent)getIntent().getParcelableExtra("intent");
    if (localIntent == null)
      localIntent = Intents.getStreamActivityIntent(this, paramEsAccount);
    startActivity(localIntent);
    finish();
  }

  protected abstract void onAccountSet(MobileOutOfBoxResponse paramMobileOutOfBoxResponse, EsAccount paramEsAccount, AccountSettingsData paramAccountSettingsData);

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    switch (paramInt1)
    {
    default:
    case 0:
    }
    while (true)
    {
      return;
      if ((paramInt2 == -1) && (paramIntent != null))
      {
        String str = paramIntent.getStringExtra("authAccount");
        this.mAccountsAdder.addAccount(str);
      }
      else
      {
        setResult(0);
        finish();
      }
    }
  }

  public final void onAttachFragment(Fragment paramFragment)
  {
    super.onAttachFragment(paramFragment);
    if ((paramFragment instanceof AccountsListFragment))
    {
      this.mAccountsListFragment = ((AccountsListFragment)paramFragment);
      this.mAccountsListFragment.setAccountsAdder(this.mAccountsAdder);
      if (this.mShowOnAttach)
      {
        this.mAccountsListFragment.showList();
        this.mShowOnAttach = false;
      }
    }
  }

  public void onCancel(DialogInterface paramDialogInterface)
  {
    showAccountList();
  }

  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    showAccountList();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
    {
      if (!paramBundle.containsKey("aa_reqid"))
        break label58;
      this.mAddAccountPendingRequestId = Integer.valueOf(paramBundle.getInt("aa_reqid"));
      if (!paramBundle.containsKey("ua_reqid"))
        break label66;
    }
    label58: label66: for (this.mUpdateAccountIdPendingRequestId = Integer.valueOf(paramBundle.getInt("ua_reqid")); ; this.mUpdateAccountIdPendingRequestId = null)
    {
      return;
      this.mAddAccountPendingRequestId = null;
      break;
    }
  }

  public Dialog onCreateDialog(int paramInt, Bundle paramBundle)
  {
    String str1;
    String str2;
    label13: Object localObject;
    if (paramBundle == null)
    {
      str1 = null;
      if (paramBundle != null)
        break label93;
      str2 = null;
      localObject = null;
      switch (paramInt)
      {
      case 6:
      case 7:
      case 8:
      case 9:
      default:
      case 0:
      case 1:
      case 2:
      case 3:
      case 4:
      case 10:
      case 11:
      case 5:
      }
    }
    while (true)
    {
      return localObject;
      str1 = paramBundle.getString("error_title");
      break;
      label93: str2 = paramBundle.getString("error_message");
      break label13;
      AlertDialog.Builder localBuilder2 = new AlertDialog.Builder(this);
      localBuilder2.setTitle(str1);
      localBuilder2.setMessage(str2);
      localBuilder2.setNeutralButton(R.string.ok, this);
      localBuilder2.setOnCancelListener(this);
      localObject = localBuilder2.create();
      continue;
      localObject = new ProgressDialog(this);
      ((ProgressDialog)localObject).setMessage(getString(R.string.signup_signing_in));
      ((ProgressDialog)localObject).setProgressStyle(0);
      ((ProgressDialog)localObject).setCancelable(false);
      continue;
      localObject = new ProgressDialog(this);
      ((ProgressDialog)localObject).setMessage(getString(R.string.signup_upgrading));
      ((ProgressDialog)localObject).setProgressStyle(0);
      ((ProgressDialog)localObject).setCancelable(false);
      continue;
      AlertDialog.Builder localBuilder1 = new AlertDialog.Builder(this);
      localBuilder1.setTitle(str1);
      localBuilder1.setMessage(str2);
      localBuilder1.setNeutralButton(R.string.ok, new DialogInterface.OnClickListener()
      {
        public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          BaseAccountSelectionActivity.this.finish();
        }
      });
      localBuilder1.setOnCancelListener(new DialogInterface.OnCancelListener()
      {
        public final void onCancel(DialogInterface paramAnonymousDialogInterface)
        {
          BaseAccountSelectionActivity.this.finish();
        }
      });
      localObject = localBuilder1.create();
    }
  }

  public void onNewIntent(Intent paramIntent)
  {
    super.onNewIntent(paramIntent);
    if (Build.VERSION.SDK_INT < 14)
      setIntent(paramIntent);
  }

  public void onPause()
  {
    EsService.unregisterListener(this.mServiceListener);
    super.onPause();
  }

  public void onResume()
  {
    super.onResume();
    EsService.registerListener(this.mServiceListener);
    ServiceResult localServiceResult2;
    if ((this.mAddAccountPendingRequestId != null) && (!EsService.isRequestPending(this.mAddAccountPendingRequestId.intValue())))
    {
      dismissDialog(10);
      if (Build.VERSION.SDK_INT < 14)
        this.mAccountsListFragment.showList();
      localServiceResult2 = EsService.removeResult(this.mAddAccountPendingRequestId.intValue());
      if (localServiceResult2 != null)
      {
        if ((!localServiceResult2.hasError()) || (EsService.isOutOfBoxError(localServiceResult2.getException())))
          handleResponse(this.mAddAccountPendingRequestId.intValue(), EsService.getActiveAccount(this));
      }
      else
        this.mAddAccountPendingRequestId = null;
    }
    else if ((this.mUpdateAccountIdPendingRequestId != null) && (!EsService.isRequestPending(this.mUpdateAccountIdPendingRequestId.intValue())))
    {
      dismissDialog(11);
      ServiceResult localServiceResult1 = EsService.removeResult(this.mUpdateAccountIdPendingRequestId.intValue());
      if (localServiceResult1 != null)
      {
        if (localServiceResult1.hasError())
          break label188;
        handleUpgradeSuccess(EsService.getActiveAccount(this));
      }
    }
    while (true)
    {
      this.mUpdateAccountIdPendingRequestId = null;
      if (Build.VERSION.SDK_INT < 14)
        overridePendingTransition(0, 0);
      return;
      handleError(localServiceResult2);
      break;
      label188: handleUpgradeFailure();
    }
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mAddAccountPendingRequestId != null)
      paramBundle.putInt("aa_reqid", this.mAddAccountPendingRequestId.intValue());
    if (this.mUpdateAccountIdPendingRequestId != null)
      paramBundle.putInt("ua_reqid", this.mUpdateAccountIdPendingRequestId.intValue());
  }

  protected final void showAccountList()
  {
    if (Build.VERSION.SDK_INT < 14)
      this.mAccountsListFragment.showList();
    while (true)
    {
      return;
      chooseAccountManually();
    }
  }

  protected final void showAccountSelectionOrUpgradeAccount(Bundle paramBundle)
  {
    EsAccount localEsAccount = EsAccountsData.getActiveAccountUnsafe(this);
    if ((localEsAccount != null) && (EsAccountsData.isAccountUpgradeRequired(this, localEsAccount)))
    {
      this.mUpdateAccountIdPendingRequestId = Integer.valueOf(EsService.upgradeAccount(this, localEsAccount));
      showDialog(11);
    }
    while (true)
    {
      return;
      if (Build.VERSION.SDK_INT < 14)
      {
        setContentView(R.layout.account_selection_activity);
        if (Build.VERSION.SDK_INT < 11)
        {
          showTitlebar(false);
          setTitlebarTitle(getString(R.string.app_name));
        }
        ((TextView)findViewById(R.id.info_title)).setText(R.string.signup_select_account_title);
        ((TextView)findViewById(R.id.info_desc)).setText(R.string.signup_select_account_desc);
      }
      else if ((this.mAddAccountPendingRequestId == null) && (paramBundle == null) && (Build.VERSION.SDK_INT >= 14))
      {
        if ((!EsAccountsData.hasLoggedInThePast(this)) && (AccountManager.get(this).getAccountsByType("com.google").length == 1))
        {
          if (EsAccountsData.hasVisitedOob(this))
          {
            EsAccountsData.setHasVisitedOob(this, false);
            finish();
          }
          else
          {
            AccountsAdder localAccountsAdder = this.mAccountsAdder;
            Account[] arrayOfAccount = AccountManager.get(this).getAccountsByType("com.google");
            if (arrayOfAccount.length > 0);
            for (String str = arrayOfAccount[0].name; ; str = null)
            {
              localAccountsAdder.addAccount(str);
              break;
            }
          }
        }
        else
          chooseAccountManually();
      }
    }
  }

  public static abstract interface AccountsAdder
  {
    public abstract void addAccount(String paramString);
  }

  private final class ServiceListener extends EsServiceListener
  {
    private ServiceListener()
    {
    }

    public final void onAccountAdded(int paramInt, EsAccount paramEsAccount, ServiceResult paramServiceResult)
    {
      if ((BaseAccountSelectionActivity.this.mAddAccountPendingRequestId != null) && (BaseAccountSelectionActivity.this.mAddAccountPendingRequestId.equals(Integer.valueOf(paramInt))))
      {
        BaseAccountSelectionActivity.this.dismissDialog(10);
        if (Build.VERSION.SDK_INT < 14)
        {
          if (BaseAccountSelectionActivity.this.mAccountsListFragment == null)
            break label100;
          BaseAccountSelectionActivity.this.mAccountsListFragment.showList();
        }
        if ((paramServiceResult.hasError()) && (!EsService.isOutOfBoxError(paramServiceResult.getException())))
          break label112;
        BaseAccountSelectionActivity.this.handleResponse(paramInt, paramEsAccount);
      }
      while (true)
      {
        BaseAccountSelectionActivity.access$102(BaseAccountSelectionActivity.this, null);
        return;
        label100: BaseAccountSelectionActivity.access$302(BaseAccountSelectionActivity.this, true);
        break;
        label112: BaseAccountSelectionActivity.this.handleError(paramServiceResult);
      }
    }

    public final void onAccountUpgraded(int paramInt, EsAccount paramEsAccount, ServiceResult paramServiceResult)
    {
      if ((BaseAccountSelectionActivity.this.mUpdateAccountIdPendingRequestId != null) && (BaseAccountSelectionActivity.this.mUpdateAccountIdPendingRequestId.equals(Integer.valueOf(paramInt))))
      {
        BaseAccountSelectionActivity.access$602(BaseAccountSelectionActivity.this, null);
        BaseAccountSelectionActivity.this.dismissDialog(11);
        if (paramServiceResult.hasError())
          break label61;
        BaseAccountSelectionActivity.this.handleUpgradeSuccess(paramEsAccount);
      }
      while (true)
      {
        return;
        label61: BaseAccountSelectionActivity.this.handleUpgradeFailure();
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.BaseAccountSelectionActivity
 * JD-Core Version:    0.6.2
 */