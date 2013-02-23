package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class MobileApplicationJson extends EsJson<MobileApplication>
{
  static final MobileApplicationJson INSTANCE = new MobileApplicationJson();

  private MobileApplicationJson()
  {
    super(MobileApplication.class, new Object[] { EmbedClientItemJson.class, "about", AggregateRatingJson.class, "aggregateRating", EmbedClientItemJson.class, "author", EmbedsPersonJson.class, "authorDeprecated", "buttonStyle", "description", "editorsChoiceBadgeUrl", "imageUrl", "logoHrefUrl", "logoImageUrl", "name", OfferJson.class, "offers", "thumbnailUrl", "titleIconUrl", "topDeveloperBadgeUrl", "url" });
  }

  public static MobileApplicationJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MobileApplicationJson
 * JD-Core Version:    0.6.2
 */