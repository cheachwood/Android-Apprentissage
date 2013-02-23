package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataPerfectStreamInfoJson extends EsJson<DataPerfectStreamInfo>
{
  static final DataPerfectStreamInfoJson INSTANCE = new DataPerfectStreamInfoJson();

  private DataPerfectStreamInfoJson()
  {
    super(DataPerfectStreamInfo.class, new Object[] { "activityId", "perfectStreamItemId", "sourceType", "title", "url" });
  }

  public static DataPerfectStreamInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataPerfectStreamInfoJson
 * JD-Core Version:    0.6.2
 */