package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataSystemGroupsJson extends EsJson<DataSystemGroups>
{
  static final DataSystemGroupsJson INSTANCE = new DataSystemGroupsJson();

  private DataSystemGroupsJson()
  {
    super(DataSystemGroups.class, new Object[] { DataSystemGroupJson.class, "systemGroup" });
  }

  public static DataSystemGroupsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataSystemGroupsJson
 * JD-Core Version:    0.6.2
 */