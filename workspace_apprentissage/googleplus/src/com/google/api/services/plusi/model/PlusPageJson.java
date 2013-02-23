package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PlusPageJson extends EsJson<PlusPage>
{
  static final PlusPageJson INSTANCE = new PlusPageJson();

  private PlusPageJson()
  {
    super(PlusPage.class, new Object[] { "description", "imageUrl", "name", "profileId", ThumbnailJson.class, "proxiedImage", "url" });
  }

  public static PlusPageJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PlusPageJson
 * JD-Core Version:    0.6.2
 */