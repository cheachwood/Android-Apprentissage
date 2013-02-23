package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.GetAudienceRequest;
import com.google.api.services.plusi.model.GetAudienceRequestJson;
import com.google.api.services.plusi.model.GetAudienceResponse;
import com.google.api.services.plusi.model.GetAudienceResponseJson;

public final class GetAudienceOperation extends PlusiOperation<GetAudienceRequest, GetAudienceResponse>
{
  private final String mActivityId;
  private AudienceData mAudienceData;

  public GetAudienceOperation(Context paramContext, EsAccount paramEsAccount, String paramString, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "getaudience", GetAudienceRequestJson.getInstance(), GetAudienceResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mActivityId = paramString;
  }

  public final AudienceData getAudience()
  {
    return this.mAudienceData;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.GetAudienceOperation
 * JD-Core Version:    0.6.2
 */