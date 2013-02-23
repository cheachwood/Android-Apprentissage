package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class WebPageJson extends EsJson<WebPage>
{
  static final WebPageJson INSTANCE = new WebPageJson();

  private WebPageJson()
  {
    super(WebPage.class, new Object[] { EmbedClientItemJson.class, "about", EmbedClientItemJson.class, "author", "canonicalFountainStream", "description", "descriptionTruncated", "imageUrl", "inboxFountainStream", "name", "postmodFountainStream", "premodFountainStream", "proxiedFaviconUrl", ThumbnailJson.class, "proxiedImage", ImageObjectJson.class, "relatedImage", "sourceName", "unfilteredFountainStream", "url" });
  }

  public static WebPageJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.WebPageJson
 * JD-Core Version:    0.6.2
 */