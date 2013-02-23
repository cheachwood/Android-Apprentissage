package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class ModifyMembershipsRequest extends GenericJson
{
  public DataCircleMembershipModificationParams circleMembershipModificationParams;
  public List<DataCircleId> circleToAdd;
  public List<DataCircleId> circleToRemove;
  public ApiaryFields commonFields;
  public Boolean enableTracing;
  public Boolean viewerCanSeeAdultContent;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ModifyMembershipsRequest
 * JD-Core Version:    0.6.2
 */