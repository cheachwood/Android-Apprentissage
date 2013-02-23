package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataVideoStreamJson extends EsJson<DataVideoStream>
{
  static final DataVideoStreamJson INSTANCE = new DataVideoStreamJson();

  private DataVideoStreamJson()
  {
    super(DataVideoStream.class, new Object[] { "formatId", "height", "url", "width" });
  }

  public static DataVideoStreamJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataVideoStreamJson
 * JD-Core Version:    0.6.2
 */