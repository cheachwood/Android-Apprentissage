package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class LoadPeopleRequest extends GenericJson
{
  public List<DataCircleMemberId> circleMemberId;
  public ApiaryFields commonFields;
  public Boolean enableTracing;
  public Boolean includeIsFollowing;
  public Boolean includeMemberships;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LoadPeopleRequest
 * JD-Core Version:    0.6.2
 */