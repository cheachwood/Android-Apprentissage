package com.google.android.apps.plus.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewSwitcher;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.api.OzServerException;
import com.google.android.apps.plus.content.AccountSettingsData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.oob.ActionCallback;
import com.google.android.apps.plus.oob.BaseFieldLayout;
import com.google.android.apps.plus.oob.OutOfBoxDialogInflater;
import com.google.android.apps.plus.oob.OutOfBoxInflater;
import com.google.android.apps.plus.oob.OutOfBoxRequestParcelable;
import com.google.android.apps.plus.oob.OutOfBoxResponseParcelable;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.android.apps.plus.service.ServiceResult;
import com.google.android.apps.plus.util.SoftInput;
import com.google.android.apps.plus.views.ActionButton;
import com.google.android.apps.plus.views.BottomActionBar;
import com.google.api.services.plusi.model.MobileOutOfBoxRequest;
import com.google.api.services.plusi.model.MobileOutOfBoxResponse;
import com.google.api.services.plusi.model.OutOfBoxAction;
import com.google.api.services.plusi.model.OutOfBoxField;
import com.google.api.services.plusi.model.OutOfBoxView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OutOfBoxFragment extends Fragment
  implements AlertFragmentDialog.AlertDialogListener, ActionCallback
{
  private static final String[] DIALOG_IDS;
  private EsAccount mAccount;
  private BottomActionBar mBottomActionBar;
  private final EsServiceListener mEsServiceListener = new OobEsServiceListener((byte)0);
  private MobileOutOfBoxRequest mLastRequest;
  private ViewGroup mOobFields;
  private OutOfBoxDialogInflater mOutOfBoxDialogInflater;
  private OutOfBoxInflater mOutOfBoxInflater;
  private MobileOutOfBoxResponse mOutOfBoxResponse;
  private Integer mPendingRequestId;
  private ViewGroup mSignUpLayout;
  private String mUpgradeOrigin;
  private ViewSwitcher mViewSwitcher;

  static
  {
    if (!OutOfBoxFragment.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      DIALOG_IDS = new String[] { "sending", "net_failure", "event", "server_error" };
      return;
    }
  }

  private void close()
  {
    getActivity().setResult(0);
    getActivity().finish();
  }

  public static String createInitialTag()
  {
    return Integer.toString(0);
  }

  private void handleServiceCallback(int paramInt, ServiceResult paramServiceResult)
  {
    if ((this.mPendingRequestId == null) || (this.mPendingRequestId.intValue() != paramInt));
    MobileOutOfBoxResponse localMobileOutOfBoxResponse;
    while (true)
    {
      return;
      this.mPendingRequestId = null;
      localMobileOutOfBoxResponse = EsService.removeOutOfBoxResponse(paramInt);
      AccountSettingsData localAccountSettingsData = EsService.removeAccountSettingsResponse(paramInt);
      EsAccount localEsAccount1 = EsService.getActiveAccount(getActivity());
      if (!this.mAccount.equals(localEsAccount1))
      {
        close();
      }
      else if ((localMobileOutOfBoxResponse == null) || (paramServiceResult.hasError()))
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
        for (String str4 : DIALOG_IDS)
        {
          DialogFragment localDialogFragment = (DialogFragment)getFragmentManager().findFragmentByTag(str4);
          if (localDialogFragment != null)
            localDialogFragment.dismiss();
        }
        FragmentActivity localFragmentActivity = getActivity();
        if ((localMobileOutOfBoxResponse.signupComplete == null) || (!localMobileOutOfBoxResponse.signupComplete.booleanValue()))
          break;
        this.mAccount = EsService.getActiveAccount(localFragmentActivity);
        EsAccount localEsAccount2 = this.mAccount;
        Intent localIntent = null;
        if (localEsAccount2 != null)
          localIntent = Intents.getNextOobIntent(localFragmentActivity, this.mAccount, localAccountSettingsData, localFragmentActivity.getIntent());
        if (localIntent != null)
        {
          startActivityForResult(localIntent, 1);
        }
        else
        {
          localFragmentActivity.setResult(-1);
          localFragmentActivity.finish();
        }
      }
    }
    int k;
    if (isDialog())
      if (getFragmentManager().getBackStackEntryCount() > 0)
      {
        getFragmentManager().popBackStack();
        k = 1;
      }
    while (true)
    {
      if (getActivity().getCurrentFocus() != null)
        SoftInput.hide(getActivity().getCurrentFocus());
      FragmentTransaction localFragmentTransaction = getFragmentManager().beginTransaction();
      String str3 = Integer.toString(1 + Integer.parseInt(getTag()));
      localFragmentTransaction.add(R.id.oob_container, newInstance(this.mAccount, localMobileOutOfBoxResponse, this.mUpgradeOrigin), str3);
      if (k != 0)
        localFragmentTransaction.addToBackStack(str3);
      localFragmentTransaction.commit();
      break;
      k = 0;
      continue;
      k = 1;
    }
  }

  private boolean isDialog()
  {
    if (this.mOutOfBoxResponse.view.dialog != null);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public static OutOfBoxFragment newInstance(EsAccount paramEsAccount, MobileOutOfBoxResponse paramMobileOutOfBoxResponse, String paramString)
  {
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("account", paramEsAccount);
    localBundle.putParcelable("oob_resp", new OutOfBoxResponseParcelable(paramMobileOutOfBoxResponse));
    localBundle.putString("upgrade_origin", paramString);
    OutOfBoxFragment localOutOfBoxFragment = new OutOfBoxFragment();
    localOutOfBoxFragment.setArguments(localBundle);
    return localOutOfBoxFragment;
  }

  private void updateActionButtons()
  {
    int i = 0;
    int j;
    label44: int k;
    label46: BaseFieldLayout localBaseFieldLayout1;
    if (i < this.mOobFields.getChildCount())
    {
      BaseFieldLayout localBaseFieldLayout2 = (BaseFieldLayout)this.mOobFields.getChildAt(i);
      if ((localBaseFieldLayout2.shouldPreventCompletionAction()) && (localBaseFieldLayout2.isEmpty()))
      {
        j = 1;
        k = 0;
        if (k >= this.mOobFields.getChildCount())
          break label121;
        localBaseFieldLayout1 = (BaseFieldLayout)this.mOobFields.getChildAt(k);
        if ("CONTINUE".equals(localBaseFieldLayout1.getActionType()))
          if (j != 0)
            break label115;
      }
    }
    label115: for (boolean bool2 = true; ; bool2 = false)
    {
      localBaseFieldLayout1.setActionEnabled(bool2);
      k++;
      break label46;
      i++;
      break;
      j = 0;
      break label44;
    }
    label121: Iterator localIterator = this.mBottomActionBar.getButtons().iterator();
    while (localIterator.hasNext())
    {
      ActionButton localActionButton = (ActionButton)localIterator.next();
      if ("CONTINUE".equals(((OutOfBoxAction)localActionButton.getTag()).type))
      {
        if (j == 0);
        for (boolean bool1 = true; ; bool1 = false)
        {
          localActionButton.setEnabled(bool1);
          break;
        }
      }
    }
  }

  public final void onAction(OutOfBoxAction paramOutOfBoxAction)
  {
    if ("URL".equals(paramOutOfBoxAction.type))
      Intents.viewUrl(getActivity(), this.mAccount, paramOutOfBoxAction.url);
    while (true)
    {
      return;
      if ("BACK".equals(paramOutOfBoxAction.type))
      {
        if (!getFragmentManager().popBackStackImmediate())
          close();
      }
      else if ("CLOSE".equals(paramOutOfBoxAction.type))
      {
        close();
      }
      else
      {
        MobileOutOfBoxRequest localMobileOutOfBoxRequest = new MobileOutOfBoxRequest();
        localMobileOutOfBoxRequest.input = new ArrayList();
        for (int i = 0; i < this.mOobFields.getChildCount(); i++)
        {
          BaseFieldLayout localBaseFieldLayout = (BaseFieldLayout)this.mOobFields.getChildAt(i);
          if (localBaseFieldLayout.getField().input != null)
            localMobileOutOfBoxRequest.input.add(localBaseFieldLayout.newFieldFromInput());
        }
        localMobileOutOfBoxRequest.action = new OutOfBoxAction();
        localMobileOutOfBoxRequest.action.type = paramOutOfBoxAction.type;
        sendOutOfBoxRequest(localMobileOutOfBoxRequest);
      }
    }
  }

  public final void onActionId(String paramString)
  {
    while (true)
    {
      try
      {
        switch (Integer.parseInt(paramString))
        {
        default:
          if ("BACK".equals(localObject))
          {
            if (!getFragmentManager().popBackStackImmediate())
              close();
          }
          else if ("CLOSE".equals(localObject))
            close();
          break;
        case 1:
        case 2:
        case 3:
        case 4:
        }
      }
      catch (NumberFormatException localNumberFormatException)
      {
        Log.w("OutOfBoxFragment", "Unable to parse actionId: " + paramString + ", not calling action on this event.", localNumberFormatException);
      }
      MobileOutOfBoxRequest localMobileOutOfBoxRequest = new MobileOutOfBoxRequest();
      OutOfBoxAction localOutOfBoxAction = new OutOfBoxAction();
      localOutOfBoxAction.type = ((String)localObject);
      localMobileOutOfBoxRequest.action = localOutOfBoxAction;
      sendOutOfBoxRequest(localMobileOutOfBoxRequest);
      break label164;
      Object localObject = null;
      continue;
      label164: return;
      localObject = "CLOSE";
      continue;
      localObject = "CONTINUE";
      continue;
      localObject = "URL";
      continue;
      localObject = "BACK";
    }
  }

  public final void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    switch (paramInt1)
    {
    default:
      super.onActivityResult(paramInt1, paramInt2, paramIntent);
    case 1:
    }
    while (true)
    {
      return;
      getActivity().setResult(paramInt2);
      getActivity().finish();
    }
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.mAccount = ((EsAccount)getArguments().getParcelable("account"));
    this.mOutOfBoxResponse = ((OutOfBoxResponseParcelable)getArguments().getParcelable("oob_resp")).getResponse();
    this.mUpgradeOrigin = getArguments().getString("upgrade_origin");
    View localView = paramLayoutInflater.inflate(R.layout.out_of_box_fragment, paramViewGroup, false);
    this.mViewSwitcher = ((ViewSwitcher)localView.findViewById(R.id.switcher));
    this.mSignUpLayout = ((ViewGroup)localView.findViewById(R.id.signup_layout));
    this.mOobFields = ((ViewGroup)localView.findViewById(R.id.signup_items));
    this.mBottomActionBar = ((BottomActionBar)localView.findViewById(R.id.bottom_bar));
    this.mOutOfBoxInflater = new OutOfBoxInflater(this.mSignUpLayout, this.mOobFields, this.mBottomActionBar);
    this.mOutOfBoxDialogInflater = new OutOfBoxDialogInflater(getActivity(), (ViewGroup)localView.findViewById(R.id.dialog_content), this.mOutOfBoxResponse.view, this);
    if (isDialog())
    {
      this.mOutOfBoxDialogInflater.inflate();
      int i = this.mViewSwitcher.indexOfChild(this.mViewSwitcher.findViewById(R.id.dialog_frame));
      this.mViewSwitcher.setDisplayedChild(i);
    }
    while (true)
    {
      if (paramBundle != null)
      {
        OutOfBoxRequestParcelable localOutOfBoxRequestParcelable = (OutOfBoxRequestParcelable)paramBundle.getParcelable("last_request");
        if (localOutOfBoxRequestParcelable != null)
          this.mLastRequest = localOutOfBoxRequestParcelable.getRequest();
        if (paramBundle.containsKey("reqid"))
          this.mPendingRequestId = Integer.valueOf(paramBundle.getInt("reqid"));
      }
      return localView;
      this.mOutOfBoxInflater.inflateFromResponse(this.mOutOfBoxResponse.view, this);
      updateActionButtons();
    }
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
    assert ("net_failure".equals(paramString));
    close();
  }

  public final void onDialogPositiveClick(Bundle paramBundle, String paramString)
  {
    if ("net_failure".equals(paramString))
      if (this.mLastRequest != null)
        sendOutOfBoxRequest(this.mLastRequest);
    while (true)
    {
      return;
      if ("server_error".equals(paramString))
        close();
    }
  }

  public final void onInputChanged$7c32a9fe()
  {
    updateActionButtons();
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
    paramBundle.putParcelable("last_request", new OutOfBoxRequestParcelable(this.mLastRequest));
    if (this.mPendingRequestId != null)
      paramBundle.putInt("reqid", this.mPendingRequestId.intValue());
  }

  public final void sendOutOfBoxRequest(MobileOutOfBoxRequest paramMobileOutOfBoxRequest)
  {
    ProgressFragmentDialog.newInstance(null, getString(R.string.signup_sending), false).show(getFragmentManager(), "sending");
    paramMobileOutOfBoxRequest.upgradeOrigin = this.mUpgradeOrigin;
    this.mLastRequest = paramMobileOutOfBoxRequest;
    this.mPendingRequestId = Integer.valueOf(EsService.sendOutOfBoxRequest(getActivity(), this.mAccount, paramMobileOutOfBoxRequest));
  }

  private final class OobEsServiceListener extends EsServiceListener
  {
    private OobEsServiceListener()
    {
    }

    public final void onOobRequestComplete$6a63df5(int paramInt, ServiceResult paramServiceResult)
    {
      OutOfBoxFragment.this.handleServiceCallback(paramInt, paramServiceResult);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.OutOfBoxFragment
 * JD-Core Version:    0.6.2
 */