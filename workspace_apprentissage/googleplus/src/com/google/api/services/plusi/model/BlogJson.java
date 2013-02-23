package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class BlogJson extends EsJson<Blog>
{
  static final BlogJson INSTANCE = new BlogJson();

  private BlogJson()
  {
    super(Blog.class, new Object[] { EmbedsPersonJson.class, "author", "blogId", "description", "imageUrl", "name", "proxiedFaviconUrl", ThumbnailJson.class, "proxiedImage", "url" });
  }

  public static BlogJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.BlogJson
 * JD-Core Version:    0.6.2
 */