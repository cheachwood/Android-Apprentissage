package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataPersonListJson extends EsJson<DataPersonList>
{
  static final DataPersonListJson INSTANCE = new DataPersonListJson();

  private DataPersonListJson()
  {
    super(DataPersonList.class, new Object[] { DataCircleMemberIdJson.class, "invalidMemberId", DataCirclePersonJson.class, "person", "syncClientInfo", DataSyncStateTokenJson.class, "syncStateToken", "totalPeople" });
  }

  public static DataPersonListJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataPersonListJson
 * JD-Core Version:    0.6.2
 */