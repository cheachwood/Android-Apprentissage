package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ModifyMembershipsResponseJson extends EsJson<ModifyMembershipsResponse>
{
  static final ModifyMembershipsResponseJson INSTANCE = new ModifyMembershipsResponseJson();

  private ModifyMembershipsResponseJson()
  {
    super(ModifyMembershipsResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", DataCirclePersonJson.class, "circlePerson", DataRevertCookieJson.class, "revertCookie", "versionInfo", "viewerCanSeeAdultContent" });
  }

  public static ModifyMembershipsResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ModifyMembershipsResponseJson
 * JD-Core Version:    0.6.2
 */