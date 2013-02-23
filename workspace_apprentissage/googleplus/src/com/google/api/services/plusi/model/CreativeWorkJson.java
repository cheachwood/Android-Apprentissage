package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class CreativeWorkJson extends EsJson<CreativeWork>
{
  static final CreativeWorkJson INSTANCE = new CreativeWorkJson();

  private CreativeWorkJson()
  {
    super(CreativeWork.class, new Object[] { EmbedClientItemJson.class, "about", "description", "imageUrl", "name", "proxiedFaviconUrl", ThumbnailJson.class, "proxiedImage", "text", "url" });
  }

  public static CreativeWorkJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.CreativeWorkJson
 * JD-Core Version:    0.6.2
 */