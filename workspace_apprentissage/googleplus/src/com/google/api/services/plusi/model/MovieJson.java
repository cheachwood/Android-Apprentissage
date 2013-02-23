package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class MovieJson extends EsJson<Movie>
{
  static final MovieJson INSTANCE = new MovieJson();

  private MovieJson()
  {
    super(Movie.class, new Object[] { EmbedClientItemJson.class, "about", EmbedsPersonJson.class, "actor", AggregateRatingJson.class, "aggregateRating", "buttonStyle", "description", EmbedsPersonJson.class, "director", "imageUrl", "logoHrefUrl", "logoImageUrl", "name", OfferJson.class, "offers", EmbedsPersonJson.class, "producer", "thumbnailUrl", "titleIconUrl", "url" });
  }

  public static MovieJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MovieJson
 * JD-Core Version:    0.6.2
 */