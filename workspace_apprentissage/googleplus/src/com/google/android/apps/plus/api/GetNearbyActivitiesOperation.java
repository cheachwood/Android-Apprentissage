package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.DbLocation;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.android.apps.plus.service.EsSyncAdapterService.SyncState;
import com.google.api.services.plusi.model.NearbyStreamRequest;
import com.google.api.services.plusi.model.NearbyStreamRequestJson;
import com.google.api.services.plusi.model.NearbyStreamResponse;
import com.google.api.services.plusi.model.NearbyStreamResponseJson;

public final class GetNearbyActivitiesOperation extends PlusiOperation<NearbyStreamRequest, NearbyStreamResponse>
{
  private final String mContinuationToken;
  private final DbLocation mLocation;
  private final int mMaxCount;
  private final EsSyncAdapterService.SyncState mSyncState;

  public GetNearbyActivitiesOperation(Context paramContext, EsAccount paramEsAccount, DbLocation paramDbLocation, String paramString, int paramInt, EsSyncAdapterService.SyncState paramSyncState, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "nearbystream", NearbyStreamRequestJson.getInstance(), NearbyStreamResponseJson.getInstance(), null, paramOperationListener);
    this.mLocation = paramDbLocation;
    this.mContinuationToken = paramString;
    if (paramInt > 0);
    while (true)
    {
      this.mMaxCount = paramInt;
      this.mSyncState = paramSyncState;
      return;
      paramInt = 10;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.GetNearbyActivitiesOperation
 * JD-Core Version:    0.6.2
 */