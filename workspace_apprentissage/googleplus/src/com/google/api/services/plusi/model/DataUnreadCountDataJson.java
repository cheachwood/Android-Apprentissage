package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataUnreadCountDataJson extends EsJson<DataUnreadCountData>
{
  static final DataUnreadCountDataJson INSTANCE = new DataUnreadCountDataJson();

  private DataUnreadCountDataJson()
  {
    super(DataUnreadCountData.class, new Object[] { "count", "version" });
  }

  public static DataUnreadCountDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataUnreadCountDataJson
 * JD-Core Version:    0.6.2
 */