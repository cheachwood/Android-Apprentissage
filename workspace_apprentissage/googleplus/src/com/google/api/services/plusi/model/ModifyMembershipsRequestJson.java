package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ModifyMembershipsRequestJson extends EsJson<ModifyMembershipsRequest>
{
  static final ModifyMembershipsRequestJson INSTANCE = new ModifyMembershipsRequestJson();

  private ModifyMembershipsRequestJson()
  {
    super(ModifyMembershipsRequest.class, new Object[] { DataCircleMembershipModificationParamsJson.class, "circleMembershipModificationParams", DataCircleIdJson.class, "circleToAdd", DataCircleIdJson.class, "circleToRemove", ApiaryFieldsJson.class, "commonFields", "enableTracing", "viewerCanSeeAdultContent" });
  }

  public static ModifyMembershipsRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ModifyMembershipsRequestJson
 * JD-Core Version:    0.6.2
 */