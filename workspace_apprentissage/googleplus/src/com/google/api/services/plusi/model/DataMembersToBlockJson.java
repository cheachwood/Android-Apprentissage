package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataMembersToBlockJson extends EsJson<DataMembersToBlock>
{
  static final DataMembersToBlockJson INSTANCE = new DataMembersToBlockJson();

  private DataMembersToBlockJson()
  {
    super(DataMembersToBlock.class, new Object[] { "block", DataMemberToBlockJson.class, "members" });
  }

  public static DataMembersToBlockJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataMembersToBlockJson
 * JD-Core Version:    0.6.2
 */