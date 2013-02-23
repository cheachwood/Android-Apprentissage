package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class InterestDataJson extends EsJson<InterestData>
{
  static final InterestDataJson INSTANCE = new InterestDataJson();

  private InterestDataJson()
  {
    super(InterestData.class, arrayOfObject);
  }

  public static InterestDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.InterestDataJson
 * JD-Core Version:    0.6.2
 */