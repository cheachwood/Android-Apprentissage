package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataPhoneJson extends EsJson<DataPhone>
{
  static final DataPhoneJson INSTANCE = new DataPhoneJson();

  private DataPhoneJson()
  {
    super(DataPhone.class, new Object[] { "canonicalizedForm", "customTag", "primary", "standardTag", "uri", "value" });
  }

  public static DataPhoneJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataPhoneJson
 * JD-Core Version:    0.6.2
 */