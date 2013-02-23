package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataKvPairJson extends EsJson<DataKvPair>
{
  static final DataKvPairJson INSTANCE = new DataKvPairJson();

  private DataKvPairJson()
  {
    super(DataKvPair.class, new Object[] { "key", "value" });
  }

  public static DataKvPairJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataKvPairJson
 * JD-Core Version:    0.6.2
 */