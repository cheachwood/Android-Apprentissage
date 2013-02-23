package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataPersonJson extends EsJson<DataPerson>
{
  static final DataPersonJson INSTANCE = new DataPersonJson();

  private DataPersonJson()
  {
    super(DataPerson.class, new Object[] { "nickName", "obfuscatedId", "photoUrl", "profileUrl", "userName" });
  }

  public static DataPersonJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataPersonJson
 * JD-Core Version:    0.6.2
 */