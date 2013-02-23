package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataRect32Json extends EsJson<DataRect32>
{
  static final DataRect32Json INSTANCE = new DataRect32Json();

  private DataRect32Json()
  {
    super(DataRect32.class, new Object[] { DataPoint32Json.class, "lowerRight", DataPoint32Json.class, "upperLeft" });
  }

  public static DataRect32Json getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataRect32Json
 * JD-Core Version:    0.6.2
 */