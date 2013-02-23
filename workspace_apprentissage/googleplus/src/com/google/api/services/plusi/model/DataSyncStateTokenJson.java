package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataSyncStateTokenJson extends EsJson<DataSyncStateToken>
{
  static final DataSyncStateTokenJson INSTANCE = new DataSyncStateTokenJson();

  private DataSyncStateTokenJson()
  {
    super(DataSyncStateToken.class, arrayOfObject);
  }

  public static DataSyncStateTokenJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataSyncStateTokenJson
 * JD-Core Version:    0.6.2
 */