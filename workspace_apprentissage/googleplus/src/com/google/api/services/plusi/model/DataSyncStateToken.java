package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;

public final class DataSyncStateToken extends GenericJson
{
  public DataContinuationToken continuationToken;
  public Long lastUpdateTimeMs;
  public Long sessionLastUpdateTimeMs;
  public String versionKey;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataSyncStateToken
 * JD-Core Version:    0.6.2
 */