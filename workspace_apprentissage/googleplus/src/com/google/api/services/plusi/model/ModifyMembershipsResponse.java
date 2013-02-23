package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class ModifyMembershipsResponse extends GenericJson
{
  public TraceRecords backendTrace;
  public List<DataCirclePerson> circlePerson;
  public DataRevertCookie revertCookie;
  public String versionInfo;
  public Boolean viewerCanSeeAdultContent;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ModifyMembershipsResponse
 * JD-Core Version:    0.6.2
 */