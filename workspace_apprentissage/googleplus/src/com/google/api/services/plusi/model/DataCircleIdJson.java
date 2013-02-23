package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataCircleIdJson extends EsJson<DataCircleId>
{
  static final DataCircleIdJson INSTANCE = new DataCircleIdJson();

  private DataCircleIdJson()
  {
    super(DataCircleId.class, new Object[] { "focusId", "obfuscatedGaiaId" });
  }

  public static DataCircleIdJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataCircleIdJson
 * JD-Core Version:    0.6.2
 */