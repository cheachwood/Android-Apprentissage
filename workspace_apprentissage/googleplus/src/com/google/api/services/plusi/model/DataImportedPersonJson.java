package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataImportedPersonJson extends EsJson<DataImportedPerson>
{
  static final DataImportedPersonJson INSTANCE = new DataImportedPersonJson();

  private DataImportedPersonJson()
  {
    super(DataImportedPerson.class, new Object[] { DataImportInfoJson.class, "importInfo", DataImportInfoJson.class, "info", DataCirclePersonJson.class, "member" });
  }

  public static DataImportedPersonJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataImportedPersonJson
 * JD-Core Version:    0.6.2
 */