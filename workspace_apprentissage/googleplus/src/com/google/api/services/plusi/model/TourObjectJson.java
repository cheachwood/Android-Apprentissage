package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class TourObjectJson extends EsJson<TourObject>
{
  static final TourObjectJson INSTANCE = new TourObjectJson();

  private TourObjectJson()
  {
    super(TourObject.class, new Object[] { "description", "imageUrl", "name", "proxiedFaviconUrl", ThumbnailJson.class, "proxiedImage", "url" });
  }

  public static TourObjectJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.TourObjectJson
 * JD-Core Version:    0.6.2
 */