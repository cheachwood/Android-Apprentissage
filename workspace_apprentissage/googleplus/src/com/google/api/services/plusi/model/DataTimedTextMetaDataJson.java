package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataTimedTextMetaDataJson extends EsJson<DataTimedTextMetaData>
{
  static final DataTimedTextMetaDataJson INSTANCE = new DataTimedTextMetaDataJson();

  private DataTimedTextMetaDataJson()
  {
    super(DataTimedTextMetaData.class, new Object[] { DataTimedTextTrackJson.class, "track", "ttsUrl" });
  }

  public static DataTimedTextMetaDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataTimedTextMetaDataJson
 * JD-Core Version:    0.6.2
 */