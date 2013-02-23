package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataRectRelativeJson extends EsJson<DataRectRelative>
{
  static final DataRectRelativeJson INSTANCE = new DataRectRelativeJson();

  private DataRectRelativeJson()
  {
    super(DataRectRelative.class, new Object[] { "bottom", "left", "right", "top" });
  }

  public static DataRectRelativeJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataRectRelativeJson
 * JD-Core Version:    0.6.2
 */