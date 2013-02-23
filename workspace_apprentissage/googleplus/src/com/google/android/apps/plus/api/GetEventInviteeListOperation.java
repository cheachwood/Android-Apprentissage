package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.GetEventInviteeListRequest;
import com.google.api.services.plusi.model.GetEventInviteeListRequestJson;
import com.google.api.services.plusi.model.GetEventInviteeListResponse;
import com.google.api.services.plusi.model.GetEventInviteeListResponseJson;

public final class GetEventInviteeListOperation extends PlusiOperation<GetEventInviteeListRequest, GetEventInviteeListResponse>
{
  private final String mAuthKey;
  private final String mEventId;
  private final boolean mIncludeBlacklist;

  public GetEventInviteeListOperation(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, boolean paramBoolean, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "geteventinviteelist", GetEventInviteeListRequestJson.getInstance(), GetEventInviteeListResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mEventId = paramString1;
    this.mIncludeBlacklist = paramBoolean;
    this.mAuthKey = paramString2;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.GetEventInviteeListOperation
 * JD-Core Version:    0.6.2
 */