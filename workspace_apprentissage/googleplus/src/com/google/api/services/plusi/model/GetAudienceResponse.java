package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class GetAudienceResponse extends GenericJson
{
  public String aclJson;
  public TraceRecords backendTrace;
  public Integer gaiaAudienceCount;
  public Integer nonGaiaAudienceCount;
  public List<Person> person;
  public String updateId;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetAudienceResponse
 * JD-Core Version:    0.6.2
 */