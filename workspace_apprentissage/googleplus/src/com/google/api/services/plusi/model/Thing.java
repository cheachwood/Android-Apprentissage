package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class Thing extends GenericJson
{
  public List<EmbedClientItem> author;
  public String description;
  public String descriptionTruncated;
  public String imageUrl;
  public String name;
  public String proxiedFaviconUrl;
  public Thumbnail proxiedImage;
  public String url;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.Thing
 * JD-Core Version:    0.6.2
 */