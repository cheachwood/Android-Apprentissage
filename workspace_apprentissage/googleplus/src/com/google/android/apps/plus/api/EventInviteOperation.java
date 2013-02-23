package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.json.GenericJson;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.InviteEventRequest;
import com.google.api.services.plusi.model.InviteEventRequestJson;

public final class EventInviteOperation extends PlusiOperation<InviteEventRequest, GenericJson>
{
  private final AudienceData mAudience;
  private final String mAuthKey;
  private final String mEventId;
  private final String mOwnerId;

  public EventInviteOperation(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, String paramString3, AudienceData paramAudienceData, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "inviteevent", InviteEventRequestJson.getInstance(), null, paramIntent, paramOperationListener);
    this.mEventId = paramString1;
    this.mOwnerId = paramString3;
    this.mAuthKey = paramString2;
    this.mAudience = paramAudienceData;
  }

  protected final void handleResponse(GenericJson paramGenericJson)
  {
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.EventInviteOperation
 * JD-Core Version:    0.6.2
 */