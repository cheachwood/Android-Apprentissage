package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class LoadCircleMembersResponse extends GenericJson
{
  public TraceRecords backendTrace;
  public Long changesSinceMillis;
  public List<DataCircleData> circleData;
  public List<DataCirclePerson> circlePerson;
  public DataContinuationToken continuationToken;
  public Integer totalPeople;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LoadCircleMembersResponse
 * JD-Core Version:    0.6.2
 */