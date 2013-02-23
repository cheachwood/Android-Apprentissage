package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataEntityJson extends EsJson<DataEntity>
{
  static final DataEntityJson INSTANCE = new DataEntityJson();

  private DataEntityJson()
  {
    super(DataEntity.class, new Object[] { "id", "name", "snippet" });
  }

  public static DataEntityJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataEntityJson
 * JD-Core Version:    0.6.2
 */