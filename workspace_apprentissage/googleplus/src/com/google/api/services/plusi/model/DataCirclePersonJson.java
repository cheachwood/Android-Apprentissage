package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataCirclePersonJson extends EsJson<DataCirclePerson>
{
  static final DataCirclePersonJson INSTANCE = new DataCirclePersonJson();

  private DataCirclePersonJson()
  {
    super(DataCirclePerson.class, new Object[] { DataCircleMemberIdJson.class, "joinKey", DataCircleMemberIdJson.class, "memberId", DataCircleMemberPropertiesJson.class, "memberProperties", DataMembershipJson.class, "membership", DataContactJson.class, "primaryContact" });
  }

  public static DataCirclePersonJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataCirclePersonJson
 * JD-Core Version:    0.6.2
 */