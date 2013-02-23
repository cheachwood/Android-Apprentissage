package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataExifInfoJson extends EsJson<DataExifInfo>
{
  static final DataExifInfoJson INSTANCE = new DataExifInfoJson();

  private DataExifInfoJson()
  {
    super(DataExifInfo.class, arrayOfObject);
  }

  public static DataExifInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataExifInfoJson
 * JD-Core Version:    0.6.2
 */