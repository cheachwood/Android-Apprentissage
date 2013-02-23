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

public final class EventReadOperation extends PlusiOperation<EventReadRequest, EventLeafResponse>
{
  private static final String[] EVENT_PROJECTION = { "polling_token", "resume_token" };
  private String mAuthKey;
  private final String mEventId;
  private final boolean mFetchNewer;
  private final String mInvitationToken;
  private boolean mPermissionErrorEncountered = false;
  private String mPollingToken;
  private final boolean mResolveTokens;
  private String mResumeToken;

  public EventReadOperation(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, boolean paramBoolean, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "eventread", EventReadRequestJson.getInstance(), EventLeafResponseJson.getInstance(), null, null);
    if (TextUtils.isEmpty(paramString1))
      throw new IllegalArgumentException("Event ID must not be empty");
    this.mEventId = paramString1;
    this.mPollingToken = paramString2;
    this.mResumeToken = paramString3;
    this.mAuthKey = paramString4;
    this.mInvitationToken = paramString5;
    this.mFetchNewer = paramBoolean;
    this.mResolveTokens = false;
  }

  public EventReadOperation(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, boolean paramBoolean, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "eventread", EventReadRequestJson.getInstance(), EventLeafResponseJson.getInstance(), null, null);
    if (TextUtils.isEmpty(paramString1))
      throw new IllegalArgumentException("Event ID must not be empty");
    this.mEventId = paramString1;
    this.mPollingToken = null;
    this.mResumeToken = null;
    this.mAuthKey = paramString2;
    this.mInvitationToken = null;
    this.mFetchNewer = paramBoolean;
    this.mResolveTokens = true;
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
          Log.i("HttpTransaction", "[EVENT_READ] received error: " + paramInt + "; disable IS");
        EsEventData.disableInstantShare(this.mContext);
      }
    }
  }

  public final void setErrorInfo(int paramInt, String paramString, Exception paramException)
  {
    if (this.mPermissionErrorEncountered)
      super.setErrorInfo(403, "INSUFFICIENT_PERMISSION", null);
    while (true)
    {
      return;
      super.setErrorInfo(paramInt, paramString, paramException);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.EventReadOperation
 * JD-Core Version:    0.6.2
 */