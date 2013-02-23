package com.google.android.apps.plus.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.api.OzServerException;
import com.google.android.apps.plus.content.AccountSettingsData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.phone.OobDeviceActivity;
import com.google.android.apps.plus.phone.OobSelectPlusPageActivity;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.android.apps.plus.service.ServiceResult;

public class OobSelectPlusPageFragment extends ListFragment
  implements AlertFragmentDialog.AlertDialogListener
{
  private static final String[] DIALOG_IDS = { "activation_progress", "net_failure", "server_error" };
  private AccountSettingsData mAccountSettings;
  private final EsServiceListener mEsServiceListener = new ServiceListener((byte)0);
  private Integer mPendingRequestId;
  private int mSelectedAccountPosition = -1;

  private String[] createAccountNameArray()
  {
    Resources localResources = getActivity().getResources();
    int i = this.mAccountSettings.getNumPlusPages();
    String[] arrayOfString = new String[i + 1];
    arrayOfString[0] = this.mAccountSettings.getUserDisplayName();
    for (int j = 0; j < i; j++)
    {
      String str = this.mAccountSettings.getPlusPageName(j);
      arrayOfString[(j + 1)] = localResources.getString(R.string.oob_plus_page_name, new Object[] { str });
    }
    return arrayOfString;
  }

  private void handleServiceCallback(int paramInt, ServiceResult paramServiceResult)
  {
    if ((this.mPendingRequestId == null) || (this.mPendingRequestId.intValue() != paramInt));
    while (true)
    {
      return;
      this.mPendingRequestId = null;
      for (String str3 : DIALOG_IDS)
      {
        DialogFragment localDialogFragment = (DialogFragment)getFragmentManager().findFragmentByTag(str3);
        if (localDialogFragment != null)
          localDialogFragment.dismiss();
      }
      if (paramServiceResult.hasError())
      {
        Exception localException = paramServiceResult.getException();
        if ((localException instanceof OzServerException))
        {
          String str1;
          String str2;
          switch (((OzServerException)localException).getErrorCode())
          {
          default:
            str1 = getString(R.string.signup_title_no_connection);
            str2 = getString(R.string.signup_error_network);
          case 10:
          case 1:
          case 12:
          case 14:
          case 15:
          }
          while (true)
          {
            AlertFragmentDialog localAlertFragmentDialog2 = AlertFragmentDialog.newInstance(str1, str2, getString(R.string.ok), null);
            localAlertFragmentDialog2.setCancelable(false);
            localAlertFragmentDialog2.setTargetFragment(this, 0);
            localAlertFragmentDialog2.show(getFragmentManager(), "server_error");
            break;
            str2 = getString(R.string.signup_required_update_available);
            str1 = null;
            continue;
            str2 = getString(R.string.signup_authentication_error);
            str1 = null;
            continue;
            str2 = getString(R.string.signup_profile_error);
            str1 = null;
            continue;
            str1 = getString(R.string.signup_title_mobile_not_available);
            str2 = getString(R.string.signup_text_mobile_not_available);
          }
        }
        AlertFragmentDialog localAlertFragmentDialog1 = AlertFragmentDialog.newInstance(getString(R.string.signup_title_no_connection), getString(R.string.signup_error_network), getString(R.string.signup_retry), getString(R.string.cancel));
        localAlertFragmentDialog1.setCancelable(false);
        localAlertFragmentDialog1.setTargetFragment(this, 0);
        localAlertFragmentDialog1.show(getFragmentManager(), "net_failure");
      }
      else
      {
        ((OobDeviceActivity)getActivity()).onContinue();
      }
    }
  }

  public final void activateAccount()
  {
    if (!isAccountSelected())
      return;
    FragmentActivity localFragmentActivity = getActivity();
    EsAccount localEsAccount = (EsAccount)localFragmentActivity.getIntent().getParcelableExtra("account");
    boolean bool;
    label35: int i;
    String str1;
    String str2;
    if (this.mSelectedAccountPosition > 0)
    {
      bool = true;
      if (!bool)
        break label132;
      i = -1 + this.mSelectedAccountPosition;
      str1 = this.mAccountSettings.getPlusPageId(i);
      str2 = this.mAccountSettings.getPlusPageName(i);
    }
    for (String str3 = this.mAccountSettings.getPlusPagePhotoUrl(i); ; str3 = this.mAccountSettings.getUserPhotoUrl())
    {
      ProgressFragmentDialog.newInstance(null, getString(R.string.signup_signing_in), false).show(getFragmentManager(), "activation_progress");
      this.mPendingRequestId = Integer.valueOf(EsService.activateAccount(localFragmentActivity, localEsAccount, str1, str2, str3, bool, this.mAccountSettings));
      break;
      bool = false;
      break label35;
      label132: str1 = this.mAccountSettings.getUserGaiaId();
      str2 = this.mAccountSettings.getUserDisplayName();
    }
  }

  public final boolean isAccountSelected()
  {
    if (this.mSelectedAccountPosition != -1);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.mAccountSettings = ((AccountSettingsData)getActivity().getIntent().getParcelableExtra("plus_pages"));
    View localView = paramLayoutInflater.inflate(R.layout.oob_select_plus_page_fragment, paramViewGroup, false);
    setListAdapter(new ArrayAdapter(getActivity(), 17367055, createAccountNameArray()));
    if (paramBundle != null)
    {
      this.mSelectedAccountPosition = paramBundle.getInt("selected_account", -1);
      if (paramBundle.containsKey("reqid"))
        this.mPendingRequestId = Integer.valueOf(paramBundle.getInt("reqid"));
    }
    return localView;
  }

  public final void onDialogCanceled$20f9a4b7(String paramString)
  {
    throw new IllegalStateException("OOB dialog not cancelable");
  }

  public final void onDialogListClick$12e92030(int paramInt, Bundle paramBundle)
  {
  }

  public final void onDialogNegativeClick$20f9a4b7(String paramString)
  {
  }

  public final void onDialogPositiveClick(Bundle paramBundle, String paramString)
  {
    if ("net_failure".equals(paramString))
      activateAccount();
  }

  public final void onListItemClick(ListView paramListView, View paramView, int paramInt, long paramLong)
  {
    super.onListItemClick(paramListView, paramView, paramInt, paramLong);
    boolean bool = isAccountSelected();
    this.mSelectedAccountPosition = paramInt;
    if (!bool)
      ((OobSelectPlusPageActivity)getActivity()).setContinueButtonEnabled(true);
  }

  public final void onPause()
  {
    super.onPause();
    EsService.unregisterListener(this.mEsServiceListener);
  }

  public final void onResume()
  {
    super.onResume();
    EsService.registerListener(this.mEsServiceListener);
    if ((this.mPendingRequestId != null) && (!EsService.isRequestPending(this.mPendingRequestId.intValue())))
    {
      ServiceResult localServiceResult = EsService.removeResult(this.mPendingRequestId.intValue());
      if (localServiceResult != null)
        handleServiceCallback(this.mPendingRequestId.intValue(), localServiceResult);
    }
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putInt("selected_account", this.mSelectedAccountPosition);
    if (this.mPendingRequestId != null)
      paramBundle.putInt("reqid", this.mPendingRequestId.intValue());
  }

  public final void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    getListView().setChoiceMode(1);
  }

  private final class ServiceListener extends EsServiceListener
  {
    private ServiceListener()
    {
    }

    public final void onAccountActivated$6a63df5(int paramInt, ServiceResult paramServiceResult)
    {
      OobSelectPlusPageFragment.this.handleServiceCallback(paramInt, paramServiceResult);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.OobSelectPlusPageFragment
 * JD-Core Version:    0.6.2
 */