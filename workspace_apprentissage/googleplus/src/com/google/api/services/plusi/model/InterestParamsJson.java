package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class InterestParamsJson extends EsJson<InterestParams>
{
  static final InterestParamsJson INSTANCE = new InterestParamsJson();

  private InterestParamsJson()
  {
    super(InterestParams.class, new Object[] { "interestViewType" });
  }

  public static InterestParamsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.InterestParamsJson
 * JD-Core Version:    0.6.2
 */