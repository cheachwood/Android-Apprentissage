package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GooglePlusSignupPromoJson extends EsJson<GooglePlusSignupPromo>
{
  static final GooglePlusSignupPromoJson INSTANCE = new GooglePlusSignupPromoJson();

  private GooglePlusSignupPromoJson()
  {
    super(GooglePlusSignupPromo.class, new Object[] { LinkFragmentJson.class, "text" });
  }

  public static GooglePlusSignupPromoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GooglePlusSignupPromoJson
 * JD-Core Version:    0.6.2
 */