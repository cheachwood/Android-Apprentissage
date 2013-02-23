package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class InterestJson extends EsJson<Interest>
{
  static final InterestJson INSTANCE = new InterestJson();

  private InterestJson()
  {
    super(Interest.class, new Object[] { "entityId", "freeText" });
  }

  public static InterestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.InterestJson
 * JD-Core Version:    0.6.2
 */