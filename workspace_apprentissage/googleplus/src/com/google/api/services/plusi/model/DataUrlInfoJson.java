package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataUrlInfoJson extends EsJson<DataUrlInfo>
{
  static final DataUrlInfoJson INSTANCE = new DataUrlInfoJson();

  private DataUrlInfoJson()
  {
    super(DataUrlInfo.class, new Object[] { "faviconUrl", "host", "isVerified", "title", "url" });
  }

  public static DataUrlInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataUrlInfoJson
 * JD-Core Version:    0.6.2
 */