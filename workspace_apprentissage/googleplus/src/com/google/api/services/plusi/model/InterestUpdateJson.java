package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class InterestUpdateJson extends EsJson<InterestUpdate>
{
  static final InterestUpdateJson INSTANCE = new InterestUpdateJson();

  private InterestUpdateJson()
  {
    super(InterestUpdate.class, new Object[] { InterestJson.class, "sourceInterest", "sourceViewType" });
  }

  public static InterestUpdateJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.InterestUpdateJson
 * JD-Core Version:    0.6.2
 */