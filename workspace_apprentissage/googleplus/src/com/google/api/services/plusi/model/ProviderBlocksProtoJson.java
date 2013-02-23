package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ProviderBlocksProtoJson extends EsJson<ProviderBlocksProto>
{
  static final ProviderBlocksProtoJson INSTANCE = new ProviderBlocksProtoJson();

  private ProviderBlocksProtoJson()
  {
    super(ProviderBlocksProto.class, new Object[] { PlacePageLinkJson.class, "moreReviewsLink", NavbarProtoJson.class, "navbar", ProviderBlockProtoJson.class, "providerBlock", ResultsRangeProtoJson.class, "resultsRange", StoryTitleJson.class, "title" });
  }

  public static ProviderBlocksProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ProviderBlocksProtoJson
 * JD-Core Version:    0.6.2
 */