package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class BlogPostingJson extends EsJson<BlogPosting>
{
  static final BlogPostingJson INSTANCE = new BlogPostingJson();

  private BlogPostingJson()
  {
    super(BlogPosting.class, new Object[] { EmbedsPersonJson.class, "author", "blogId", "canonicalFountainStream", "description", "imageUrl", "inboxFountainStream", "name", "postId", "postmodFountainStream", "premodFountainStream", "proxiedFaviconUrl", ThumbnailJson.class, "proxiedImage", "unfilteredFountainStream", "url" });
  }

  public static BlogPostingJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.BlogPostingJson
 * JD-Core Version:    0.6.2
 */