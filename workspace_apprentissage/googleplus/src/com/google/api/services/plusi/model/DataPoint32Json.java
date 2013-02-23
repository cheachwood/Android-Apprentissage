package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataPoint32Json extends EsJson<DataPoint32>
{
  static final DataPoint32Json INSTANCE = new DataPoint32Json();

  private DataPoint32Json()
  {
    super(DataPoint32.class, new Object[] { "x", "y" });
  }

  public static DataPoint32Json getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataPoint32Json
 * JD-Core Version:    0.6.2
 */