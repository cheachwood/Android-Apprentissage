package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class Movie extends GenericJson
{
  public EmbedClientItem about;
  public List<EmbedsPerson> actor;
  public AggregateRating aggregateRating;
  public String buttonStyle;
  public String description;
  public List<EmbedsPerson> director;
  public String imageUrl;
  public String logoHrefUrl;
  public String logoImageUrl;
  public String name;
  public List<Offer> offers;
  public List<EmbedsPerson> producer;
  public String thumbnailUrl;
  public String titleIconUrl;
  public String url;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.Movie
 * JD-Core Version:    0.6.2
 */