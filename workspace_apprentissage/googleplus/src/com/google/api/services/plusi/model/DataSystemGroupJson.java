package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataSystemGroupJson extends EsJson<DataSystemGroup>
{
  static final DataSystemGroupJson INSTANCE = new DataSystemGroupJson();

  private DataSystemGroupJson()
  {
    super(DataSystemGroup.class, new Object[] { DataClientPoliciesJson.class, "clientPolicy", "id", "memberCount", "name", "type" });
  }

  public static DataSystemGroupJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataSystemGroupJson
 * JD-Core Version:    0.6.2
 */