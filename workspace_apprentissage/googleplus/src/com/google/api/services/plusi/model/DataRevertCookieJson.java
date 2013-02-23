package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataRevertCookieJson extends EsJson<DataRevertCookie>
{
  static final DataRevertCookieJson INSTANCE = new DataRevertCookieJson();

  private DataRevertCookieJson()
  {
    super(DataRevertCookie.class, new Object[] { "focusRevertCookie" });
  }

  public static DataRevertCookieJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataRevertCookieJson
 * JD-Core Version:    0.6.2
 */