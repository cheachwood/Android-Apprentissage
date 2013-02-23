package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.GetActivityRequest;
import com.google.api.services.plusi.model.GetActivityRequestJson;
import com.google.api.services.plusi.model.GetActivityResponse;
import com.google.api.services.plusi.model.GetActivityResponseJson;

public final class GetActivityOperation extends PlusiOperation<GetActivityRequest, GetActivityResponse>
{
  private final String mActivityId;
  private final String mOwnerGaiaId;
  private String mResponseUpdateId;
  private final String mSquareId;

  public GetActivityOperation(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, String paramString3, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "getactivity", GetActivityRequestJson.getInstance(), GetActivityResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mActivityId = paramString1;
    this.mOwnerGaiaId = paramString2;
    this.mSquareId = paramString3;
  }

  public final String getResponseUpdateId()
  {
    return this.mResponseUpdateId;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.GetActivityOperation
 * JD-Core Version:    0.6.2
 */