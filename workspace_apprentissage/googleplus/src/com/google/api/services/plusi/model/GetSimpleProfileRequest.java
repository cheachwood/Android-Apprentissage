package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;

public final class GetSimpleProfileRequest extends GenericJson
{
  public ApiaryFields commonFields;
  public Boolean enableTracing;
  public Boolean includeAclData;
  public Boolean includeIncomingMembership;
  public Boolean includeViewerCircles;
  public String ownerId;
  public Boolean useCachedDataForCircles;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetSimpleProfileRequest
 * JD-Core Version:    0.6.2
 */