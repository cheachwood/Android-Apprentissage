package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataEmailJson extends EsJson<DataEmail>
{
  static final DataEmailJson INSTANCE = new DataEmailJson();

  private DataEmailJson()
  {
    super(DataEmail.class, new Object[] { "customTag", "primary", "standardTag", "value" });
  }

  public static DataEmailJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataEmailJson
 * JD-Core Version:    0.6.2
 */