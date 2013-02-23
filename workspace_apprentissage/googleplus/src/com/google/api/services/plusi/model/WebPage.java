package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class WebPage extends GenericJson
{
  public EmbedClientItem about;
  public List<EmbedClientItem> author;
  public String canonicalFountainStream;
  public String description;
  public String descriptionTruncated;
  public String imageUrl;
  public List<String> inboxFountainStream;
  public String name;
  public List<String> postmodFountainStream;
  public List<String> premodFountainStream;
  public String proxiedFaviconUrl;
  public Thumbnail proxiedImage;
  public List<ImageObject> relatedImage;
  public String sourceName;
  public List<String> unfilteredFountainStream;
  public String url;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.WebPage
 * JD-Core Version:    0.6.2
 */