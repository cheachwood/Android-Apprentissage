package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SharedPlusOneJson extends EsJson<SharedPlusOne>
{
  static final SharedPlusOneJson INSTANCE = new SharedPlusOneJson();

  private SharedPlusOneJson()
  {
    super(SharedPlusOne.class, new Object[] { "fromDomain", PlusOneSummaryJson.class, "plusone" });
  }

  public static SharedPlusOneJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SharedPlusOneJson
 * JD-Core Version:    0.6.2
 */