package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class Review extends GenericJson
{
  public EmbedClientItem about;
  public String description;
  public String imageUrl;
  public String name;
  public List<Rating> reviewRating;
  public String text;
  public String url;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.Review
 * JD-Core Version:    0.6.2
 */