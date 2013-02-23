package com.google.android.apps.plus.phone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAccountsData;
import com.google.android.apps.plus.fragments.ProgressFragmentDialog;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.android.apps.plus.service.ServiceResult;
import com.google.android.apps.plus.util.EsLog;
import java.util.ArrayList;

public abstract class EsProfileGatewayActivity extends FragmentActivity
{
  protected EsAccount mAccount;
  private final Handler mHandler = new Handler();
  protected Integer mPendingRequestId;
  protected String mPersonId;
  protected String mPersonName;
  protected boolean mRedirected;
  private final EsServiceListener mServiceListener = new EsServiceListener()
  {
    public final void onSetCircleMembershipComplete$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      EsProfileGatewayActivity.this.handleServiceCallback(paramAnonymousInt, paramAnonymousServiceResult);
    }
  };

  protected final void handleServiceCallback(int paramInt, ServiceResult paramServiceResult)
  {
    if ((this.mPendingRequestId == null) || (paramInt != this.mPendingRequestId.intValue()))
      return;
    DialogFragment localDialogFragment = (DialogFragment)getSupportFragmentManager().findFragmentByTag("req_pending");
    if (localDialogFragment != null)
      localDialogFragment.dismiss();
    this.mPendingRequestId = null;
    if ((paramServiceResult != null) && (paramServiceResult.hasError()))
      Toast.makeText(getApplicationContext(), R.string.transient_server_error, 0).show();
    while (true)
    {
      finish();
      break;
      Context localContext = getApplicationContext();
      int i = R.string.add_to_circle_confirmation_toast;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.mPersonName;
      Toast.makeText(localContext, getString(i, arrayOfObject), 0).show();
    }
  }

  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    int i = 0;
    switch (paramInt1)
    {
    default:
    case 0:
    }
    while (true)
    {
      if (i == 0)
        finish();
      return;
      i = 0;
      if (paramInt2 == -1)
      {
        final ArrayList localArrayList = paramIntent.getStringArrayListExtra("selected_circle_ids");
        this.mHandler.post(new Runnable()
        {
          public final void run()
          {
            EsProfileGatewayActivity.this.setCircleMembership(localArrayList);
          }
        });
        i = 1;
      }
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
    {
      this.mAccount = ((EsAccount)paramBundle.getParcelable("account"));
      this.mPersonId = paramBundle.getString("person_id");
      this.mPersonName = paramBundle.getString("person_name");
      if (paramBundle.containsKey("pending_req_id"))
        this.mPendingRequestId = Integer.valueOf(paramBundle.getInt("pending_req_id"));
      this.mRedirected = paramBundle.getBoolean("redirected");
      if ((this.mAccount != null) && (!this.mRedirected))
        break label104;
      finish();
    }
    while (true)
    {
      return;
      this.mAccount = EsAccountsData.getActiveAccount(this);
      break;
      label104: if (getIntent().getData() == null)
        finish();
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
    if ((this.mPendingRequestId != null) && (!EsService.isRequestPending(this.mPendingRequestId.intValue())))
    {
      ServiceResult localServiceResult = EsService.removeResult(this.mPendingRequestId.intValue());
      handleServiceCallback(this.mPendingRequestId.intValue(), localServiceResult);
      this.mPendingRequestId = null;
    }
  }

  protected void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putParcelable("account", this.mAccount);
    paramBundle.putString("person_id", this.mPersonId);
    paramBundle.putString("person_name", this.mPersonName);
    if (this.mPendingRequestId != null)
      paramBundle.putInt("pending_req_id", this.mPendingRequestId.intValue());
    paramBundle.putBoolean("redirected", this.mRedirected);
  }

  protected final void setCircleMembership(ArrayList<String> paramArrayList)
  {
    this.mPendingRequestId = EsService.setCircleMembership(this, this.mAccount, this.mPersonId, this.mPersonName, (String[])paramArrayList.toArray(new String[0]), null);
    ProgressFragmentDialog localProgressFragmentDialog = ProgressFragmentDialog.newInstance(null, getString(R.string.add_to_circle_operation_pending), false);
    try
    {
      localProgressFragmentDialog.show(getSupportFragmentManager(), "req_pending");
      return;
    }
    catch (Throwable localThrowable)
    {
      while (true)
        if (EsLog.isLoggable("EsProfileGatewayActivity", 6))
          Log.e("EsProfileGatewayActivity", "Cannot show dialog", localThrowable);
    }
  }

  protected final void showCirclePicker()
  {
    startActivityForResult(Intents.getCircleMembershipActivityIntent(this, this.mAccount, this.mPersonId, this.mPersonName, false), 0);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.EsProfileGatewayActivity
 * JD-Core Version:    0.6.2
 */