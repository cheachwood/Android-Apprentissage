package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class AttributeProtoCanonicalValueJson extends EsJson<AttributeProtoCanonicalValue>
{
  static final AttributeProtoCanonicalValueJson INSTANCE = new AttributeProtoCanonicalValueJson();

  private AttributeProtoCanonicalValueJson()
  {
    super(AttributeProtoCanonicalValue.class, new Object[] { "hotelRatingStars", PlacePageLinkJson.class, "link", "priceLevel", PriceRangeProtoJson.class, "priceRange", "ratingStars", TimeScheduleProtoJson.class, "timeSchedule" });
  }

  public static AttributeProtoCanonicalValueJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.AttributeProtoCanonicalValueJson
 * JD-Core Version:    0.6.2
 */