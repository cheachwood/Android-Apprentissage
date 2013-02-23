package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataImageJson extends EsJson<DataImage>
{
  static final DataImageJson INSTANCE = new DataImageJson();

  private DataImageJson()
  {
    super(DataImage.class, new Object[] { "height", "url", "width" });
  }

  public static DataImageJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataImageJson
 * JD-Core Version:    0.6.2
 */