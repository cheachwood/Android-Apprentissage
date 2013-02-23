package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.DbLocation;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.SnapToPlaceRequest;
import com.google.api.services.plusi.model.SnapToPlaceRequestJson;
import com.google.api.services.plusi.model.SnapToPlaceResponse;
import com.google.api.services.plusi.model.SnapToPlaceResponseJson;

public final class SnapToPlaceOperation extends PlusiOperation<SnapToPlaceRequest, SnapToPlaceResponse>
{
  private DbLocation mCoarseLocation;
  private DbLocation mFirstPlace;
  private final boolean mIsPlaceSearch;
  private final LocationQuery mLocationQuery;
  private final DbLocation mOmitLocation;
  private DbLocation mPreciseLocation;
  private final boolean mStoreResult;

  public SnapToPlaceOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, LocationQuery paramLocationQuery, DbLocation paramDbLocation, boolean paramBoolean)
  {
    super(paramContext, paramEsAccount, "snaptoplace", SnapToPlaceRequestJson.getInstance(), SnapToPlaceResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mLocationQuery = paramLocationQuery;
    this.mOmitLocation = paramDbLocation;
    this.mStoreResult = paramBoolean;
    this.mIsPlaceSearch = this.mLocationQuery.hasQueryString();
  }

  public final DbLocation getCoarseLocation()
  {
    return this.mCoarseLocation;
  }

  public final DbLocation getPlaceLocation()
  {
    return this.mFirstPlace;
  }

  public final DbLocation getPreciseLocation()
  {
    return this.mPreciseLocation;
  }

  public final boolean hasCoarseLocation()
  {
    if (this.mCoarseLocation != null);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean hasPlaceLocation()
  {
    if (this.mFirstPlace != null);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean hasPreciseLocation()
  {
    if (this.mPreciseLocation != null);
    for (boolean bool = true; ; bool = false)
      return bool;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.SnapToPlaceOperation
 * JD-Core Version:    0.6.2
 */