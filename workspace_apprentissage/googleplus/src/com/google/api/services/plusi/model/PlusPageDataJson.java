package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PlusPageDataJson extends EsJson<PlusPageData>
{
  static final PlusPageDataJson INSTANCE = new PlusPageDataJson();

  private PlusPageDataJson()
  {
    super(PlusPageData.class, new Object[] { "isShellPage", "name", "obfuscatedGaiaId", "photoUrl", "profileUrl", "tagline" });
  }

  public static PlusPageDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PlusPageDataJson
 * JD-Core Version:    0.6.2
 */