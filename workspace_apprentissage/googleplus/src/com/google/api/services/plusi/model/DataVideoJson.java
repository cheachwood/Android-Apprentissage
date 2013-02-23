package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataVideoJson extends EsJson<DataVideo>
{
  static final DataVideoJson INSTANCE = new DataVideoJson();

  private DataVideoJson()
  {
    super(DataVideo.class, arrayOfObject);
  }

  public static DataVideoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataVideoJson
 * JD-Core Version:    0.6.2
 */