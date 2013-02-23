package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ProviderBlockProtoJson extends EsJson<ProviderBlockProto>
{
  static final ProviderBlockProtoJson INSTANCE = new ProviderBlockProtoJson();

  private ProviderBlockProtoJson()
  {
    super(ProviderBlockProto.class, new Object[] { AggregatedReviewsProtoJson.class, "aggregatedReviews", AwardProtoJson.class, "award", "faviconUrl", "hostName", "originalIndex", RealtimeUpdateProtoJson.class, "realtimeUpdate", WebReviewProtoJson.class, "review", PlacePageLinkJson.class, "title" });
  }

  public static ProviderBlockProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ProviderBlockProtoJson
 * JD-Core Version:    0.6.2
 */