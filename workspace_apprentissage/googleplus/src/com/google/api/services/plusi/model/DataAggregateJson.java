package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataAggregateJson extends EsJson<DataAggregate>
{
  static final DataAggregateJson INSTANCE = new DataAggregateJson();

  private DataAggregateJson()
  {
    super(DataAggregate.class, new Object[] { "count", "groupdisplayname", DataPersonJson.class, "person" });
  }

  public static DataAggregateJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataAggregateJson
 * JD-Core Version:    0.6.2
 */