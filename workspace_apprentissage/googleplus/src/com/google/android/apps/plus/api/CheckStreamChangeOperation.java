package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.GetActivitiesRequest;
import com.google.api.services.plusi.model.GetActivitiesRequestJson;
import com.google.api.services.plusi.model.GetActivitiesResponse;
import com.google.api.services.plusi.model.GetActivitiesResponseJson;

public final class CheckStreamChangeOperation extends PlusiOperation<GetActivitiesRequest, GetActivitiesResponse>
{
  private final String mCircleId;
  private final boolean mFromWidget;
  private final String mGaiaId;
  private final String mSquareStreamId;
  private boolean mStreamHasChanged;
  private final int mView;

  public CheckStreamChangeOperation(Context paramContext, EsAccount paramEsAccount, int paramInt, String paramString1, String paramString2, String paramString3, boolean paramBoolean, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "getactivities", GetActivitiesRequestJson.getInstance(), GetActivitiesResponseJson.getInstance(), null, null);
    this.mView = paramInt;
    if ((!TextUtils.isEmpty(paramString1)) && (paramString1.startsWith("f.")))
      paramString1 = paramString1.substring(2);
    this.mCircleId = paramString1;
    this.mGaiaId = paramString2;
    this.mSquareStreamId = paramString3;
    this.mFromWidget = paramBoolean;
  }

  public final boolean hasStreamChanged()
  {
    return this.mStreamHasChanged;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.CheckStreamChangeOperation
 * JD-Core Version:    0.6.2
 */