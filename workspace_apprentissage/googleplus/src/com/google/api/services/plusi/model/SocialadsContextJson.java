package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SocialadsContextJson extends EsJson<SocialadsContext>
{
  static final SocialadsContextJson INSTANCE = new SocialadsContextJson();

  private SocialadsContextJson()
  {
    super(SocialadsContext.class, arrayOfObject);
  }

  public static SocialadsContextJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SocialadsContextJson
 * JD-Core Version:    0.6.2
 */