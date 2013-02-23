package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class CreateCircleResponse extends GenericJson
{
  public TraceRecords backendTrace;
  public Integer category;
  public DataCircleId circleId;
  public List<DataCirclePerson> circlePerson;
  public String nameSortKey;
  public DataRevertCookie revertCookie;
  public String versionInfo;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.CreateCircleResponse
 * JD-Core Version:    0.6.2
 */