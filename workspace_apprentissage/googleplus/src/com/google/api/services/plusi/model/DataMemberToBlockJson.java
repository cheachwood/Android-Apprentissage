package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataMemberToBlockJson extends EsJson<DataMemberToBlock>
{
  static final DataMemberToBlockJson INSTANCE = new DataMemberToBlockJson();

  private DataMemberToBlockJson()
  {
    super(DataMemberToBlock.class, new Object[] { DataCircleMemberIdJson.class, "memberId", "name" });
  }

  public static DataMemberToBlockJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataMemberToBlockJson
 * JD-Core Version:    0.6.2
 */