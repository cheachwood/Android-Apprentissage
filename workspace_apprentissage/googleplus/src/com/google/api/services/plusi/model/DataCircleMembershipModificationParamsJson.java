package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataCircleMembershipModificationParamsJson extends EsJson<DataCircleMembershipModificationParams>
{
  static final DataCircleMembershipModificationParamsJson INSTANCE = new DataCircleMembershipModificationParamsJson();

  private DataCircleMembershipModificationParamsJson()
  {
    super(DataCircleMembershipModificationParams.class, new Object[] { "addCircleMembersActionSource", "includeIsFollowing", DataCircleMemberToAddJson.class, "person" });
  }

  public static DataCircleMembershipModificationParamsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataCircleMembershipModificationParamsJson
 * JD-Core Version:    0.6.2
 */