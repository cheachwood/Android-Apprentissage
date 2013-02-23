package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataCurationInfoJson extends EsJson<DataCurationInfo>
{
  static final DataCurationInfoJson INSTANCE = new DataCurationInfoJson();

  private DataCurationInfoJson()
  {
    super(DataCurationInfo.class, new Object[] { "argyleScore", "exposureScore", "sharpnessScore" });
  }

  public static DataCurationInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataCurationInfoJson
 * JD-Core Version:    0.6.2
 */