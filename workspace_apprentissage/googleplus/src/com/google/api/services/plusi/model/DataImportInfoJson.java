package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataImportInfoJson extends EsJson<DataImportInfo>
{
  static final DataImportInfoJson INSTANCE = new DataImportInfoJson();

  private DataImportInfoJson()
  {
    super(DataImportInfo.class, new Object[] { "groupId", "iconUrl" });
  }

  public static DataImportInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataImportInfoJson
 * JD-Core Version:    0.6.2
 */