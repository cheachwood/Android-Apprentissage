package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.android.apps.plus.service.EsSyncAdapterService.SyncState;
import com.google.api.services.plusi.model.GetActivitiesRequest;
import com.google.api.services.plusi.model.GetActivitiesRequestJson;
import com.google.api.services.plusi.model.GetActivitiesResponse;
import com.google.api.services.plusi.model.GetActivitiesResponseJson;

public final class GetActivitiesOperation extends PlusiOperation<GetActivitiesRequest, GetActivitiesResponse>
{
  private final String mCircleId;
  private final String mContinuationToken;
  private final boolean mFromWidget;
  private final String mGaiaId;
  private final int mMaxCount;
  private long mRequestTime;
  private final String mSquareStreamId;
  private final EsSyncAdapterService.SyncState mSyncState;
  private final int mView;

  public GetActivitiesOperation(Context paramContext, EsAccount paramEsAccount, int paramInt1, String paramString1, String paramString2, String paramString3, boolean paramBoolean, String paramString4, int paramInt2, EsSyncAdapterService.SyncState paramSyncState, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "getactivities", GetActivitiesRequestJson.getInstance(), GetActivitiesResponseJson.getInstance(), null, paramOperationListener);
    this.mView = paramInt1;
    if ((!TextUtils.isEmpty(paramString1)) && (paramString1.startsWith("f.")))
      paramString1 = paramString1.substring(2);
    this.mCircleId = paramString1;
    this.mGaiaId = paramString2;
    this.mSquareStreamId = paramString3;
    this.mFromWidget = paramBoolean;
    this.mContinuationToken = paramString4;
    if (paramInt2 > 0);
    while (true)
    {
      this.mMaxCount = paramInt2;
      this.mSyncState = paramSyncState;
      return;
      paramInt2 = 10;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.GetActivitiesOperation
 * JD-Core Version:    0.6.2
 */