package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataClusterInfoJson extends EsJson<DataClusterInfo>
{
  static final DataClusterInfoJson INSTANCE = new DataClusterInfoJson();

  private DataClusterInfoJson()
  {
    super(DataClusterInfo.class, new Object[] { "burstClusterIndex", "diversityClusterIndex" });
  }

  public static DataClusterInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataClusterInfoJson
 * JD-Core Version:    0.6.2
 */