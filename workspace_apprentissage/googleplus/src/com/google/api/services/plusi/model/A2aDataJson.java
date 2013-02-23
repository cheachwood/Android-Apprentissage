package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class A2aDataJson extends EsJson<A2aData>
{
  static final A2aDataJson INSTANCE = new A2aDataJson();

  private A2aDataJson()
  {
    super(A2aData.class, new Object[] { HangoutDataJson.class, "hangoutData" });
  }

  public static A2aDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.A2aDataJson
 * JD-Core Version:    0.6.2
 */