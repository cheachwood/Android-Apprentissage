package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class WritePlaceReviewRequestJson extends EsJson<WritePlaceReviewRequest>
{
  static final WritePlaceReviewRequestJson INSTANCE = new WritePlaceReviewRequestJson();

  private WritePlaceReviewRequestJson()
  {
    super(WritePlaceReviewRequest.class, new Object[] { AbuseSignalsJson.class, "abuseSignals", "cid", ApiaryFieldsJson.class, "commonFields", "enableTracing", "experimentId", "includeSuggestedPlaces", ZagatAspectRatingProtoJson.class, "oldZagatAspectRatings", PriceProtoJson.class, "price", PriceLevelsProtoJson.class, "priceLevel", "reviewText", "source", "userCountryCode", "userLanguage", ZagatAspectRatingProtoJson.class, "zagatAspectRatings" });
  }

  public static WritePlaceReviewRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.WritePlaceReviewRequestJson
 * JD-Core Version:    0.6.2
 */