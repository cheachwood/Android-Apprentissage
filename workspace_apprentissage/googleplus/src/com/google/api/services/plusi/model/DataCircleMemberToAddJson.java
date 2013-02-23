package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataCircleMemberToAddJson extends EsJson<DataCircleMemberToAdd>
{
  static final DataCircleMemberToAddJson INSTANCE = new DataCircleMemberToAddJson();

  private DataCircleMemberToAddJson()
  {
    super(DataCircleMemberToAdd.class, new Object[] { "contactId", "displayName", DataCircleMemberIdJson.class, "memberId" });
  }

  public static DataCircleMemberToAddJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataCircleMemberToAddJson
 * JD-Core Version:    0.6.2
 */