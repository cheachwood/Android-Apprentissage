package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class MagazineJson extends EsJson<Magazine>
{
  static final MagazineJson INSTANCE = new MagazineJson();

  private MagazineJson()
  {
    super(Magazine.class, new Object[] { EmbedClientItemJson.class, "about", "buttonStyle", "description", "imageUrl", "logoHrefUrl", "logoImageUrl", "name", OfferJson.class, "offers", "text", "thumbnailUrl", "titleIconUrl", "url" });
  }

  public static MagazineJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MagazineJson
 * JD-Core Version:    0.6.2
 */