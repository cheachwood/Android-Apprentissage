package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class TvEpisodeJson extends EsJson<TvEpisode>
{
  static final TvEpisodeJson INSTANCE = new TvEpisodeJson();

  private TvEpisodeJson()
  {
    super(TvEpisode.class, new Object[] { EmbedClientItemJson.class, "about", "description", "imageUrl", "name", TvSeriesJson.class, "partOfTvSeries", "url" });
  }

  public static TvEpisodeJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.TvEpisodeJson
 * JD-Core Version:    0.6.2
 */