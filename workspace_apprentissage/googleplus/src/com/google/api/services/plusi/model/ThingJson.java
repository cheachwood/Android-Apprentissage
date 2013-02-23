package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ThingJson extends EsJson<Thing>
{
  static final ThingJson INSTANCE = new ThingJson();

  private ThingJson()
  {
    super(Thing.class, new Object[] { EmbedClientItemJson.class, "author", "description", "descriptionTruncated", "imageUrl", "name", "proxiedFaviconUrl", ThumbnailJson.class, "proxiedImage", "url" });
  }

  public static ThingJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ThingJson
 * JD-Core Version:    0.6.2
 */