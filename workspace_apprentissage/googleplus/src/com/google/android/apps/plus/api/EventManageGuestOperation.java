package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.EventManageGuestsRequest;
import com.google.api.services.plusi.model.EventManageGuestsRequestJson;
import com.google.api.services.plusi.model.EventManageGuestsResponse;
import com.google.api.services.plusi.model.EventManageGuestsResponseJson;

public final class EventManageGuestOperation extends PlusiOperation<EventManageGuestsRequest, EventManageGuestsResponse>
{
  private final String mAuthKey;
  private final boolean mBlacklist;
  private final String mEmail;
  private final String mEventId;
  private final String mGaiaId;

  public EventManageGuestOperation(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, boolean paramBoolean, String paramString3, String paramString4, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "eventmanageguests", EventManageGuestsRequestJson.getInstance(), EventManageGuestsResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mEventId = paramString1;
    this.mAuthKey = paramString2;
    this.mBlacklist = paramBoolean;
    this.mGaiaId = paramString3;
    this.mEmail = paramString4;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.EventManageGuestOperation
 * JD-Core Version:    0.6.2
 */