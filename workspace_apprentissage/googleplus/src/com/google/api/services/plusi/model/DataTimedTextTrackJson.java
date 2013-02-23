package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataTimedTextTrackJson extends EsJson<DataTimedTextTrack>
{
  static final DataTimedTextTrackJson INSTANCE = new DataTimedTextTrackJson();

  private DataTimedTextTrackJson()
  {
    super(DataTimedTextTrack.class, new Object[] { "isActive", "isProcessing", "isServable", "lang", "name" });
  }

  public static DataTimedTextTrackJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataTimedTextTrackJson
 * JD-Core Version:    0.6.2
 */