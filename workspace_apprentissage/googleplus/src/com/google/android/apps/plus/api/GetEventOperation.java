package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsEventData;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.android.apps.plus.phone.InstantUpload;
import com.google.android.apps.plus.util.EsLog;
import com.google.api.services.plusi.model.EventLeafResponse;
import com.google.api.services.plusi.model.EventLeafResponseJson;
import com.google.api.services.plusi.model.EventReadRequest;
import com.google.api.services.plusi.model.EventReadRequestJson;

public final class GetEventOperation extends PlusiOperation<EventReadRequest, EventLeafResponse>
{
  private final String mAuthKey;
  private final String mEventId;

  public GetEventOperation(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "eventread", EventReadRequestJson.getInstance(), EventLeafResponseJson.getInstance(), null, null);
    if (TextUtils.isEmpty(paramString1))
      throw new IllegalArgumentException("Event ID must not be empty");
    this.mEventId = paramString1;
    this.mAuthKey = paramString2;
  }

  protected final void onHttpOperationComplete(int paramInt, String paramString, Exception paramException)
  {
    if (paramInt == 404)
      EsEventData.deleteEvent(this.mContext, this.mAccount, this.mEventId);
    while (true)
    {
      return;
      if ((paramInt >= 400) && (TextUtils.equals(this.mEventId, InstantUpload.getInstantShareEventId(this.mContext))))
      {
        if (EsLog.isLoggable("HttpTransaction", 4))
          Log.i("HttpTransaction", "[GET_EVENT] received error: " + paramInt + "; disable IS");
        EsEventData.disableInstantShare(this.mContext);
      }
    }
  }

  public static abstract interface EventQuery
  {
    public static final String[] PROJECTION = { "polling_token", "resume_token", "display_time" };
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.GetEventOperation
 * JD-Core Version:    0.6.2
 */