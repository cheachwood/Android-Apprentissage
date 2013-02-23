package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsEventData;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.EventRespondRequest;
import com.google.api.services.plusi.model.EventRespondRequestJson;
import com.google.api.services.plusi.model.EventRespondResponse;
import com.google.api.services.plusi.model.EventRespondResponseJson;

public final class SendEventRsvpOperation extends PlusiOperation<EventRespondRequest, EventRespondResponse>
{
  private final String mAuthKey;
  private final String mEventId;
  private final String mRollbackRsvpType;
  private final String mRsvpType;

  public SendEventRsvpOperation(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, String paramString3, String paramString4, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "eventrespond", EventRespondRequestJson.getInstance(), EventRespondResponseJson.getInstance(), null, null);
    this.mAuthKey = paramString2;
    this.mEventId = paramString1;
    this.mRsvpType = paramString3;
    this.mRollbackRsvpType = paramString4;
  }

  private void rollback()
  {
    if (TextUtils.equals(EsEventData.getRsvpType(EsEventData.getPlusEvent(this.mContext, this.mAccount, this.mEventId)), this.mRsvpType))
      EsEventData.setRsvpType(this.mContext, this.mAccount, this.mEventId, this.mRollbackRsvpType);
    EsEventData.refreshEvent(this.mContext, this.mAccount, this.mEventId);
  }

  protected final void onHttpOperationComplete(int paramInt, String paramString, Exception paramException)
  {
    if ((paramInt != 200) || (paramException != null))
      rollback();
  }

  private static enum Status
  {
    static
    {
      SUCCESS = new Status("SUCCESS", 1);
      REJECTED_OFF_NETWORK_DISPLAY_NAME = new Status("REJECTED_OFF_NETWORK_DISPLAY_NAME", 2);
      Status[] arrayOfStatus = new Status[3];
      arrayOfStatus[0] = UNKNOWN;
      arrayOfStatus[1] = SUCCESS;
      arrayOfStatus[2] = REJECTED_OFF_NETWORK_DISPLAY_NAME;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.SendEventRsvpOperation
 * JD-Core Version:    0.6.2
 */