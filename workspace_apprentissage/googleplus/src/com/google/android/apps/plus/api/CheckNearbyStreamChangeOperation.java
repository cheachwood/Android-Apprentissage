package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.DbLocation;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.NearbyStreamRequest;
import com.google.api.services.plusi.model.NearbyStreamRequestJson;
import com.google.api.services.plusi.model.NearbyStreamResponse;
import com.google.api.services.plusi.model.NearbyStreamResponseJson;

public final class CheckNearbyStreamChangeOperation extends PlusiOperation<NearbyStreamRequest, NearbyStreamResponse>
{
  private final DbLocation mLocation;
  private boolean mStreamHasChanged;

  public CheckNearbyStreamChangeOperation(Context paramContext, EsAccount paramEsAccount, DbLocation paramDbLocation, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "nearbystream", NearbyStreamRequestJson.getInstance(), NearbyStreamResponseJson.getInstance(), null, null);
    this.mLocation = paramDbLocation;
  }

  public final boolean hasStreamChanged()
  {
    return this.mStreamHasChanged;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.CheckNearbyStreamChangeOperation
 * JD-Core Version:    0.6.2
 */