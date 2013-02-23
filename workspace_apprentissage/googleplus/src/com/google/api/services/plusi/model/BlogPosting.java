package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class BlogPosting extends GenericJson
{
  public List<EmbedsPerson> author;
  public String blogId;
  public String canonicalFountainStream;
  public String description;
  public String imageUrl;
  public List<String> inboxFountainStream;
  public String name;
  public String postId;
  public List<String> postmodFountainStream;
  public List<String> premodFountainStream;
  public String proxiedFaviconUrl;
  public Thumbnail proxiedImage;
  public List<String> unfilteredFountainStream;
  public String url;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.BlogPosting
 * JD-Core Version:    0.6.2
 */