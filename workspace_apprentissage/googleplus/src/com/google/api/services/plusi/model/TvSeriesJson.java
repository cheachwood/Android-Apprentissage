package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class TvSeriesJson extends EsJson<TvSeries>
{
  static final TvSeriesJson INSTANCE = new TvSeriesJson();

  private TvSeriesJson()
  {
    super(TvSeries.class, new Object[] { EmbedClientItemJson.class, "about", EmbedsPersonJson.class, "actor", "buttonStyle", "description", "imageUrl", "logoHrefUrl", "logoImageUrl", "name", OfferJson.class, "offers", "thumbnailUrl", "titleIconUrl", "url" });
  }

  public static TvSeriesJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.TvSeriesJson
 * JD-Core Version:    0.6.2
 */